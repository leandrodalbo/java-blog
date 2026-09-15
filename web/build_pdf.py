#!/usr/bin/env python3
"""Build one downloadable PDF of the whole blog (all entries, concepts + exercises).

Run after editing any entry, or after web/build.py:

    python3 build_pdf.py

Renders a single print-styled HTML page (same fonts/theme as the site,
via Tailwind's typography plugin + marked.js) and prints it to PDF with
headless Chrome. Regenerates systems-and-software-concepts.pdf in this
directory. Source of truth is still the entry-N/*.md files — this PDF,
like the generated .html/entries.js, is a derived artifact.
"""
import base64
import json
import re
import subprocess
from pathlib import Path

WEB_DIR = Path(__file__).parent
OUT_PDF = WEB_DIR / "systems-and-software-concepts.pdf"
PRINT_HTML = WEB_DIR / "_print.html"
CHROME_BIN = "google-chrome-stable"

IMG_RE = re.compile(r"(!\[[^\]]*\]\()([^)]+)(\))")


def entry_dirs():
    return sorted(
        WEB_DIR.glob("entry-*"),
        key=lambda p: (int(p.name.split("-")[1]), p.name),
    )


def qualify_image_paths(md_text: str, entry_dir_name: str) -> str:
    def repl(m):
        target = m.group(2)
        if target.startswith(("http://", "https://", "/")):
            return m.group(0)
        return f"{m.group(1)}{entry_dir_name}/{target}{m.group(3)}"

    return IMG_RE.sub(repl, md_text)


def shift_headings(md_text: str) -> str:
    """Demote every heading by one level (# -> ##, ## -> ### ...)."""
    return re.sub(r"(?m)^(#+)(\s)", r"#\1\2", md_text)


def extract_title(md_text: str, fallback: str) -> str:
    match = re.search(r"^#\s+(.+)$", md_text, re.MULTILINE)
    return match.group(1).strip() if match else fallback


def build_sections():
    """Returns list of (anchor, title, markdown_body) per entry."""
    sections = []
    for entry_dir in entry_dirs():
        concepts_md = entry_dir / "concepts.md"
        exercise_md = entry_dir / "exercise.md"
        if not concepts_md.exists():
            continue

        concepts_text = qualify_image_paths(concepts_md.read_text(), entry_dir.name)
        title = extract_title(concepts_text, entry_dir.name)
        body = concepts_text.strip()

        if exercise_md.exists():
            exercise_text = qualify_image_paths(exercise_md.read_text(), entry_dir.name)
            exercise_text = shift_headings(exercise_text)
            exercise_text = re.sub(
                r"(?m)^##\s+(.+)$", r"## Exercise: \1", exercise_text, count=1
            )
            body += "\n\n" + exercise_text.strip()

        sections.append((entry_dir.name, title, body))
    return sections


PRINT_TEMPLATE = """<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Systems &amp; Software Concepts — full text</title>
<script src="https://cdn.tailwindcss.com?plugins=typography"></script>
<script src="tailwind.config.js"></script>
<script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Source+Serif+4:opsz,wght@8..60,400;8..60,600&family=JetBrains+Mono:wght@400;500&display=swap" rel="stylesheet">
<style>
  @page { margin: 20mm 18mm; }
  .prose pre { white-space: pre-wrap; overflow-wrap: break-word; }
  .entry { break-before: page; }
  .entry:first-child { break-before: avoid; }
  .cover { break-after: page; }

  /* never split a diagram, code block, table, or blockquote across pages */
  .prose img,
  .prose pre,
  .prose table,
  .prose blockquote,
  .prose figure {
    break-inside: avoid;
    page-break-inside: avoid;
  }
  /* don't strand a heading alone at the bottom of a page */
  .prose h1, .prose h2, .prose h3 {
    break-after: avoid-page;
    page-break-after: avoid;
  }
</style>
</head>
<body class="font-sans bg-white text-stone-800">

<div class="cover max-w-3xl mx-auto px-8 pt-12 pb-10">
  <p class="text-sm font-mono text-amber-700 uppercase tracking-wide">softwaresystems.blog</p>
  <h1 class="mt-3 text-4xl font-bold text-stone-900">Systems &amp; Software Concepts</h1>
  <p class="mt-3 text-lg text-stone-600 leading-relaxed max-w-xl">
    Theory notes and worked exercises, one concept at a time.
  </p>
  <p class="mt-6 text-stone-500">Leandro Dal Bo</p>
  <p class="mt-10 text-sm font-semibold uppercase tracking-wide text-stone-500">Contents</p>
  <ol class="mt-3 text-sm text-stone-700 leading-relaxed" style="columns: 2; column-gap: 2.5rem;">
    {{TOC}}
  </ol>
</div>

<div class="max-w-3xl mx-auto px-8">
  {{ENTRIES}}
</div>

<script>
  document.querySelectorAll("[data-md]").forEach((el) => {
    const b64 = el.textContent.trim();
    const bytes = Uint8Array.from(atob(b64), (c) => c.charCodeAt(0));
    const md = new TextDecoder("utf-8").decode(bytes);
    el.innerHTML = marked.parse(md);
  });
  window.__renderDone = true;
</script>

</body>
</html>
"""


def build_print_html():
    sections = build_sections()

    toc_items = "\n".join(
        f'<li style="break-inside: avoid;">{i + 1}. {title}</li>'
        for i, (_, title, _) in enumerate(sections)
    )

    entries_html = []
    for anchor, title, body in sections:
        b64 = base64.b64encode(body.encode("utf-8")).decode("ascii")
        entries_html.append(
            f'<article id="{anchor}" class="entry py-10">'
            f'<div class="prose prose-lg prose-stone max-w-none font-serif '
            f'prose-headings:font-sans prose-headings:font-semibold '
            f'prose-code:font-mono prose-pre:font-mono '
            f'prose-a:text-amber-700" data-md>{b64}</div>'
            f"</article>"
        )

    html = PRINT_TEMPLATE.replace("{{TOC}}", toc_items).replace(
        "{{ENTRIES}}", "\n".join(entries_html)
    )
    PRINT_HTML.write_text(html)


def render_pdf():
    subprocess.run(
        [
            CHROME_BIN,
            "--headless=new",
            "--disable-gpu",
            "--no-sandbox",
            "--run-all-compositor-stages-before-draw",
            "--virtual-time-budget=20000",
            f"--print-to-pdf={OUT_PDF}",
            "--no-pdf-header-footer",
            str(PRINT_HTML),
        ],
        check=True,
        cwd=WEB_DIR,
    )


if __name__ == "__main__":
    build_print_html()
    render_pdf()
    PRINT_HTML.unlink(missing_ok=True)
    size_kb = OUT_PDF.stat().st_size / 1024
    print(f"Built {OUT_PDF.name} ({size_kb:.0f} KB)")
