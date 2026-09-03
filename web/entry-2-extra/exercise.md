# Factorial & Fibonacci

Two of the oldest recursion problems, both done iteratively. A good excuse to compare the iterative and recursive shapes of the same idea.

## Problem

Write `factorial(int x)`: return `x!` (`x × (x-1) × (x-2) × ... × 1`).

```
factorial(5) -> 120
```

Write `fibonacci(int x)`: return an array holding the first `x` numbers
of the Fibonacci sequence (each number is the sum of the two before
it, starting `0, 1`).

```
fibonacci(5) -> [0, 1, 1, 2, 3]
```

## Tests (RED)

```java
@Test
public void shouldCalculateFactorial() {
    Day2 day2 = new Day2();
    assertThat(day2.factorial(5)).isEqualTo(120);
}

@Test
public void shouldCalculateFibonacciForN() {
    Day2 day2 = new Day2();
    assertThat(day2.fibonacci(5)).isEqualTo(new int[]{0, 1, 1, 2, 3});
}
```

## Implementation (GREEN)

```java
/*
    factorial:  x! = x × (x-1) × (x-2) × ⋯
    Time complexity: O(x)
    Space complexity: O(1)
 */
public int factorial(int x) {
    int result = 1;

    for (int i = x; i >= 1; i--) {
        result *= i;
    }

    return result;
}

/*
    Complexity: O(x) time and O(x) space
*/
public int[] fibonacci(int x) {
    int[] result = new int[x];
    int a = 0;
    int b = 1;

    if (x == 0) return new int[]{0};
    if (x == 1) {
        return new int[]{0, 1};
    }

    result[a] = a;
    result[b] = b;

    for (int i = 2; i < x; i++) {
        result[i] = result[a] + result[b];
        a++;
        b++;
    }

    return result;
}
```

## Factorial: iterative vs recursive

The textbook definition of factorial is recursive (`x! = x × (x-1)!`),
so the recursive version reads closest to the math:

```java
public int factorialRecursive(int x) {
    if (x <= 1) return 1;
    return x * factorialRecursive(x - 1);
}
```

Same O(x) time either way, but the recursive version requires O(x) space
for the call stack — one frame per pending multiplication — while the
iterative loop above stays O(1) space.

## Fibonacci: why keep the whole array

`fibonacci` here returns the *sequence*, not just the `x`-th value, so
it genuinely needs O(x) space — every previous value stays in the
output. If the problem only asked for the `x`-th Fibonacci number, the
usual next question is "can you do it without an array?" — yes, since
each step only ever needs the previous two values:

```java
public int fibonacciNth(int x) {
    if (x == 0) return 0;

    int prev = 0;
    int curr = 1;

    for (int i = 2; i <= x; i++) {
        int next = prev + curr;
        prev = curr;
        curr = next;
    }

    return curr;
}
```

That drops space from O(x) to O(1).
