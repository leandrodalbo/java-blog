# Min Stack

## Problem

Design a stack that supports `push`, `pop`, `top`, and `getMin`, all in
O(1) time, `getMin` included.

```
push(5)  -> stack: [5]           getMin() -> 5
push(3)  -> stack: [5, 3]        getMin() -> 3
push(7)  -> stack: [5, 3, 7]     getMin() -> 3
pop()    -> removes 7            getMin() -> 3
pop()    -> removes 3            getMin() -> 5
```

## Tests (RED)

```java
@Test
public void shouldTrackMinAsElementsArePushed(){
    underTest.push(5);
    assertThat(underTest.getMin()).isEqualTo(5);

    underTest.push(3);
    assertThat(underTest.getMin()).isEqualTo(3);

    underTest.push(7);
    assertThat(underTest.getMin()).isEqualTo(3);
}

@Test
public void shouldRestorePreviousMinAfterPoppingDuplicateMin(){
    underTest.push(5);
    underTest.push(3);
    underTest.push(3);
    underTest.push(7);

    underTest.pop(); // removes 7
    assertThat(underTest.getMin()).isEqualTo(3);

    underTest.pop(); // removes one of the 3s, other 3 still there
    assertThat(underTest.getMin()).isEqualTo(3);

    underTest.pop(); // removes last 3
    assertThat(underTest.getMin()).isEqualTo(5);
}

@Test
public void shouldReturnTopWithoutRemovingIt(){
    underTest.push(5);
    underTest.push(9);

    assertThat(underTest.top()).isEqualTo(9);
    assertThat(underTest.top()).isEqualTo(9); // unchanged, top() doesn't pop
}

@Test
public void shouldThrowOnEmptyStackOperations(){
    assertThatThrownBy(() -> underTest.pop()).isInstanceOf(EmptyStackException.class);
    assertThatThrownBy(() -> underTest.top()).isInstanceOf(EmptyStackException.class);
    assertThatThrownBy(() -> underTest.getMin()).isInstanceOf(EmptyStackException.class);
}
```

## Implementation (GREEN)

```java
private Stack<Integer> values = new Stack<>();
private Stack<Integer> mins = new Stack<>();

/**
 * O(1) time, O(1) extra space per call (amortized O(n) total across n pushes)
 */
public void push(int value) {
    values.push(value);
    mins.push(mins.isEmpty() ? value : Math.min(value, mins.peek()));
}

/**
 * O(1) time
 */
public int pop() {
    if (values.isEmpty()) throw new EmptyStackException();

    mins.pop();
    return values.pop();
}

/**
 * O(1) time
 */
public int top() {
    if (values.isEmpty()) throw new EmptyStackException();

    return values.peek();
}

/**
 * O(1) time
 */
public int getMin() {
    if (mins.isEmpty()) throw new EmptyStackException();

    return mins.peek();
}
```

## Why a second stack, not a single "current min" field

The obvious first attempt: keep one `min` variable, update it on every
push.

```
push(5) -> min = 5
push(3) -> min = 3
push(7) -> min = 3   (unchanged)
pop()   -> removes 7, min still 3, fine
pop()   -> removes 3... but what was the min before 3 was pushed?
```

That's the break: a single variable only remembers the *current*
minimum, not the history of minimums. Once the value that set it gets
popped, there's no way back to the previous one short of rescanning
the whole stack, `O(n)`, when `getMin` is supposed to be `O(1)`.

The fix mirrors the values stack itself, tracking one min per push:

```
push(5) -> values: [5]         mins: [5]
push(3) -> values: [5,3]       mins: [5,3]
push(3) -> values: [5,3,3]     mins: [5,3,3]
push(7) -> values: [5,3,3,7]   mins: [5,3,3,7]   (7 > 3, min unchanged)
pop()   -> values: [5,3,3]     mins: [5,3,3]     (drop 7's min too)
pop()   -> values: [5,3]       mins: [5,3]       (min correctly back to 3)
```

No rescans, no lost history.
