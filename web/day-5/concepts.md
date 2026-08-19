# Day 5: Data Structures are important

## Hash Tables

A data structure for storing key-value pairs so you can look them up fast.
On average, look-ups stay **O(1)** as it grows, because the array resizes
once it gets too full.

### How are they implemented?

An array, where each slot holds a small linked list instead of a single
value:

1. A **hash function** turns the key into a number: `hashCode()` in Java.
2. That number is used to get a valid index: `hashCode % arraySize`.
3. The key-value pair is added to the linked list at that index.

```
key ---> hashCode() ---> hashCode % arraySize ---> linked list at that index
```

### Collisions

Two different keys can land on the same index. That's a **collision**.
Because each slot is a linked list, not just one value, it simply grows:

```
index   linked list
  0     -> ["apple", 3] -> null
  1     -> null
  2     -> ["banana", 7] -> ["grape", 1] -> null   <- collision: two keys, same slot
  3     -> ["kiwi", 9] -> null
```

Looking up `"grape"` means: hash it to index `2`, then walk that list comparing keys until `"grape"` is found.

### Why it's O(1) on average, not always

- **Best case, O(1).** A good hash function spreads keys evenly, so each
  bucket holds very few entries, often zero or one. Hash it, jump to the
  bucket, done.
- **Worst case, O(N).** If every key lands in the same bucket, it turns
  into one long linked list, and lookup becomes a plain linear scan.

## Array

- **Fixed size**, set when the array is created.

- Elements sit in **contiguous memory**, so any index can be reached
  directly: `address = base + index * elementSize`: **O(1) access**.

- Inserting or removing in the middle means shifting other elements: **O(N)**.

## List (`ArrayList`)

A resizable array, same O(1) indexed access as a plain array, plus growth.

- **O(1) access** by index, same reason as an array (contiguous memory).

- **Amortized O(1) append.** Usually adding an element is instant. When the
  array is full, every element gets copied into a bigger one, which is
  O(N). That copy is rare enough that, on average, each insert still costs
  O(1).

- **O(N) insert/remove in the middle**, everything after the gap has to
  shift over.

## Linked List

A linear structure of nodes, each holding a value and a pointer to the
next node (a **doubly** linked list also keeps a pointer to the previous
one).

- **No random access.** Reaching the *i*-th node means walking from the
  head, one `next` at a time. That's **O(N)**.

- **O(1) insert/remove at the head** (and at the tail too, if a tail
  pointer is kept). Just need to rewire a couple of pointers, nothing shifts.

- **Removing a node you already have a reference to is O(1)**: point the
  previous node's `next` straight at the following node. Finding that node
  in the first place is still O(N).

## Stacks

**LIFO** means Last In, First Out. Think of a stack of plates: you only
ever add or take from the top.

- Operations: `push` (add to top), `pop` (remove from top), `peek` (look at
  the top without removing it), `isEmpty`.

- All four operations are **O(1)**, but only because they only ever touch the top.

## Queues

**FIFO** means First In, First Out. Think of a line at a store: first to join, first to be served.

- Operations: `add` (enqueue, to the back), `remove` (dequeue, from the front), `peek` (look at the front without removing it), `isEmpty`.

- Same shape as a stack: `add`/`remove`/`peek` are **O(1)** because they only ever touch the two ends.
