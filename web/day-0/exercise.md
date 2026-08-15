# Palindrome Check

The solution fits in a few lines but still has a real complexity.

## Problem

Write `isAPalindrome(String word)`: return `true` if the string reads the
same forwards and backwards, `false` otherwise.

```
"racecar" -> true
"hannah"  -> true
"hello"   -> false
```

Simple enough to explain in one sentence to a beginner. The interesting part
is *how* you check it, not *what* you're checking.

## Tests (RED)

```java
@Test
void shouldCheckPalindromes() {
    Day1 day1 = new Day1();

    assertThat(day1.isAPalindrome("racecar")).isTrue();
    assertThat(day1.isAPalindrome("hannah")).isTrue();
    assertThat(day1.isAPalindrome("hello")).isFalse();
}
```

Three cases: an odd-length palindrome, an even-length palindrome, and a
plain rejection. That's enough to pin down the behavior before writing any
implementation.

## Implementation (GREEN)

```java
/**
 * two pointers implementation
 * Space O(1)  != String.reverse()  O(N)
 * Time O(N)
 */
public boolean isAPalindrome(String word) {
    int x = 0;
    int y = word.length() - 1;

    while (x < y) {
        if (word.charAt(x) != word.charAt(y)) return false;
        x++;
        y--;
    }

    return true;
}
```

## Why two pointers, not `reverse()`

The tempting first answer is: reverse the string, compare it to the
original.

```java
return word.equals(new StringBuilder(word).reverse().toString());
```

That works, but it allocates a whole new string just to throw it away.
O(n) extra space for a question that doesn't need it. Walking inward from
both ends at once (one pointer at index `0`, one at `length - 1`) answers
the same question in O(n) time and **O(1)** space: no extra allocation, no
copy. It also stops early. Whenever two characters disagree, you're done, you don't have to scan the whole string.

```
racecar
^     ^        x=0, y=6: 'r' == 'r' -> keep going
 ^   ^         x=1, y=5: 'a' == 'a' -> keep going
  ^ ^          x=2, y=4: 'c' == 'c' -> keep going
   ^           x=3, y=3: x >= y, loop ends -> true
```

