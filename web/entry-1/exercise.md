# Prime Check

A simple problem and a good excuse to talk about why `isPrime` should never loop all the way to `n`.

## Problem


Write `isPrime(int number)`: return `true` if the number is prime or `false` otherwise.

Prime Number: greater than 1, divisible only by 1 and itself 

```
2  -> true
13 -> true
29 -> true
4  -> false
12 -> false
25 -> false
```

## Tests (RED)

```java
@Test
public void shouldCheckIsPrime() {
    Day1 d1 = new Day1();
    assertThat(d1.isPrime(2)).isTrue();
    assertThat(d1.isPrime(13)).isTrue();
    assertThat(d1.isPrime(29)).isTrue();
    assertThat(d1.isPrime(4)).isFalse();
    assertThat(d1.isPrime(12)).isFalse();
    assertThat(d1.isPrime(25)).isFalse();
}
```

## Implementation (GREEN)

```java
public boolean isPrime(int number) {
    if (number <= 1) {
        return false;
    }

    for (int i = 2; i <= Math.sqrt(number); i++) {
        if (number % i == 0) return false;
    }

    return true;
}
```

## Why stop at sqrt(n)

Factors come in pairs. If n = a × b, at least one factor must be ≤ sqrt(n).

So once we've checked every number up to sqrt(n) and found no divisor, there can't be a larger divisor either — it would have a smaller matching factor that we'd already have checked.

```
25 = 1 x 25
   = 5 x 5      <- sqrt(25) = 5, the pair meets exactly here
```

For 25, we only need to check 2 through 5. This reduces the loop from O(n) to O(√n).