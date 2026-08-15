# Day 1: All Basic Concepts Together

## main method: The program starting point

```
public static void main(String[] args){}
   |      |     |    |             |
   |      |     |    | List of arguments passed to the program
   |      |     |    |
   |      |     | The name the JVM looks for, it must match this exactly
   |      |     |
   |      | Returns nothing to the JVM
   |      |
   | The JVM calls it without having to create an instance.
   |
   Must be reachable from outside the class, the JVM is the one calling it
```

This exact signature is a contract with the JVM: change any part of it
(rename `main`, drop `static`, make it return something) and the JVM won't
recognize it as an entry point. It'll compile fine but fail at launch.

## Pool of Strings

- A place in memory to store all the strings

- Strings are immutable, whenever a new string is created, the JVM will check the
pool and if a matching string is found it will return the reference to that object.

- String literals like "foo" use the String pool. new String("foo") creates a new heap object, even though "foo" itself is pooled. So new String("a") == "a" is false, while "a" == "a" is true. Use .intern() to get the pooled reference.


## Packages

- used to organize the code and prevent conflicts


## Access Modifiers:

- public: access globally
- private: access only within the same class
- protected: access within the same package and subclasses
- default (no keyword): access only within the same package — narrower than `protected`, no subclass-outside-the-package access


## Data Types

| Primitives | size    | wrapper     |
|------------|---------|-------------|
| boolean    | 1 bit*  | `Boolean`   |
| char       | 2 bytes | `Character` |
| byte       | 1 byte  | `Byte`      |
| short      | 2 bytes | `Short`     |
| int        | 4 bytes | `Integer`   |
| long       | 8 bytes | `Long`      |
| float      | 4 bytes | `Float`     |
| double     | 8 bytes | `Double`    |


Wrappers encapsulate a primitive in an object, so they're usable where an
object is required (generics, collections — you can't have a `List<int>`,
only `List<Integer>`). They're `final` and immutable, same as `String`.

Non-primitives (reference types): `String`, arrays, and any `class` you
define. A variable of a reference type holds a reference to an object on
the heap, not the value itself — that's the real primitive/non-primitive
split, not the size.


## String

- An abstraction to store a sequence of characters
- Immutable — every "modification" (`concat`, `substring`, ...) returns a
  new `String` rather than changing the original. That's what makes string
  pooling and safe sharing across threads possible in the first place.

- StringBuffer thread safe (synchronized methods)
- StringBuilder not thread safe, but faster — use it unless multiple threads touch the same instance


## Array:

- A data structure to store a sequence of elements
- It can have multiple dimensions (matrix)
- Elements can be accessed using the index ==> O(1)
- size is fixed


## Class Variable vs Instance Variable

- Class variables are `static`. One copy shared by every instance of that class.
- Instance variables are non-`static`. Each instance gets its own copy; it's part of that object's state. (Access modifier is a separate, independent choice — `private` is just the usual convention for encapsulation, not what makes it an instance variable.)


## Constructors

- Special methods used to create an instance of a class
- Same name as the class, no return type (not even `void`)
- If you don't write one, the compiler generates a no-arg default constructor 
- Can be overloaded (multiple constructors, different parameter lists) to support different ways of building the object

## Volatile Keyword

- Every read/write goes straight to main memory instead of a thread-local CPU cache, so a write on one thread is immediately visible to reads on other threads.
- It only guarantees **visibility**, not **atomicity**. `count++` on a `volatile int` is still read-modify-write — two threads can interleave and lose an update. For that you need `synchronized` or `AtomicInteger`, not `volatile`.

## I/O Packages

- Perform Input output operations (read/write)
- Buffered streams reduce the number of operation to interact with devices
