# Java, One Concept at a Time — Interview-Prep Blog

## What this is

Leandro is getting back to the basics — revisiting CS/Java fundamentals and
writing them up the way he wishes they'd been explained to him the first
time. Each working session he writes theory notes in a markdown file, and
(most days) one exercise gets a short write-up too. Over time this becomes
"Java, One Concept at a Time", a small static blog shared publicly: an
`index.html` (Tailwind) linking to every concept page. The blog must be
readable by beginners but not shallow for seniors — correct terminology,
clear diagrams where they help, and honest complexity analysis.

## Public repo — handle with care

No credentials/tokens ever. Don't hand-add `.idea`/`target` even on "add
everything." Only personal info allowed is the byline already in the
footer — no employer/recruiter names without asking first.

## Structure

- `web/` — the blog: fully static, no server, works opened directly via
  `file://`. Source of truth is the `.md` files; `.html`/`days.js` are
  generated — never hand-edit those.
  - `day-N/concepts.md` — theory notes for day N (one topic area per day).
  - `day-N/exercise.md` — the day's exercise write-up: problem description,
    test suite, implementation. Picked from `practice/`, may be something
    already implemented on an earlier day or something new.
  - `_template.html` — shared page shell (Tailwind + marked, both via CDN).
  - `build.py` — run after any `.md` edit: `python3 build.py`. Renders each
    `day-N/*.md` into a sibling `.html` (markdown embedded inline as base64,
    so no `fetch()` — that's what breaks under `file://`) and regenerates
    `days.js`, which `index.html` reads to build the day list.
  - `index.html` — the blog homepage, links to every generated `day-N/*.html`.
- `practice/` — Java (Maven, JUnit 5 + AssertJ) exercise sprint.
  - `EXERCISES.md` — the 7-day interview-prep plan and checklist, source of
    truth for what's done vs pending.
  - `src/main/java/org/example/DayN.java` — implementations.
  - `src/test/java/org/example/DayNTest.java` — tests (TDD: written first).

## Daily workflow

1. Create `web/day-N/` and write/refine `concepts.md` — theory for the day.
2. Pick one exercise from `practice/` for `day-N/exercise.md`:
   - Already implemented → just write it up (problem, existing tests, existing
     code). No new coding.
   - Not yet implemented → do a short TDD cycle: SPECIFY the problem in
     plain language, propose failing tests (RED) and wait for approval,
     then implement (GREEN). Don't weaken tests to force a pass.
3. Run `python3 web/build.py` to regenerate the static pages and day list.

Start easy and ramp up — early exercises should be simple enough to explain
to a beginner in a few minutes, even if there's a senior-level angle
(complexity, trade-offs) worth calling out.

## Progress log

- **Day 0** (2026-08-14): `web/day-0/concepts.md` — Java platform
  independence (javac/.class/JVM), JIT, and JVM runtime memory areas (heap,
  stacks, PC register, method area). `web/day-0/exercise.md` — palindrome
  check (two-pointer, O(1) space), picked from `practice` Day1 (already
  implemented). Static site scaffolded: `index.html`, `build.py`,
  `_template.html` — open `web/index.html` directly, no server needed.
- **Day 1** (2026-08-15): `web/day-1/concepts.md` — main method anatomy,
  string pool, access modifiers, primitive/wrapper/non-primitive data
  types, class vs instance variables, constructors, volatile, I/O
  packages. `web/day-1/exercise.md` — prime check (trial division up to
  `sqrt(n)`), picked from `practice` Day1 (already implemented).
- **Day 2+**: not started. Next unimplemented item in `EXERCISES.md` is
  "Validate a BST" (Day 5 of the sprint).
