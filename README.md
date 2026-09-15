# Systems & Software Concepts

Back to basics — software fundamentals and systems design explained the way I wish they'd been explained to me. One markdown file per entry, plus most entries a small exercise: problem, tests, implementation.

## Reading it

```
open web/index.html
```

Fully static, no server needed.

## Structure

- `web/entry-N/concepts.md`, `web/entry-N/exercise.md` — the entry's content.
- `web/build.py` — run after editing any `.md`: `python3 web/build.py`.
- `web/about.html` — bio page, linked from the homepage header.
- `web/build_pdf.py` — regenerates the downloadable full-blog PDF:
  `python3 web/build_pdf.py`.
- `web/build_docx.py` — one-off editable `.docx` of the whole blog (for
  manually reorganizing content/diagrams, e.g. in Google Docs), not a
  published site artifact: `python3 web/build_docx.py`.
- `practice/` — the Java/Maven project the exercises come from (TDD).
