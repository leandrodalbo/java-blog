#!/usr/bin/env python3
"""Build an editable .docx of the whole blog, for manual reorganizing.

    python3 build_docx.py

Same content/order as build_pdf.py (About Me + every entry's concepts +
exercise, diagrams included), but converted straight to Word format via
pandoc (docx needs no LaTeX engine, unlike the PDF path). Open the
result in Word, or upload it to Google Drive to get a fully editable
Google Doc — drag diagrams/paragraphs around by hand, then decide what
goes back into the site.

This is a one-off working copy, not a generated site artifact: it isn't
linked from anywhere on the site and there's no expectation of running
it after every entry edit — just when you want a fresh working copy.
"""
import subprocess
import tempfile
from pathlib import Path

from build_pdf import WEB_DIR, build_sections

OUT_DOCX = WEB_DIR / "systems-and-software-concepts-EDITABLE.docx"

ABOUT_MD = """\
# About Leandro Dal Bo

![Leandro Dal Bo](about-photo.jpg)

I'm a backend-focused software engineer with several years of production \
experience in Java, Kotlin, and Spring Boot — building REST APIs and \
cloud-deployed services with AWS, Terraform, and Docker, and leaning on \
TDD/BDD to keep changes safe. This blog is me going back over the \
fundamentals properly: writing the theory up the way I wish someone had \
explained it to me the first time, then backing each piece with a worked \
exercise.

Outside of the blog, I design and build the Open Football Project \
(footballproject.org) — a football analytics platform built around a \
Kotlin/Spring Boot API, a live-events service, a React web app, and a \
React Native mobile app, all deployed on AWS via Terraform. It \
aggregates and normalises football leagues, scores, and stats from \
around the world, and it's open source.

GitHub: https://github.com/leandrodalbo — LinkedIn: \
https://www.linkedin.com/in/leandrodlb/ — footballproject.org
"""


def build_combined_markdown() -> str:
    parts = [ABOUT_MD.strip()]
    for _, _, body in build_sections():
        parts.append(body)
    return "\n\n".join(parts) + "\n"


def build_docx():
    combined_md = build_combined_markdown()

    with tempfile.NamedTemporaryFile(
        "w", suffix=".md", dir=WEB_DIR, delete=False
    ) as tmp:
        tmp.write(combined_md)
        tmp_path = Path(tmp.name)

    try:
        subprocess.run(
            [
                "pandoc",
                str(tmp_path),
                "-o",
                str(OUT_DOCX),
                "--toc",
                "--toc-depth=1",
                "--resource-path",
                str(WEB_DIR),
                "--metadata",
                "title=Systems & Software Concepts — editable working copy",
            ],
            check=True,
            cwd=WEB_DIR,
        )
    finally:
        tmp_path.unlink(missing_ok=True)


if __name__ == "__main__":
    build_docx()
    size_kb = OUT_DOCX.stat().st_size / 1024
    print(f"Built {OUT_DOCX.name} ({size_kb:.0f} KB)")
