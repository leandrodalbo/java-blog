# String Reverse

A short one because it is Friday and I need a beer.

## Problem

Write `stringReverse(String word)`: return the string with its characters in reverse order.

```
stringReverse("abc") -> "cba"
```

## Tests (RED)

```java
@Test
public void shouldReverseAString(){
    Day3 d3 = new Day3();

    assertThat(d3.stringReverse("abc")).isEqualTo("cba");
}
```

## Implementation (GREEN)

```java
public String stringReverse(String word)
{
    StringBuffer buffer = new StringBuffer();

    for (int i = word.length() - 1; i >= 0; i--)
    {
        buffer.append(word.charAt(i));
    }

    return buffer.toString();
}
```

The loop walks the input backwards, one character at a time, appending each one as it goes. `StringBuffer` is the thread-safe twin of `StringBuilder`, same idea, synchronized methods.

## Complexity

```
Time complexity: O(N)
Space complexity: O(N)
```
