# Balanced Brackets

## Problem

Write `isBalancedBrackets(String word)`: given a string that may mix
`()[]{}` with other characters, return whether every opening bracket
has a matching closing bracket, in the correct order.

```
"hello(world)"                  -> true
"foo[bar{baz}]"                 -> true
"hello(world[123]{test})"       -> true
"abc(def[ghi]{jkl})xyz"         -> true
"foo(bar[baz}qux)"              -> false   ({ closed by } instead of ])
```

## Tests (RED)

```java
@Test
public void shouldValidateBalancedBrackets(){
    assertThat(underTest.isBalancedBrackets("hello(world)")).isTrue();
    assertThat(underTest.isBalancedBrackets("foo[bar{baz}]")).isTrue();
    assertThat(underTest.isBalancedBrackets("hello(world[123]{test})")).isTrue();
    assertThat(underTest.isBalancedBrackets("abc(def[ghi]{jkl})xyz")).isTrue();
    assertThat(underTest.isBalancedBrackets("foo(bar[baz}qux)")).isFalse();
}
```

## Implementation (GREEN)

```java
private Set<Character> openingBrackets = Set.of('(', '{', '[');
private Set<Character> closingBrackets = Set.of(')', '}', ']');

/*
    O(N) time, O(N) space worst case (a string of only opening brackets)
*/
public boolean isBalancedBrackets(String word){
    Stack<Character> opened = new Stack<>();

    for (Character character : word.toCharArray()){

        if (openingBrackets.contains(character)){
            opened.push(character);
        }

        if (closingBrackets.contains(character)){
            if (opened.empty()) return false;

            Character latestOpening = opened.pop();

            if (!isClosingIt(latestOpening, character)) return false;
        }
    }

    return true;
}

private boolean isClosingIt(Character a, Character b){
    return (a.equals('(') && b.equals(')')) ||
           (a.equals('{') && b.equals('}')) ||
           (a.equals('[') && b.equals(']'));
}
```

## Why a stack, not a counter

What if we count opens and closes and check they match?

```
"([)]"   -> 2 opens, 2 closes, "balanced" by count, but not actually valid
```
- That breaks immediately on ordering.

A stack fixes this because it remembers *which* bracket is still
waiting to be closed, not just how many. Every closing bracket must
match the *most recently opened, still-unclosed* bracket, and that's
exactly what `pop()` gives you: LIFO, last in, first out.

Walking `"([)]"` through the stack:

```
'(' -> push       stack: [(]
'[' -> push       stack: [( []
')' -> pop '['    '[' vs ')' don't match -> false, bail out immediately
```

