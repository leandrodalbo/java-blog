# Systems & Software Concepts — Interview-Prep Blog

## What this is

Leandro is getting back to the basics — revisiting software fundamentals
and systems design and writing them up the way he wishes they'd been
explained to him the first time. Each working session he writes theory
notes in a markdown file, and (most sessions) one exercise gets a short
write-up too. Over time this becomes "Systems & Software Concepts", a
small static blog shared publicly: an `index.html` (Tailwind) linking to
every concept page. The blog started Java-only and has since grown past
that into broader backend/systems territory (Spring internals, sharding,
event-driven architecture) — code examples are still Java (that's the
`practice/` project's language), but the blog itself isn't scoped to Java
specifically anymore, hence the name. The blog must be readable by
beginners but not shallow for seniors — correct terminology, clear
diagrams where they help, and honest complexity analysis.

Renamed from "Java, One Concept at a Time" on `dailyjava.blog`; see the
rebrand note in the Progress log below for what changed structurally.

## Public repo — handle with care

No credentials/tokens ever. Don't hand-add `.idea`/`target` even on "add
everything." Personal info allowed: the footer byline, and — as of the
`about.html` page added 2026-09-13 — a short bio, headshot
(`about-photo.jpg`), skill summary, and a link to the Open Football
Project. Deliberately excluded from that bio: employer/recruiter names
and sectors (asked and declined) — keep it to skills and years of
experience in general terms. Still ask first before adding anything
beyond that (specific employers, clients, addresses, phone/email).

## Structure

- `web/` — the blog: fully static, no server, works opened directly via
  `file://`. Source of truth is the `.md` files; `.html`/`entries.js` are
  generated — never hand-edit those.
  - `entry-N/concepts.md` — theory notes for entry N (one topic area per
    entry). First line is `# Entry N: Title` — keep the `Entry N` prefix,
    it drives the label shown on the homepage.
  - `entry-N/exercise.md` — the entry's exercise write-up: problem
    description, test suite, implementation. Picked from `practice/`, may
    be something already implemented on an earlier entry or something new.
  - `_template.html` — shared page shell (Tailwind + marked, both via CDN).
  - `build.py` — run after any `.md` edit: `python3 build.py`. Renders each
    `entry-N/*.md` into a sibling `.html` (markdown embedded inline as
    base64, so no `fetch()` — that's what breaks under `file://`) and
    regenerates `entries.js`, which `index.html` reads to build the entry
    list.
  - `index.html` — the blog homepage, links to every generated
    `entry-N/*.html`.
  - `about.html` — hand-written (not generated), a short bio + photo +
    links (GitHub, LinkedIn, Open Football Project) + the PDF download.
    Linked from the homepage header.
  - `build_pdf.py` — run after `build.py`: `python3 build_pdf.py`.
    Renders every entry's concepts + exercise into one print-styled HTML
    page and prints it to `systems-and-software-concepts.pdf` via
    headless Chrome (`google-chrome-stable`). Linked from the homepage
    header and from `about.html`.
- `practice/` — Java (Maven, JUnit 5 + AssertJ) exercise sprint. Its own
  `DayN.java`/`DayNTest.java` naming predates the blog rebrand and is
  unrelated to the `entry-N` web folders — leave it as-is, see
  `practice/EXERCISES.md`.
  - `EXERCISES.md` — the 7-day interview-prep plan and checklist, source of
    truth for what's done vs pending.
  - `src/main/java/org/example/DayN.java` — implementations.
  - `src/test/java/org/example/DayNTest.java` — tests (TDD: written first).

## Working session workflow

1. Create `web/entry-N/` and write/refine `concepts.md` — theory for the
   entry.
2. Pick one exercise from `practice/` for `entry-N/exercise.md`:
   - Already implemented → just write it up (problem, existing tests, existing
     code). No new coding.
   - Not yet implemented → do a short TDD cycle: SPECIFY the problem in
     plain language, propose failing tests (RED) and wait for approval,
     then implement (GREEN). Don't weaken tests to force a pass.
3. Run `python3 web/build.py` to regenerate the static pages and entry list.

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
- **Day 2** (2026-08-16): `web/day-2/concepts.md` — OOP pillars (objects,
  classes, abstraction, encapsulation, inheritance, polymorphism), a UML
  class diagram (`Shape`/`Circle`/`Rectangle`) tying all four pillars
  together, covariant return types, abstract class vs interface.
  `web/day-2/exercise.md` — GCD (Euclidean algorithm by subtraction) and
  LCM, picked from `practice` Day1 (already implemented); includes a
  subtraction-vs-modulo complexity comparison and the overflow gotcha in
  `lcm`.
- **Day 3** (2026-08-17): `web/day-3/concepts.md` — SOLID (code example per
  principle, including the classic `Square extends Rectangle` LSP
  violation) and design patterns (State, Strategy, Proxy, Decorator,
  Facade, Factory, Builder; code examples for Strategy and Decorator,
  and the Proxy-vs-Decorator intent distinction). `web/day-3/exercise.md`
  — longest substring without repeating characters (sliding window,
  O(n)), picked from `practice` Day3 (already implemented).
- **Day 4** (2026-08-18): `web/day-4/concepts.md` — Big O (time vs space
  complexity, Big O/Ω/Θ), the complexity-class chart (`big-O.jpg`),
  calculation rules (sequential adds, nested multiplies, drop constants
  and lower-order terms), amortized time via `ArrayList` resizing, and
  recursive complexity via the Fibonacci call tree (O(2^N) time, O(N)
  stack space). `web/day-4/exercise.md` — reverse a singly linked list,
  picked from `practice` Day4 (iterative already implemented); the
  recursive version was written directly in the article for the space
  comparison (O(1) vs O(N) call stack) rather than added to `practice`.
- **Day 5** and **Day 6**: published (`web/day-5`, `web/day-6`) but never
  logged here — gap predates this entry, not an oversight going forward.
- **Day 6 extra** (2026-08-20): `web/day-6-extra/concepts.md` — bit
  manipulation (shift operators incl. `>>` vs `>>>` sign handling, AND/OR/
  XOR/NOT truth table, bit tricks: clear/toggle/check/set a bit, find the
  non-duplicate via XOR) and why `String +=` in a loop is O(N²) vs
  `StringBuilder`'s O(N), with the full series-to-Big-O derivation.
  `web/day-6-extra/exercise.md` — rotate an array in place by k (reversal
  trick), picked from `practice` Day4 (already implemented, `rotateArray`
  was unused in the blog until now).
- **Entries 7–12**: published (`web/day-7` through `web/day-12` at the
  time) but never logged here individually — same pre-existing gap as
  Day 5/6 above.
- **Rebrand** (2026-09-03): the blog outgrew "Java, One Concept at a
  Time" — Entry 9 (sharding/replication/indexing) and Entry 12 (reactive
  Spring, event-driven architecture, API gateways, security internals)
  are senior-round systems-design material, not Java fundamentals.
  Renamed to "Systems & Software Concepts", moving off `dailyjava.blog`
  onto `softwaresystems.blog`. Every `web/day-N/` folder was renamed to
  `web/entry-N/` (`git mv`, history preserved), each `concepts.md` H1
  changed from `# Day N: ...` to `# Entry N: ...` (two titles that named
  "Java" directly were reworded: Entry 0 is now "Inside the JVM", Entry 2
  extra is now "Collections, Enums, Exceptions & GC" — prose inside those
  pages still says "Java" where it's factually accurate, only the
  title/branding layer was scrubbed). `build.py`, `_template.html`, and
  `index.html` were updated to match: `days.js` is now `entries.js`, the
  `day` field is now `number`. The `practice/` project (Java source,
  `DayN.java` naming) was deliberately left untouched — the rename is
  scoped to the public `web/` blog and its branding, not the exercise
  sprint's internal naming.
- **Closed at 20** (2026-09-13): Entry 21 (Test-Driven Development /
  Binary Search) was built, then pulled — the blog closes at Entry 20 by
  decision; see the "DROPPED" note in `PLAN_xpdriven_audit.md` for
  detail, dropped content kept locally (gitignored) under
  `.dropped-entry-21/` rather than deleted outright, in case it's picked
  back up later. Added `about.html` (bio + `about-photo.jpg` + Open
  Football Project link + PDF download, see the policy update above) and
  `build_pdf.py` (whole blog as one downloadable PDF, linked from the
  homepage header and `about.html`).
