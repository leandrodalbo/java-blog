# URLify

## Problem

Write `urlSafeString(String word)`: replace every space in a string with
`%20`, trimming leading/trailing whitespace first.

```
"Mr John Smith" -> "Mr%20John%20Smith"
"a  b"           -> "a%20%20b"
"Hello"          -> "Hello"
```

## Tests (RED)

```java
@Test
public void shouldMakeStringsURLSafe(){
    assertThat(underTest.urlSafeString("Mr John Smith"))
            .isEqualTo("Mr%20John%20Smith");

    assertThat(underTest.urlSafeString("Hello World"))
            .isEqualTo("Hello%20World");

    assertThat(underTest.urlSafeString("Java Is Fun"))
            .isEqualTo("Java%20Is%20Fun");

    assertThat(underTest.urlSafeString("a  b"))
            .isEqualTo("a%20%20b");

    assertThat(underTest.urlSafeString("Hello"))
            .isEqualTo("Hello");
}
```

## Implementation (GREEN)

```java
/*
    n = trimmed length, s = number of spaces
    totalSpaces: one pass, O(n)
    main loop: one pass, O(n)
    O(n) time, O(n) space (result array is n + 2s chars)
*/
public String urlSafeString(String word){
    char[] charArray = word.trim().toCharArray();
    char[] charArrayResult = new char[charArray.length + (totalSpaces(word.trim()) * 2)];

    int index = charArrayResult.length;

    for (int i = charArray.length - 1; i >= 0; i--){
        if (Character.isWhitespace(charArray[i])){
            charArrayResult[--index] = '0';
            charArrayResult[--index] = '2';
            charArrayResult[--index] = '%';
        }
        else {
            charArrayResult[--index] = charArray[i];
        }
    }

    return new String(charArrayResult);
}

private int totalSpaces(String word){
    int result = 0;

    for (int i = 0; i < word.length(); i++){
        if (Character.isWhitespace(word.charAt(i)))
            result++;
    }
    return result;
}
```

## Why fill from the back

Each space grows from 1 character to 3 (`%20`). Filling left to right
would keep pushing later characters further along, forcing re-shifts,
O(n·s) worst case. Filling right to left instead: the final length is
already known, so every character has one fixed slot and gets written
exactly once. O(n) total.
