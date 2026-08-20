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
