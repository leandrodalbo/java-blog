package org.example.threads;

public class SynchCounter implements ThreadSafeCounter
{

    private long value = 0;

    @Override
    public void increment()
    {
        inc();
    }

    @Override
    public long getValue()
    {
        return get();
    }


    private synchronized void inc(){
        value++;
    }


    private synchronized long get()
    {
        return value;
    }
}
