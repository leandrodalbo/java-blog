# Frequency Table, Two Ways

## Problem

Build a frequency table for the characters in a string.

What would the functional approach look like?

```java
freqTableStreams("AABBBC") -> table['A'] == 2, table['B'] == 3, table['C'] == 1
```

## Tests (RED)

```java
@Test
public void shouldBuildAFreqTableWithStreams() {
    Day2 day2 = new Day2();

    int[] table = day2.freqTableStreams("AABBBC");

    assertThat(table['A']).isEqualTo(2);
    assertThat(table['B']).isEqualTo(3);
    assertThat(table['C']).isEqualTo(1);
}
```

The tests should check that the new implementation behaves the same as the existing one.

## Implementation (GREEN)

```java
public int[] freqTableStreams(String word) {
    int[] result = new int[128];
    word.chars().forEach(c -> result[c]++);
    return result;
}
```

Compare it with the original:

```java
public int[] freqTable(String word) {
    char[] chars = word.toCharArray();
    int[] result = new int[128];

    for (int i = 0; i < chars.length; i++) {
        result[chars[i]]++;
    }

    return result;
}
```

## Was it worth it?

Not really. The stream version removes the explicit loop, but it still mutates `result` on every element.

A more functional version could return a `Map`:

```java
Map<Character, Long> counts = word.chars()
    .mapToObj(c -> (char) c)
    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
```

But that changes the return type and would require changes to the code using it.

**The takeaway:** use streams when they make a transformation clearer, not just because a loop can be rewritten as one. 

## Complexity

```text
Time:  O(N)
Space: O(1), the output array always has 128 elements
```
