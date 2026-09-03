# Longest Substring Without Repeating Characters

A sliding-window problem. 

## Problem

Write `longestSubstring(String input)`: return the length of the longest
substring that has no repeated characters.

```
"abcabcbb" -> "abc"  -> 3
"bbbbb"    -> "b"    -> 1
"pwwkew"   -> "wke"  -> 3
```

## Tests (RED)

```java
@Test
public void shouldFindLongestSubstringWithoutRepeats() {
    Day3 d3 = new Day3();

    assertThat(d3.longestSubstring("abcabcbb")).isEqualTo(3);
    assertThat(d3.longestSubstring("bbbbb")).isEqualTo(1);
    assertThat(d3.longestSubstring("pwwkew")).isEqualTo(3);
    assertThat(d3.longestSubstring("")).isEqualTo(0);
}
```

## Implementation (GREEN)

```java
/*
    sliding window / two-pointer technique
    The current window must contain only unique characters.

    O(N + N) = O(2N) => O(N)
*/
public int longestSubstring(String input) {
    Set<Character> characters = new HashSet<>();

    int left = 0;
    int max = 0;

    for (int right = 0; right < input.length(); right++) {

        while (characters.contains(input.charAt(right))) {
            characters.remove(input.charAt(left));
            left++;
        }

        characters.add(input.charAt(right));
        max = Math.max(max, right - left + 1);
    }

    return max;
}
```

## Why a window, not brute force

The brute-force approach checks every substring for uniqueness —
O(n³) (O(n²) substrings, O(n) each to verify). The sliding window gets
to O(n) by never re-examining a character it doesn't have to:

- `right` walks forward once, always expanding the window by one
  character per step.
- `left` only moves forward too — when the character at `right` is
  already in the window, `left` advances (shrinking from the left)
  until the duplicate is gone.
- Because both pointers only move forward and each visits every index
  at most once, the whole scan is O(2n) → O(n), not O(n) work repeated
  per starting point.

The `Set<Character>` mirrors exactly what's inside the current window,
so "does the window already contain this character" is an O(1) check
instead of re-scanning the window itself.
