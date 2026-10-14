# Systems & Software Concepts — Interview-Prep Blog

## What this is

Leandro revisiting software fundamentals and systems design, written the way he wishes they'd been explained the first time. One entry = theory notes + (usually) one exercise write-up. Published as a small static blog at `softwaresystems.blog` (formerly "Java, One Concept at a Time" on `dailyjava.blog`). Started Java-only and grew into backend/systems (Spring internals, sharding, event-driven); code examples stay Java. Readable for beginners, not shallow for seniors: correct terms, diagrams where they help, honest complexity analysis.

**Status: closed at Entry 20** (2026-09-13), by decision.

## Public repo — handle with care

- No credentials/tokens ever. Never add `.idea`/`target`, even on "add everything".
- Personal info allowed: footer byline, and in `about.html` a short bio, headshot (`about-photo.jpg`), skill summary, Open Football Project link.
- Deliberately excluded: employer/recruiter names and sectors (asked and declined). Keep to skills and general years of experience. Ask before adding anything more (employers, clients, address, phone/email).

## Structure

- `web/` — the blog. Fully static, works via `file://`. **Source of truth is the `.md` files**; `.html` and `entries.js` are generated, never hand-edit.
  - `entry-N/concepts.md` — theory. First line `# Entry N: Title` (the prefix drives the homepage label).
  - `entry-N/exercise.md` — problem, tests, implementation, from `practice/`.
  - `_template.html` — page shell (Tailwind + marked via CDN).
  - `build.py` — run after any `.md` edit (`python3 web/build.py`). Renders each `.md` to a sibling `.html` (markdown inlined as base64, since `fetch()` breaks under `file://`) and regenerates `entries.js` for `index.html`.
  - `about.html` — hand-written bio + photo + links (GitHub, LinkedIn, Open Football Project) + PDF download.
  - `build_pdf.py` — run after `build.py`. Prints all entries to `systems-and-software-concepts.pdf` via headless `google-chrome-stable`. Linked from the homepage header and `about.html`.
  - `build_docx.py` — one-off editable `.docx` of the whole blog (for reorganizing in Google Docs). Not published.
- `practice/` — Java (Maven, JUnit 5 + AssertJ) exercises. `DayN.java`/`DayNTest.java` naming predates the rebrand and is unrelated to `entry-N`; leave it.
  - `EXERCISES.md` — the 7-day plan/checklist, source of truth for done vs pending.
  - Tests in `src/test/...` are written first (TDD).

## Working session workflow (if the blog reopens)

1. Create `web/entry-N/`, write `concepts.md`.
2. Pick one `practice/` exercise for `exercise.md`:
   - Already implemented → write it up, no new code.
   - Not implemented → short TDD cycle: SPECIFY, propose failing tests (RED) and wait for approval, implement (GREEN). Never weaken tests.
3. `python3 web/build.py`.

Start easy and ramp up: early exercises explainable to a beginner in minutes, with a senior angle (complexity, trade-offs) called out.

## History

- **2026-08-14 → 08-20:** entries 0–6 extra (JVM, basics, OOP, SOLID/patterns, Big O, data structures, graphs/trees, bit tricks/StringBuilder). Entries 5, 6 and 7–12 were never logged individually.
- **2026-09-03 rebrand:** entries 9 (sharding/replication/indexing) and 12 (reactive Spring, event-driven, gateways, security) were senior systems material, so renamed to "Systems & Software Concepts" on `softwaresystems.blog`. `web/day-N/` → `web/entry-N/` (`git mv`), H1s `Day N` → `Entry N`; Entry 0 retitled "Inside the JVM", Entry 2 extra "Collections, Enums, Exceptions & GC" (prose still says "Java" where accurate). `days.js` → `entries.js`, field `day` → `number`. `practice/` untouched.
- **2026-09-13 closed at 20:** Entry 21 (TDD / Binary Search) built then pulled. Added `about.html` and `build_pdf.py`. (The `PLAN_xpdriven_audit.md` note and the local `.dropped-entry-21/` copy no longer exist; the plan's deletion isn't committed yet.)
