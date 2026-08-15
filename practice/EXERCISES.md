# Java Interview Prep — 7-Day Sprint

Pace: 2 exercises/day, 3 on high-energy days, mixed topics each day (not one
subject at a time). Do a complexity analysis (time + space, worst case) for
every exercise, in a comment, like Day1–Day3.

**Honest framing:** 7 days of 2-3 exercises/day is enough for broad pattern
fluency across the topics that actually come up, and to talk complexity
confidently without freezing. It is not enough for deep concurrency/JVM
mastery — that's a longer game. The goal here is "nothing catches you off
guard," not "strongest person in the room on every topic."

Naming convention: keep using `DayN.java` / `DayNTest.java`, same as before.

Already done: Day1 (primes, GCD/LCM, palindrome), Day2 (factorial, fibonacci,
min/max, freq table, unique chars, permutation check), Day3 (longest substring,
palindrome permutation, two-sum, string reverse), Arrays.merge, URLify.

---

## Day 4 — Arrays + Linked Lists

- [x] **Rotate an array in place by k.** O(n) time, O(1) space (reversal trick, not a new array).
- [x] **Reverse a singly linked list**, iteratively and recursively. Compare space cost (O(1) vs O(n) call stack).

## Day 5 — Stacks + Trees

- [x] **Valid parentheses / balanced brackets** for `()[]{}`. O(n) time/space.
- [ ] **Validate a BST.** Classic bug to avoid: checking only immediate children isn't enough — bounds must propagate down the recursion.

## Day 6 — Graphs + Linked Lists

- [ ] **Number of islands** (grid BFS/DFS). O(rows × cols) time.
- [ ] **Detect a cycle in a linked list and find its start** (Floyd's tortoise/hare). O(n) time, O(1) space — know *why* the second phase finds the start, that's usually the actual follow-up question.

## Day 7 — Concurrency (dedicated day, this is the section senior rounds lean on hardest)

- [ ] **Producer-consumer** using `BlockingQueue`. Be ready to also sketch the raw `wait`/`notify` version verbally even if you don't code it.
- [ ] **Thread-safe counter**, compared three ways: `synchronized`, `AtomicInteger`, `LongAdder`. Know when `LongAdder` wins (high-contention writes, don't need strict read consistency).

## Day 8 — OOD + Java specifics

- [ ] **LRU cache** from scratch (HashMap + doubly linked list), O(1) get/put.
- [ ] **`equals`/`hashCode` contract exercise.** Write a class that breaks the contract, show it silently corrupting a `HashSet`, then fix it.

## Day 9 — OOD + Concurrency

- [ ] **Rate limiter** (token bucket), thread-safe under concurrent `tryAcquire()`.
- [ ] **Custom fixed-size thread pool** (mini `ExecutorService`): worker threads pulling off a shared queue, `shutdown()` vs `shutdownNow()`.

## Day 10 — Java specifics + cold review (rehearsal day)

- [ ] **Streams pipeline refactor.** Take one Day1–Day3 imperative loop (e.g. `freqTable`) and rewrite with the Streams API; be ready to explain when streams are the *wrong* choice.
- [ ] **Immutable class with a validating Builder.** Defensive copies in/out, `final` fields, no setters.
- [ ] **Cold re-solve, timed.** Pick 2 exercises from Day1–9 at random, solve from scratch in ~20 min each without looking at your old code. This is the closest thing to interview pressure you can simulate alone — don't skip it.

---

## Bonus backlog (only if energy allows — not required for the 7 days)

- [ ] Zero matrix (O(1) extra space version)
- [ ] String compression
- [ ] Remove duplicates from an unsorted linked list without extra space (O(n²)/O(1))
- [ ] Queue implemented with two stacks
- [ ] Min-stack (O(1) `getMin`)
- [ ] Lowest common ancestor in a plain (non-BST) binary tree
- [ ] Clone a graph (deep copy with cycles)
- [ ] Course schedule / topological sort (Kahn's algorithm)
- [ ] Parking lot or elevator system (OOD)

## Notes for yourself

- Keep the complexity-comment style you already use — it's exactly what you should say out loud in an interview.
- Ask for a review after each day the same way as Day1–Day3 — bugs/edge cases matter more than "it compiles."
