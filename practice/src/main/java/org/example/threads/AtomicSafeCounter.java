package org.example.threads;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicSafeCounter implements ThreadSafeCounter
{
    private final AtomicLong value = new AtomicLong(0);

    @Override
    public void increment()
    {
        value.incrementAndGet();
    }

    @Override
    public long getValue()
    {
        return value.get();
    }
}
