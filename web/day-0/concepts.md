# Day 0: How Java Actually Runs

## Platform independence: what do we actually mean?

"Write once, run anywhere" it means Java compiles to an intermediate format instead of compiling straight to a specific CPU's machine code.

- `javac` compiles your `.java` source into **bytecode** — a `.class` file. Bytecode is not native machine code; it's instructions for a virtual machine, the JVM.

- Any machine with a JVM built for its OS/CPU can load and run that same `.class` file. The portability lives in the JVM, not in your code. You ship one `.class` file, and a Mac, a Linux server, and a Windows box each run it through their own native JVM.

```
┌─────────────┐    ╭─────────────╮    ┌─────────────┐    ╭─────────────╮    ┌─────────────┐
│   Foo.java  │──▶│    javac    │──▶│  Foo.class  │──▶│     JVM     │──▶│ native code │
│  (source)   │    │  compiler   │    │ (bytecode)  │    │interpret/JIT│    │  (per CPU)  │
└─────────────┘    ╰─────────────╯    └─────────────┘    ╰─────────────╯    └─────────────┘

   [ file ]          (process)         [ file ]          (process)          [ file ]
```

## The JVM

The JVM (Java Virtual Machine) is the thing that actually executes your
program. It's more than "just" a converter, its responsibilities include:

- **Class loading**: finds and loads `.class` files, verifies the bytecode is well-formed and safe before running it.
- **Execution**: runs the bytecode, initially by *interpreting* it instruction-by-instruction.
- **JIT compilation**: see below.
- **Memory management**: allocates and garbage-collects objects 

### JIT (Just-In-Time compiler)

The JVM compiles the bytecode to native machine code at runtime, then
reuses that native version on later calls. 

## Memory: the JVM runtime data areas

Two of these areas are **shared by all threads**; two exist **per thread**.
That distinction matters. This is the reason why the heap needs synchronization
for thread safety and the stack doesn't.

```
                     JVM Process
  ┌─────────────────────────────────────────────────────────┐
  │  Shared by every thread                                 │
  │  ┌───────────────┐   ┌───────────────────────────────┐  │
  │  │     Heap      │   │   Method Area (Metaspace)     │  │
  │  │ all objects   │   │ class metadata, static fields,│  │
  │  │ and arrays    │   │ constant pool, method bytecode│  │
  │  └───────────────┘   └───────────────────────────────┘  │
  │                                                         │
  │  Per thread (one set each, created with the thread)     │
  │  ┌───────────────┐   ┌───────────────┐                  │
  │  │ Thread A stack│   │ Thread B stack│   ...            │
  │  │ + PC register │   │ + PC register │                  │
  │  └───────────────┘   └───────────────┘                  │
  └─────────────────────────────────────────────────────────┘
```

- **Heap**: where every object and array actually lives, no matter which
  thread created it. Shared across all threads, garbage-collected. This is
  the memory people mean when they say "OutOfMemoryError: heap space."

- **Stack**: one per thread, not shared. Each method call pushes a new
  *stack frame* holding that call's local variables, method arguments, and
  intermediate results and the info needed to return to the caller. When
  the method returns, its frame is popped.

- **PC (Program Counter) register**: one per thread. Holds the address of
  the JVM instruction that thread is currently executing. Because each
  thread has its own PC, threads can each be in the middle of a different
  method at the same time without stepping on each other.

- **Method Area (Metaspace since Java 8)**: shared, like the heap, but
  holds *class-level* data rather than object instances. The bytecode for
  each method, the runtime constant pool, `static` fields. Loaded once per
  class, not once per object.

- **Native method stack**: like the stack, but for native (non-Java, e.g.
  JNI/C) method calls. Mentioned for completeness. You won't touch this
  directly doing application-level Java.
