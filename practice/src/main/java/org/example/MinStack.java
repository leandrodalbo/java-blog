package org.example;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * The problem: a stack that supports push, pop, top, and getMin, all O(1).
 * A single "current min" field breaks on pop: if the popped value was the
 * min, there's no way to recover what the min was before it, short of
 * rescanning the whole stack. Fix: track the min at every push, in a second
 * stack that rises and falls in lockstep with the main one.
 */
public class MinStack
{

    private Stack<Integer> values = new Stack<>();
    private Stack<Integer> mins = new Stack<>();

    /**
     * O(1) time, O(1) extra space per call (amortized O(n) total across n pushes)
     */
    public void push(int value)
    {
        values.push(value);
        mins.push(mins.isEmpty() ? value : Math.min(value, mins.peek()));
    }

    /**
     * O(1) time
     */
    public int pop()
    {
        if (values.isEmpty()) throw new EmptyStackException();

        mins.pop();
        return values.pop();
    }

    /**
     * O(1) time
     */
    public int top()
    {
        if (values.isEmpty()) throw new EmptyStackException();

        return values.peek();
    }

    /**
     * O(1) time
     */
    public int getMin()
    {
        if (mins.isEmpty()) throw new EmptyStackException();

        return mins.peek();
    }
}
