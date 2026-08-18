# Reverse a Singly Linked List

Two ways to solve the same problem, worth comparing directly because
they land on the exact same time complexity but a different space
complexity.

## Problem

Given the head of a singly linked list, reverse it in place and return
the new head. Each node only knows its `next`, there's no `prev` to
fall back on:

```
1 -> 2 -> 3 -> 4 -> 5 -> null

becomes

5 -> 4 -> 3 -> 2 -> 1 -> null
```

## Tests (RED)

```java
@Test
public void shouldReverseIteratively(){
    Day4.ListNode head = fromArray(1, 2, 3, 4, 5);

    Day4.ListNode reversed = Day4.reverseIterative(head);

    assertThat(toArray(reversed)).isEqualTo(new int[]{5, 4, 3, 2, 1});
}

@Test
public void shouldReverseIterativelyWhenEmpty(){
    assertThat(Day4.reverseIterative(null)).isNull();
}

@Test
public void shouldReverseIterativelyWhenSingleNode(){
    Day4.ListNode head = fromArray(1);

    Day4.ListNode reversed = Day4.reverseIterative(head);

    assertThat(toArray(reversed)).isEqualTo(new int[]{1});
}
```

## Iterative Implementation (GREEN)

```java
/*
    reverse iteratively
    O(N) time, O(1) space
*/
public static ListNode reverseIterative(ListNode head) {
    ListNode prev = null;

    while (head != null) {
        ListNode next = head.next;
        head.next = prev;
        prev = head;
        head = next;
    }

    return prev;
}
```

Three pointers do all the work: `prev` (what's already reversed),
`head` (the node currently being processed) and `next` (saved before
it's overwritten, otherwise the rest of the list is lost the moment
`head.next` is reassigned). Each node is visited once and each pointer
update is O(1), so the loop is **O(N) time**. No extra structure grows
with the input, so it's **O(1) space**.

## Recursive Implementation

```java
/*
    reverse recursively: recurse all the way to the last node first,
    then rewire pointers on the way back up the call stack
    O(N) time, O(N) space (call stack)
*/
public static ListNode reverseRecursive(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    ListNode newHead = reverseRecursive(head.next);

    head.next.next = head;
    head.next = null;

    return newHead;
}
```

The recursion walks forward to the *last* node first — that becomes
`newHead`, unchanged all the way back up. Then, as each call returns,
it flips one link: `head.next.next = head` makes the next node point
back at the current one, and `head.next = null` clears the old forward
link (crucial on the original head, otherwise it'd still point forward
into the reversed list, forming a two-node cycle).

## Same time complexity, different space complexity

Both visit every node exactly once, so both are **O(N) time**. They
diverge on space:

- **Iterative** — O(1) space. Three pointers, reused on every
  iteration, regardless of how long the list is.
- **Recursive** — O(N) space. Every call stays on the call stack
  waiting for `reverseRecursive(head.next)` to return before it can
  rewire its own pointers — the same "how deep does the stack go"
  reasoning from today's Fibonacci example. A list of `n` nodes means
  `n` stack frames open at once, right before the base case is hit.

For a short list the difference doesn't matter. For a very long one,
the recursive version risks a `StackOverflowError` where the iterative
version would run fine.