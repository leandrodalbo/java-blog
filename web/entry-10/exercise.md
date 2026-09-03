# Thread-Safe Counter

## Problem

A simple `long value; value++;` counter is unsafe under concurrent writes:`++` isn't a single CPU instruction, it's read-modify-write, so two threads can read the same value, both increment it, and one increment gets lost.

```
20 threads, 10,000 increments each -> expected total: 200,000

unsafe long:      < 200,000   (lost updates)
synchronized:       200,000   (correct, but threads queue up)
AtomicLong:          200,000   (correct, no queuing)
```

## Tests (RED)

```java
private static final int THREAD_COUNT = 20;
private static final int INCREMENTS_PER_THREAD = 10_000;

@Test
public void syncCounterShouldCountAllIncrementsAcrossThreads() throws InterruptedException {
    SynchCounter underTest = new SynchCounter();

    runConcurrently(underTest::increment);

    assertThat(underTest.getValue()).isEqualTo((long) THREAD_COUNT * INCREMENTS_PER_THREAD);
}

@Test
public void atomicCounterShouldCountAllIncrementsAcrossThreads() throws InterruptedException {
    AtomicSafeCounter underTest = new AtomicSafeCounter();
    runConcurrently(underTest::increment);
    assertThat(underTest.getValue()).isEqualTo((long) THREAD_COUNT * INCREMENTS_PER_THREAD);
}

private void runConcurrently(Runnable increment) throws InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
    for (int i = 0; i < THREAD_COUNT; i++) {
        pool.submit(() -> {
            for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                increment.run();
            }
        });
    }
    pool.shutdown();
    pool.awaitTermination(10, TimeUnit.SECONDS);
}
```

Both implementations share one interface:

```java
public interface ThreadSafeCounter {
    void increment();
    long getValue();
}
```

## Implementation (GREEN)

### synchronized

```java
public class SynchCounter implements ThreadSafeCounter {

    private long value = 0;

    @Override
    public void increment() {
        inc();
    }

    @Override
    public long getValue() {
        return get();
    }

    private synchronized void inc() {
        value++;
    }

    private synchronized long get() {
        return value;
    }
}
```

`synchronized` puts a lock around `value`. Only one thread can be inside
`inc()` or `get()` at a time, every other thread trying to enter has to
wait for the lock to free up.

### AtomicLong

```java
public class AtomicSafeCounter implements ThreadSafeCounter {

    private final AtomicLong value = new AtomicLong(0);

    @Override
    public void increment() {
        value.incrementAndGet();
    }

    @Override
    public long getValue() {
        return value.get();
    }
}
```

No lock. `incrementAndGet()` is built on a CPU-level compare-and-swap
(CAS): read the current value, compute the new one, then write it back
only if nobody else changed it in between. If another thread got there
first, retry with the fresh value. No thread ever blocks, it just
occasionally redoes a bit of work.
