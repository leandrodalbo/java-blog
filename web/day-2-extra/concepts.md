# Day 2 extra: Remaining Java Concepts

## Java Collections Framework

A unified set of interfaces and classes for storing and manipulating
groups of objects.


```
                Iterable
                    △
                    |
                Collection
        ┌───────────┼───────────┐
        |           |           |
       List        Set        Queue
        |           |           |
   ArrayList    HashSet    PriorityQueue
   LinkedList   TreeSet    LinkedList (also a Deque)
```

`Map` is **not** a `Collection` — it stores key-value pairs, not a
sequence of single elements, so it sits in its own branch of the
hierarchy with `HashMap`, `LinkedHashMap`, `TreeMap`.

- **List** — allows duplicates, indexable. `ArrayList` is
  backed by a resizable array (O(1) get by index, O(n) insert/remove in
  the middle). `LinkedList` is a doubly linked list (O(1) insert/remove
  at the ends, O(n) get by index).
- **Set** — no duplicates. `HashSet` (no order guarantee, O(1) average
  add/contains via hashing), `LinkedHashSet` (preserves insertion
  order), `TreeSet` (sorted, O(log n) operations, backed by a red-black
  tree).
- **Queue** — FIFO by default (`LinkedList` used as a `Queue`).
  `PriorityQueue` is the exception: elements come out in priority
  order, not insertion order, so it's a min-heap under the hood, not a
  FIFO structure.
- **Map** — key-value pairs, keys unique. Same `Hash`/`LinkedHash`/`Tree`
  split as `Set` (a `HashSet` is, in fact, implemented as a `HashMap`
  under the hood with dummy values).
- **Iterator** — the contract every `Collection` implements to be walked
  element by element without exposing its internal structure:

```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String value = it.next();
    if (shouldRemove(value)) {
        it.remove(); // safe removal during iteration
    }
}
```

## Enums

A type-safe way to represent a fixed set of constants — instead of
`int` or `String` constants (which the compiler can't validate), an
`enum` gives you a closed set of values the compiler *does* check.

```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public boolean isWeekend() {
        return this == SATURDAY || this == SUNDAY;
    }
}
```

Each constant is a singleton instance of the enum type, so `enum`
values can carry fields, constructors, and methods — not just names.
A `switch` over an enum lets the compiler warn when a case is missing,
something plain `int`/`String` constants can never give you.

## Exceptions

An event that interrupts a program's normal execution. It can be
handled where it occurs, or propagated up the call stack for a caller
to handle.

| Category      | When it's known               | Examples                                    | Must be declared/caught? |
|----------------|-------------------------------|----------------------------------------------|----------------------------|
| **Checked**    | Compile time                  | `IOException`, `SQLException`                | Yes — `throws` or `try/catch`, enforced by the compiler |
| **Unchecked**  | Runtime                       | `NullPointerException`, `ArrayIndexOutOfBoundsException` | No — extends `RuntimeException`, compiler doesn't enforce handling |
| **Error**      | Runtime, usually unrecoverable | `OutOfMemoryError`, `StackOverflowError`     | No — not meant to be caught, the program generally can't recover |

```java
try {
    riskyOperation();
} catch (IOException e) {
    // handle or wrap and rethrow
} finally {
    // always runs, even if a return happens above
}
```

`Checked` vs `Unchecked` is a design choice about the exception class
itself (does it extend `Exception` or `RuntimeException`), not about
the situation that caused it — that's why the compiler can enforce
checked exceptions but not unchecked ones.

## Garbage Collection

The JVM automatically reclaims heap memory occupied by objects that no
longer have any reachable reference — no manual `free()` like in C.

- An object becomes eligible for collection once nothing reachable
  from a GC root (local variables on the stack, static fields, active
  threads) points to it, directly or transitively.
- Collection runs on a **daemon thread**: a background thread that
  doesn't keep the JVM alive by itself, so the program can still exit
  normally even while GC is running.
- Most JVMs use **generational** collection: new objects go into a
  small "young generation" that's collected frequently and cheaply
  (most objects die young); objects that survive several collections
  get promoted to the "old generation," collected less often. This
  split is why GC is usually fast in practice despite scanning a large
  heap — most of the heap is rarely touched.
