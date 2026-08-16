# Day 2: Object Oriented Programming

## Objects

- Every object is an instance of a class.
- An object has **state** and **behaviour**.
- State is made up of the instance fields.
- Behaviour is defined through methods.

## Classes

- A class is the template, or blueprint, used to create objects.
- `this` refers to the current object.
- `super` refers to the parent class (its constructor, fields, or methods).

## Abstraction

- Achieved using classes and interfaces.
- A class should expose only what's relevant to its users — the focus is on
  the **"WHAT"**, not the **"HOW"**.
- The user of a class needs to know its interface (the public contract),
  not its implementation details.
- Promotes loose coupling and high cohesion.

## Encapsulation

- Implemented by keeping an object's fields `private` and exposing access
  through methods (getters/setters, or better, behaviour that operates on
  the state directly).
- Hides the object's internal state, so the internal representation can
  change without breaking callers.

## Inheritance

- Lets a class be based on an existing one (`extends`) — the subclass
  gets the parent's fields and methods.
- Creates tight coupling: a change in the parent can ripple into every
  subclass, and deep hierarchies get hard to reason about and maintain.
  This is why "favor composition over inheritance" is a common piece of
  design advice — model "has-a" with a field instead of "is-a" with
  `extends` unless the is-a relationship is genuine and stable.

## Polymorphism

Gives objects the ability to be treated through a common type while
behaving differently underneath. Two flavors:

- **Overloading** — same method name, different parameter list. Resolved
  at **compile time** based on the declared argument types.
- **Overriding** — a subclass redefines a method it inherited. Resolved
  at **runtime**, based on the actual object type (dynamic dispatch).

### UML class diagram: all four pillars in one example

```
                +--------------------------------+
                |          <<abstract>>           |
                |              Shape              |
                +--------------------------------+|
                | # color: String                 |   <- encapsulated state
                +--------------------------------+|
                | + area(): double     {abstract} |   <- abstraction:
                | + describe(): String            |      contract, not implementation
                +--------------------------------+
                              △
                              |  inheritance (extends)
                +-------------+-------------+
                |                           |
      +-------------------+       +-----------------------+
      |      Circle        |      |      Rectangle        |
      +-------------------+       +-----------------------+
      | - radius: double   |      | - width: double       |
      +-------------------+       | - height: double      |
      | + area(): double   |      +-----------------------+
      +-------------------+       | + area(): double      |
                                  +-----------------------+
```

- `Shape` is abstract: it declares the contract (`area()`) without
  providing an implementation — abstraction.
- `color` is `protected`, accessible to subclasses but hidden from the
  outside world — encapsulation.
- `Circle` and `Rectangle` both `extend Shape` — inheritance.
- Calling `shape.area()` on a `Shape` reference runs `Circle`'s or
  `Rectangle`'s version depending on the actual object at runtime —
  polymorphism (dynamic dispatch).

## Covariant return types

A subclass overriding a method may narrow the return type to a subtype
of what the parent declared:

```java
class Animal {
    Animal reproduce() { return new Animal(); }
}

class Dog extends Animal {
    @Override
    Dog reproduce() { return new Dog(); } // narrows Animal -> Dog
}
```

This is still a valid override (not overloading) because the return type
is a subtype — callers that expect an `Animal` back are never surprised,
they just might get a more specific one.

## Abstract class vs interface

| Aspect         | Abstract class                                                        | Interface                                                                                    |
|-----------------|------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|
| Instantiation   | Cannot be instantiated directly                                       | Cannot be instantiated directly                                                                |
| Constructors    | Can have constructors, run via `super()` when a subclass is built     | No constructors                                                                                 |
| Fields          | Can hold real instance state, any access modifier                     | Fields are implicitly `public static final` — constants only, no instance state                |
| Methods         | Can mix abstract and concrete methods; zero abstract methods is legal | Before Java 8: every method implicitly abstract. Java 8+: `default`/`static` methods allowed    |
| Inheritance     | Single inheritance only (`extends` one class)                         | A class can implement multiple interfaces                                                       |
| When to use     | Related types that share state and some implementation                | An unrelated capability/contract that different types can opt into                              |

A common misconception: an abstract class does **not** need to declare
any abstract method — `abstract` just means "cannot be instantiated,"
even if every method already has a body.
