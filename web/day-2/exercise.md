# GCD & LCM

The Euclidean algorithm — one of the oldest algorithms still in everyday
use (Euclid's *Elements*, ~300 BC) — and the LCM identity that rides on
top of it.

## Problem

Write `mcd(int a, int b)`: return the greatest common divisor of `a` and
`b` (the largest number that divides both evenly).

```
mcd(12, 8)  -> 4
mcd(12, 18) -> 6
mcd(36, 48) -> 12
```

Write `lcm(int a, int b)`: return the least common multiple of `a` and
`b` (the smallest number both divide evenly into).

```
lcm(4, 6)   -> 12
lcm(5, 7)   -> 35
lcm(15, 20) -> 60
```

## Tests (RED)

```java
@Test
public void shouldCheckGCD() {
    Day1 d1 = new Day1();
    assertThat(d1.mcd(12, 8)).isEqualTo(4);
    assertThat(d1.mcd(12, 18)).isEqualTo(6);
    assertThat(d1.mcd(36, 48)).isEqualTo(12);
}

@Test
public void shouldCheckLCM() {
    Day1 d1 = new Day1();
    assertThat(d1.lcm(4, 6)).isEqualTo(12);
    assertThat(d1.lcm(5, 7)).isEqualTo(35);
    assertThat(d1.lcm(15, 20)).isEqualTo(60);
}
```

## Implementation (GREEN)

```java
/**
 * Euclidean algorithm using subtraction
 */
public int mcd(int a, int b) {
    while (a != b) {
        if (a < b) {
            b = b - a;
        } else {
            a = a - b;
        }
    }
    return a;
}

public int lcm(int a, int b) {
    return (a * b) / mcd(a, b);
}
```

## Why repeated subtraction works

The core fact: any number that divides both `a` and `b` also divides
their difference. So `gcd(a, b) == gcd(a - b, b)` when `a > b` — shrinking
the pair never changes the answer, it just gets there faster. Keep
subtracting the smaller from the larger and the pair converges until
`a == b`, and that shared value is the GCD.

```
mcd(12, 8)
  12, 8 -> a > b: a = 12 - 8 = 4
  4, 8  -> b > a: b = 8 - 4  = 4
  4, 4  -> equal, done -> 4
```

## Subtraction vs modulo — the complexity angle worth mentioning out loud

The version above is the textbook *subtractive* Euclidean algorithm:
easy to reason about, but its worst case is `O(max(a, b))` — think
`mcd(1, 1_000_000)`, which subtracts 1 a million times.

The standard library version (and the one worth using in practice) swaps
subtraction for modulo:

```java
public int mcdFast(int a, int b) {
    return b == 0 ? a : mcdFast(b, a % b);
}
```

`a % b` does in one step what the subtractive version does in
`a / b` steps, so this converges in `O(log(min(a, b)))` — the same
correctness argument, dramatically fewer iterations.

## LCM: built on GCD, not independent of it

`lcm(a, b) = (a * b) / gcd(a, b)` falls out of a simple identity:
`gcd(a, b) * lcm(a, b) == a * b`. It's cheap once `mcd` exists — no need
to re-derive LCM from scratch.
