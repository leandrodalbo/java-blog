# Palindrome Permutation

Reuses `freqTable`, same helper as the unique characters exercise, on a different question.

## Problem

Write `isAPalindromePermutation(String input)`: return `true` if the characters of the string can be rearranged into some palindrome. Case and spaces don't count.

```
isAPalindromePermutation("Tact Coa")       -> true   ("taco cat" is a palindrome)
isAPalindromePermutation("This is a test") -> false
```

## Why frequency counts are enough

A string's letters can be rearranged into a palindrome when at most one character has an odd count.

## Tests (RED)

```java
@Test
public void shouldCheckPalindromePermutations(){
    Day3 d3 = new Day3();

    assertThat(d3.isAPalindromePermutation("Tact Coa")).isTrue();
    assertThat(d3.isAPalindromePermutation("tact coa")).isTrue();
    assertThat(d3.isAPalindromePermutation("Taco Cat")).isTrue();
    assertThat(d3.isAPalindromePermutation("This is a test")).isFalse();
    assertThat(d3.isAPalindromePermutation("abc")).isFalse();
    assertThat(d3.isAPalindromePermutation("aab")).isTrue();
    assertThat(d3.isAPalindromePermutation("aabb")).isTrue();
    assertThat(d3.isAPalindromePermutation("aabbc")).isTrue();
    assertThat(d3.isAPalindromePermutation("aabbcd")).isFalse();
    assertThat(d3.isAPalindromePermutation("racecar")).isTrue();
    assertThat(d3.isAPalindromePermutation("")).isTrue();
    assertThat(d3.isAPalindromePermutation("a")).isTrue();
}
```


## Implementation (GREEN)

```java
public boolean isAPalindromePermutation(String input)
{
    Day2 day2 = new Day2();

    int[] frequencies = day2.freqTable(input.replace(" ", "").toLowerCase());

    int oddsFrequencies = 0;

    for (int i = 0; i < frequencies.length; i++)
    {
        if ((frequencies[i] % 2) != 0) oddsFrequencies++;
        if (oddsFrequencies > 1) return false;
    }

    return true;
}
```


## Complexity

```
n = length of input
k = size of the frequency table (128, constant)

freqTable build:   O(n)
frequency scan:    O(k)

O(n + k)  k is constant  =>  O(n)
Space: O(k)  =>  O(1)
```
