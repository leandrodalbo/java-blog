package org.example.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadSafeCounterTest
{


    private static final int THREAD_COUNT = 20;
    private static final int INCREMENTS_PER_THREAD = 10_000;

    @Test
    public void syncCounterShouldCountAllIncrementsAcrossThreads() throws InterruptedException
    {
        SynchCounter underTest = new SynchCounter();

        runConcurrently(underTest::increment);

        assertThat(underTest.getValue()).isEqualTo((long) THREAD_COUNT * INCREMENTS_PER_THREAD);
    }

    @Test
    public void atomicCounterShouldCountAllIncrementsAcrossThreads() throws InterruptedException
    {
        AtomicSafeCounter underTest = new AtomicSafeCounter();
        runConcurrently(underTest::increment);
        assertThat(underTest.getValue()).isEqualTo((long) THREAD_COUNT * INCREMENTS_PER_THREAD);
    }

    private void runConcurrently(Runnable increment) throws InterruptedException
    {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++)
        {
            pool.submit(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++)
                {
                    increment.run();
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }
}

