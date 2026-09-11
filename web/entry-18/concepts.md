# Entry 18: TDCG, Using AI safely

## The problem with a bare prompt

AI can generate the implementation I need much faster than I can write it by hand. Faster is not the same as correct, and it is not the same as safe. An implementation that looks right but was never asked to prove itself is a promise, not a guarantee.

**Test-Driven Code Generation (TDCG)** is my method for keeping AI-assisted work honest. It takes the core discipline of TDD and applies it to a workflow where the AI is the one writing both the test and the implementation.

> The AI generates. The human decides what is right and what is not.

## The loop

```
1. SPECIFY  - agree on one behavior, in plain language
2. RED      - AI generates failing tests, human reviews and approves
3. PROMPT   - the approved tests become the spec for the implementation
4. GREEN    - AI generates the implementation, tests pass
5. REVIEW   - check naming, responsibilities, duplication, dead code
6. REFACTOR - apply what review found, tests stay green throughout
7. COMMIT   - one small, focused commit with a clear why
```

## Why each step earns its place

- **SPECIFY** forces one small behavior at a time.

- **RED** happens before any implementation exists. If a human never reads the test, they never really own the specification; they are just trusting the AI's guess at what "done" means.

- **PROMPT** hands the approved tests back to the AI as the spec, along with a reference file or existing pattern to follow.

- **GREEN** only passes because the tests already say what correct looks like.

- **REVIEW** Passing tests say nothing about naming, duplication, or whether the design fits the rest of the codebase, so an experienced human still has to read the implementation.

- **REFACTOR** Change what we found during the review: renaming, removing duplication, simplifying. Tests stay green throughout; if a refactor breaks one, the refactor is wrong, not the test.

- **COMMIT** stays small on purpose. A small, tested, reviewed change is easy to understand and easy to undo if something goes wrong.

## Where it fits, and where to be careful

Works well when:

- the codebase already has clear patterns to follow
- what to add is clear, even when the implementation itself is not trivial

Be more careful with:

- greenfield design, there is no existing pattern for the AI to anchor to, so the specify step has more room to drift

- performance and security-sensitive code, since passing tests prove the behavior asked for, not that it is fast enough or safe against a determined attacker

## The habit, not just the steps

The point is to stop generated implementations from getting into a codebase without proof that it is doing what it needs to do, and without a human who actually read them. TDCG just turns that into a habit that is hard to skip by accident.
