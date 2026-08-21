# Permutation Check

## Problem

Write `isApermutation(String a, String b)`: return `true` if `b` is a permutation of `a`, meaning both strings have exactly the same characters, in any order (and the same length).

```
isApermutation("ab", "ba") -> true
isApermutation("ab", "ava") -> false   (different length)
isApermutation("ab", "aa") -> false    (same length, different characters)
```

## Tests (RED)

```java
@Test
public void shouldCheckPermutations(){
    Day2 day2 = new Day2();

    assertThat(day2.isApermutation("ab", "ba")).isTrue();
    assertThat(day2.isApermutation("ab", "ava")).isFalse();
    assertThat(day2.isApermutation("ab", "aa")).isFalse();
}
```

## Implementation (GREEN)

```java
/**
 * count every character of a into a fixed-size (128, ASCII) *frequency table, then walk b decrementing counts. If a count *goes negative, b has a character a doesn't (or has it too *many times), so it can't be a permutation.
 *
 *O(N) time, O(1) space (the frequency table is capped at 128 *regardless of N)
 */
public boolean isApermutation(String a, String b)
{
    if(a.length() != b.length()) return false;

    int[] freqA = freqTable(a);
    char[] charsB = b.toCharArray();

    for (int i = 0; i < charsB.length; i++)
    {
        if (freqA[charsB[i]] >= 0) freqA[charsB[i]]--;

        if (freqA[charsB[i]] < 0) return false;
    }

    return true;
}
```

## Why a frequency table, not sorting

The obvious approach is to sort both strings and compare them character by character, since two strings with the same characters become identical once sorted. That works, but sorting costs O(n log n).

Counting instead of sorting drops that to O(n): build a frequency table for `a`, then walk `b` once, decrementing as you go. If `b` is a true permutation, every count lands back at exactly 0.

```
a = "ab", b = "ba"

freqTable(a):        {a: 1, b: 1}

walk b:
  'b' -> freqA[b] 1 -> 0
  'a' -> freqA[a] 1 -> 0

no count went negative -> true
```

```
a = "ab", b = "aa"

freqTable(a):        {a: 1, b: 1}

walk b:
  'a' -> freqA[a] 1 -> 0
  'a' -> freqA[a] 0 -> -1   (already at 0, 'a' appears once too often)

count went negative -> false
```
