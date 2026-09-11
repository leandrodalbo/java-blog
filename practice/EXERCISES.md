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

## Day 5 — Stacks + Searching

- [x] **Valid parentheses / balanced brackets** for `()[]{}`. O(n) time/space.
- [ ] **Binary search.** Find a target's index in a sorted array, and the first/last position of a target (the classic follow-up). O(log n) time, O(1) space — same complexity class as the B-tree index lookups from Day 9's concepts write-up.

## Day 6 — Array Techniques

- [x] **Product of array except self.** Build a result array where each slot is the product of every other element, without division. O(n) time, O(1) extra space (excluding the output array) using a prefix-pass then a suffix-pass.
- [ ] **Majority element** (Boyer-Moore voting). Find the element appearing more than n/2 times. O(n) time, O(1) space — a single counter and candidate, no auxiliary structure.

## Day 7 — Concurrency (dedicated day, this is the section senior rounds lean on hardest)

- [ ] **Producer-consumer** using `BlockingQueue`. Be ready to also sketch the raw `wait`/`notify` version verbally even if you don't code it.
- [x] **Thread-safe counter**, compared three ways: `synchronized`, `AtomicInteger`, `LongAdder`. Know when `LongAdder` wins (high-contention writes, don't need strict read consistency).

## Day 8 — OOD + Java specifics

- [x] **LRU cache** from scratch (HashMap + doubly linked list), O(1) get/put.
- [ ] **Comparable / Comparator.** Sort a list of custom objects two ways: implement `Comparable<T>` for natural ordering, then a separate `Comparator` (via `Comparator.comparing().thenComparing()`) for a different field order.

## Day 9 — OOD + Concurrency

- [ ] **Rate limiter** (token bucket), thread-safe under concurrent `tryAcquire()`.
- [ ] **Thread-safe Singleton** with double-checked locking. Ties back to Day 1's `volatile`: without it, the double-checked check can observe a partially-constructed instance.

## Day 10 — Java specifics + cold review (rehearsal day)

- [ ] **Streams pipeline refactor.** Take one Day1–Day3 imperative loop (e.g. `freqTable`) and rewrite with the Streams API; be ready to explain when streams are the *wrong* choice.
- [ ] **Immutable class with a validating Builder.** Defensive copies in/out, `final` fields, no setters.
- [ ] **Cold re-solve, timed.** Pick 2 exercises from Day1–9 at random, solve from scratch in ~20 min each without looking at your old code. This is the closest thing to interview pressure you can simulate alone — don't skip it.

---

## Bonus backlog (only if energy allows — not required for the 7 days)

- [ ] Zero matrix (O(1) extra space version)
- [?] String compression
- [ ] Remove duplicates from an unsorted linked list without extra space (O(n²)/O(1))
- [ ] Queue implemented with two stacks
- [x] Min-stack (O(1) `getMin`)
- [ ] Parking lot or elevator system (OOD)
- [ ] Climbing stairs (DP, bottom-up tabulation — count distinct ways to climb n stairs taking 1 or 2 steps at a time)
- [ ] Coin change (DP — fewest coins to make an amount; classic unbounded-knapsack shape, good contrast with climbing stairs)
- [?] Maximum subarray sum (Kadane's algorithm — O(n) single pass, a DP recurrence in disguise)
- [x] Group anagrams (hashmap grouping by sorted-string or char-count key, same family as the permutation-check exercise)
- [ ] Merge intervals (sort + sweep over an array of ranges, common array/interval pattern)

---

## Blog write-up backlog

Implemented (and, unless noted, tested) but not yet picked for a `web/entry-N/exercise.md`. Check off and move to "used" once an entry uses it.

- [x] `Day3.stringReverse` — reverse a string, tested (`shouldReverseAString`) — used in entry-18
- [x] `Day3.isAPalindromePermutation` — check if a string's characters can be rearranged into a palindrome, tested (`shouldCheckPalindromePermutations`) — used in entry-17
- [ ] `Arrays.merge` — merge two sorted arrays, implemented but **no test yet**, needs a RED/GREEN cycle before it can be written up

