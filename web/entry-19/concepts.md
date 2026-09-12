# Entry 19: Software Paradigms

## Why paradigms matter

A paradigm defines how we organize the solution to a problem. How you break a problem into smaller parts, how those parts work together.

Most languages let you work and combine different paradigms. But the three below have had a huge influence, and you can still see them in everyday work.


## Structured Programming

Back when `GOTO` was common, Edsger Dijkstra argued that programs were difficult to understand when their control flow was too tangled. If a program could jump anywhere, understanding one line might require tracing through many other parts of the program.

Structured programming replaced these arbitrary jumps with three basic control structures:

* **Sequence**: run statements in order.
* **Selection**: choose between paths with `if`/`else`.
* **Iteration**: repeat code with `while`/`for`.

These structures make programs easier to understand because each block has a clear beginning and end.

```java
// unstructured: where you end up depends on history, not just this line
start:
if (done) goto end;
// ... do work ...
goto start;
end:

// structured: sequence, selection and iteration, each provable in isolation
while (!done) {
    // ... do work ...
}
```

## Object-Oriented Programming

OOP takes decomposition a step further by grouping **data and the behaviour that works on that data** into one unit: an **object**.

The object hides its internal details behind an interface. Code using the object only needs to know **what it can do**, not **how it does it**.

* **Abstraction and encapsulation** hide an object's internal details. 

* **Polymorphism** allows different implementations to be used through the same interface. Code that works with the interface does not need to change when a different implementation is plugged in. 

* **Dependency Inversion** takes this idea further: code should depend on abstractions rather than specific implementations. This makes implementations easier to replace and test.

OOP is not free. Abstractions can make implementations easier to change, but they can also make it harder to understand. 

## Functional Programming

Functional programming has its roots in **lambda calculus**, a model of computation developed by Alonzo Church in the 1930s, before electronic computers existed.

Lisp brought these ideas into a practical programming language and treated **functions as first-class values**. This means functions can be assigned to variables, passed as arguments, and returned from other functions.

The core idea is to use **pure functions** with **immutable data**:

* A pure function always produces the same output for the same input.

* It does not change anything outside itself.

* Immutable data cannot be changed after it is created.


```java
// imperative: a shared mutable accumulator, mutated across iterations
List<Integer> squares = new ArrayList<>();
for (int n : numbers) {
    if (n % 2 == 0) squares.add(n * n);
}

// functional: each step produces a new value, nothing is mutated in place
List<Integer> squares = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .toList();
```

Both produce the same result, but the imperative version mutates a shared list, while the functional version transforms values into a new result.

Avoiding shared mutable state can also make concurrent code easier to reason about, without relying as heavily on locks such as `synchronized` or `AtomicInteger`.


## Picking a Paradigm

None of these paradigms replaced the others; they work well together. A typical Java service might use structured programming within methods, OOP to organise modules, and functional programming for transformations that don't need shared state.

What matters is knowing **which paradigm you're using and why**, rather than trying to use one everywhere.
