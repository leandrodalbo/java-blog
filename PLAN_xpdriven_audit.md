# Plan: folding xpdriven-blog content into the blog

Source audit: `xpdriven-blog/src/assets/articles-content/*.ts` (9 articles).
Kept the technical/process content useful for modern software engineering,
dropped `Intro` (site landing copy) and `CodingMonkeys` (AI-replaces-coders
opinion piece, already covered more rigorously by entry 18's TDCG).

3 new entries, one per working session/day, following the usual workflow
(concepts.md, then exercise via SPECIFY -> RED -> GREEN, then
`python3 web/build.py`).

## Entry 19: Software Paradigms — DONE (2026-09-12, concepts.md + exercise.md, built)

Source: `SFParadigms.ts`, reframed away from the "can AI do this" rhetorical
framing, kept the technical substance.

- Structured Programming (Dijkstra, GOTO elimination, decomposition into
  provable units)
- Object-Oriented Programming (abstraction/encapsulation, polymorphism,
  dependency inversion — cross-references entry 3's code instead of
  repeating it)
- Functional Programming (lambda calculus roots, immutability, pure
  functions — cross-references entry 10's threading exercise)

**Exercise — DONE: Streams refactor of `Day2.freqTable`.** Added
`freqTableStreams` alongside the existing imperative one
(`practice/.../Day2.java`), test `shouldBuildAFreqTableWithStreams` in
`Day2Test.java` (passing), and `entry-19/exercise.md` with an honest
"was it worth it?" section: the streams version still mutates the array
in `forEach`, so it isn't actually side-effect-free, and a genuinely
pure version would require changing the return type and its callers.

## Entry 20: Software Design, Clean Code & Working Together — DONE (2026-09-13, concepts.md + exercise.md, built)

Source: `softwaredesign.ts` + `WhatIsCleanCode.ts` + `CodeReadabilityMatters.ts`,
with a short Agile-values section folded in (from `AgileManifesto.ts`,
per your call to merge rather than give Agile its own entry or drop it).

- The cost of bad design (every added line gets more expensive, more
  developers != more productivity)
- What clean code means and why (KISS, meaningful names, small
  single-purpose functions — cross-ref entry 3's SRP)
- Code reviews and pair programming
- Working as a team: the four Agile values as principles behind the
  above, one line noting Scrum/Kanban are common ways teams structure
  that, out of scope here

**Exercise — DONE: `Arrays.merge` test retrofit.** Added
`ArraysTest.java` (5 cases: equal length, different lengths, one empty,
duplicates, negatives), all passing against the existing implementation
unchanged, and `entry-20/exercise.md` framing it honestly as retrofitting
tests onto pre-existing code rather than textbook test-first TDD.

## Entry 21: Test-Driven Development — NOT STARTED

Source: `WhatIsTDD.ts`, Kotlin example replaced with Java, cross-references
entry 18 (TDCG applies this same loop to AI-generated code).

- What TDD actually is (tests before implementation, XP origin)
- The cycle: RED -> GREEN -> REFACTOR
- Self-tested code (confidence + documentation that can't silently rot)
- Common objections, answered plainly
- Where it's a natural fit vs where it's harder

**Exercise: Binary search**, full SPECIFY -> RED -> GREEN cycle (find a
target's index in a sorted array, plus first/last occurrence). Not yet
implemented in `practice/` — this is a real TDD cycle, not a write-up of
existing code. O(log n) time, O(1) space.

## Not used from xpdriven-blog

- `Intro.ts` — site landing copy, not a concept entry.
- `CodingMonkeys.ts` — AI-replaces-programmers opinion piece, thin,
  already covered more rigorously by entry 18.
