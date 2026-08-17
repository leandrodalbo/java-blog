# Day 3: SOLID and shiny day, also some design patterns

## SOLID

Managing **coupling and cohesion**: related things grouped together
(cohesion), unrelated things kept from leaning on each other's
internals (coupling).

### Single Responsibility Principle (S)

A class should have only one reason to change. Uncle Bob's framing:
a module should answer to **one** actor/part of the business, if two
different stakeholders could ask for a change to the same class for
unrelated reasons, it's doing too much.

```java
// violates SRP: persistence + formatting + business rules, all in one class
class Invoice {
    double calculateTotal() { /* ... */ }
    void saveToDatabase() { /* ... */ }
    String formatAsPdf() { /* ... */ }
}

// SRP: each reason to change gets its own class
class Invoice {
    double calculateTotal() { /* ... */ }
}
class InvoiceRepository {
    void save(Invoice invoice) { /* ... */ }
}
class InvoicePdfFormatter {
    String format(Invoice invoice) { /* ... */ }
}
```

### Open/Closed Principle (O)

A class should be **open for extension, closed for modification**. Add
new behaviour by writing new code (a new subclass/implementation), not
by editing code that already works and is already tested.

```java
// violates OCP: every new shape means editing this method again
double area(Object shape) {
    if (shape instanceof Circle c) return Math.PI * c.radius() * c.radius();
    if (shape instanceof Rectangle r) return r.width() * r.height();
    // adding Triangle means coming back here
    return 0;
}

// OCP: each shape knows its own area, area() never changes again
interface Shape {
    double area();
}
class Circle implements Shape {
    public double area() { return Math.PI * radius * radius; }
}
class Rectangle implements Shape {
    public double area() { return width * height; }
}
```

### Liskov Substitution Principle (L)

Wherever code expects the base type, a subtype must be usable in its
place **without breaking correctness**. A subclass shouldn't strengthen
preconditions or weaken postconditions the caller relies on. 

### Interface Segregation Principle (I)

Don't force a class to implement methods it has no use for. Many small,
focused interfaces beat one large one.


### Dependency Inversion Principle (D)

High-level modules (business logic) shouldn't depend on low-level
modules (implementation details like a specific database or HTTP
client), both should depend on an **abstraction**. This is what makes
the low-level piece swappable and the high-level piece testable in
isolation.

```java
// violates DIP: the service is welded to one concrete implementation
class NotificationService {
    private final EmailSender sender = new EmailSender();
    void notify(String msg) { sender.send(msg); }
}

// DIP: depend on an interface, the concrete class is injected
interface MessageSender {
    void send(String msg);
}

class NotificationService {
    private final MessageSender sender;
    NotificationService(MessageSender sender) { this.sender = sender; }
    void notify(String msg) { sender.send(msg); }
}
```

`EmailSender` or `SmsSender` can now be swapped — or replaced with a
mock in a test — without touching `NotificationService`.

## Useful Design Patterns

### State

The behaviour of an object changes depending on its internal state,
without a wall of `if`/`switch` statements at every call. Each
state is its own class implementing a shared interface, and the object
delegates to whichever one it currently holds.

### Strategy

Lets an algorithm be swapped at runtime by depending on an interface
instead of a hard-coded implementation. Structurally identical to
State, but the intent differs: Strategy picks *how* to do something
once, State reacts to transitions *over time*.

```java
interface DiscountStrategy {
    double apply(double price);
}
class NoDiscount implements DiscountStrategy {
    public double apply(double price) { return price; }
}
class TenPercentOff implements DiscountStrategy {
    public double apply(double price) { return price * 0.9; }
}

class Checkout {
    private final DiscountStrategy discount;
    Checkout(DiscountStrategy discount) { this.discount = discount; }
    double total(double price) { return discount.apply(price); }
}
```

Swapping `new NoDiscount()` for `new TenPercentOff()` at construction
time changes the checkout's behaviour with zero changes to `Checkout`
itself. Same shape as the Open/Closed example above.

### Decorator

Adds behaviour to an individual object at runtime by wrapping it in
another object that shares its interface. An alternative to
subclassing when the combination of behaviours needed would otherwise
require a new subclass for every combination.

```java
interface Coffee {
    double cost();
}
class BlackCoffee implements Coffee {
    public double cost() { return 2.0; }
}
class WithMilk implements Coffee {
    private final Coffee inner;
    WithMilk(Coffee inner) { this.inner = inner; }
    public double cost() { return inner.cost() + 0.5; }
}
class WithSugar implements Coffee {
    private final Coffee inner;
    WithSugar(Coffee inner) { this.inner = inner; }
    public double cost() { return inner.cost() + 0.2; }
}

// stack decorators to combine behaviour: 2.0 + 0.5 + 0.2 = 2.7
Coffee order = new WithSugar(new WithMilk(new BlackCoffee()));
order.cost();
```

### Facade

A single, simplified interface in front of a complex subsystem. The
caller talks to the facade and never sees the tangle of classes it
coordinates underneath.

### Factory

Wraps the constructor call behind a method, so the caller asks for
"a thing that satisfies this interface" instead of naming a concrete
class directly. It is useful when construction involves choosing between
several implementations or needs setup logic beyond `new`.

### Builder

Constructs an object step by step through a chain of setters, useful
when a constructor would otherwise need a long list of parameters.
Each call returns `this`, so calls read as a fluent chain ending in `.build()`.
