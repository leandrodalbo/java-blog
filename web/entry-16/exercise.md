# Unique Characters

A quick one, reusing the frequency table from previous exercises.

## Problem

Write `everyCharIsUnique(String word)`: return `true` if no character repeats in the string.

```
everyCharIsUnique("abc")   -> true
everyCharIsUnique("abcas") -> false   ('a' repeats)
```

## Tests (RED)

```java
@Test
public void shouldCheckEveryCharIsUnique(){
    Day2 day2 = new Day2();

    assertThat(day2.everyCharIsUnique("abc")).isTrue();
    assertThat(day2.everyCharIsUnique("abcas")).isFalse();
}
```

## Implementation (GREEN)

```java
public boolean everyCharIsUnique(String word)
{
    int[] frequencies = freqTable(word);

    for (int i = 0; i < frequencies.length; i++)
        if (frequencies[i] > 1) return false;

    return true;
}
```

`freqTable` builds a 128 slot array counting how many times each character appears. If every slot holds at most 1, every character in the word is unique.

```java
public int[] freqTable(String word)
{
    char[] chars = word.toCharArray();
    int[] result = new int[128];

    for (int i = 0; i < chars.length; i++)
    {
        result[chars[i]]++;
    }

    return result;
}
```

## Complexity

```
Time complexity: O(N)
Space complexity: O(1)
```
