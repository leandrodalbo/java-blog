# Day 6: Crazy extra bits and StringBuilder

## Bit Manipulation

### Operations

- `<<` left shift: shift bits left, fill with 0 on the right.

- `>>` right shift (signed): shift bits right, fill on the left with the **sign bit** (0 for positive, 1 for negative). Preserves the sign, same as dividing by 2 per shift (rounded toward negative infinity).
- `>>>` unsigned right shift: shift bits right, always fill on the left with 0, ignoring the sign. Only differs from `>>` on negative numbers.

```
 5 >>  1  =  2     0000...0101 >> 1  = 0000...0010
-8 >>  1  = -4     1111...1000 >> 1  = 1111...1100   (sign bit carried in)
-8 >>> 1  = big +  1111...1000 >>> 1 = 0111...1100   (0 carried in, sign lost)
```

### Truth table

| A | B | A & B (AND) | A \| B (OR) | A ^ B (XOR) |
|---|---|:---:|:---:|:---:|
| 0 | 0 | 0 | 0 | 0 |
| 0 | 1 | 0 | 1 | 1 |
| 1 | 0 | 0 | 1 | 1 |
| 1 | 1 | 1 | 1 | 0 |

`~` (NOT) is unary, it flips a single bit:

| A | ~A |
|---|:---:|
| 0 | 1 |
| 1 | 0 |

- `&` is 1 only if **both** bits are 1.
- `|` is 1 if **at least one** bit is 1.
- `^` is 1 if the bits are **different**.

## Magic Tricks

Each trick below builds on one operator from the truth table above.

### Clear a bit: `num & ~mask`

`~(1 << pos)` is all 1s except a 0 at `pos`. When an AND is applied with it forces that position to 0 and leaves every other bit untouched (`x & 1 = x`, `x & 0 = 0`).

```
num = 0110  (6), pos = 1
mask = ~(1 << 1) = ~0010 = 1101
num & mask = 0110 & 1101 = 0100  (4, bit 1 cleared)
```

```java
// O(1) time, O(1) space
int clearBit(int num, int pos){
  int mask = ~(1 << pos);
  return (mask & num);
}
```

### Toggle a bit: `num ^ mask`

`1 << pos` is all 0s with a single 1 at `pos`. When XOR is applied it flips only that bit: `x ^ 1` flips `x`, `x ^ 0` leaves `x` alone.

```
num = 0110  (6), pos = 0
mask = 1 << 0 = 0001
num ^ mask = 0110 ^ 0001 = 0111  (7, bit 0 turned on)

num = 0111  (7), pos = 0
num ^ mask = 0111 ^ 0001 = 0110  (6, bit 0 turned off again)
```

```java
// O(1) time, O(1) space
int toggleBit(int num, int pos){
  int mask = 1 << pos;
  return (num ^ mask);
}
```

### Appear only once

- Given an array where every number appears twice except one, find the one that appears only once.

Two facts about XOR make this work: `x ^ x = 0` (a number cancels itself out) and `x ^ 0 = x` (XOR with 0 is a no-op), and XOR doesn't care about order. XOR the whole array together and every pair cancels out, leaving only the number with no partner.

```
data = [4, 1, 2, 1, 2]

result = 0
result ^= 4 -> 4
result ^= 1 -> 5
result ^= 2 -> 7
result ^= 1 -> 6   (the two 1s have now cancelled: 5^1^1 = 5)
result ^= 2 -> 4   (the two 2s have now cancelled)
```

```java
// O(N) time, O(1) space
int appearOnce(int[] data){
  int result = 0;

  for(int num: data){
    result ^= num;
  }

  return result;
}
```

### Check a bit: `(num & mask) != 0`

`1 << pos` isolates that one position. When AND is applied with `num` it zeroes out every other bit, so the result is non-zero only if `num` had a 1 there.

```java
// O(1) time, O(1) space
boolean isPositionOn(int num, int pos){
  int mask = 1 << pos;
  return (num & mask) != 0;
}
```

### Set a bit: `num | mask`

`1 << pos` is a single 1 at `pos`. When OR is applied it forces that position to 1 and
leaves every other bit untouched (`x | 0 = x`).

```java
// O(1) time, O(1) space
int setBit(int num, int pos){
  int mask = 1 << pos;
  return (num | mask);
}
```

## Why we need StringBuilder?

```java
String joinWords(String[] manyWords){
    String mySentence = "";

    for(String word: manyWords){
       mySentence += word;
    }
    return mySentence;
}
```

`String` is immutable. Every iteration allocates a brand new `String` and copies every character seen so far into it, plus the new word.

Let `x` be how many characters are in one word (assume every word is the same length, to keep the numbers simple), and `N` the number of words.

### Step 1: from the loop to a series

Before the loop starts, `mySentence` is empty. Walk through what each
iteration actually has to copy:

```
before iteration 1: mySentence holds 0 words = 0 chars
  += word1  ->  copy the 0 existing chars, then the x new ones  =>  1x chars copied

before iteration 2: mySentence holds 1 word = 1x chars
  += word2  ->  copy the 1x existing chars, then the x new ones =>  2x chars copied

before iteration 3: mySentence holds 2 words = 2x chars
  += word3  ->  copy the 2x existing chars, then the x new ones =>  3x chars copied

...

before iteration N: mySentence holds (N-1) words = (N-1)x chars
  += wordN  ->  copy the (N-1)x existing chars, then the x new ones => Nx chars copied
```

Each iteration `i` copies `i * x` characters, because that's how much of the sentence has piled up so far. Add every iteration's cost together and that's the *total* work the whole loop does:

```
total copies = 1x + 2x + 3x + ... + Nx
```

That's the series: it's not something we chose, it just falls out of
tracing what the loop does on each pass.

### Step 2: from the series to O(N²)

`x` is the same in every term, so factor it out:

```
1x + 2x + 3x + ... + Nx  =  x * (1 + 2 + 3 + ... + N)
```

`1 + 2 + 3 + ... + N` is the sum of the first `N` natural numbers. There's a well-known shortcut for it (pair the smallest with the largest: `1+N`, `2+(N-1)`, `3+(N-2)`, ... every pair 
adds up to `N+1`, and there are `N/2` such pairs):

```
1 + 2 + 3 + ... + N = N(N+1)/2
```

Substitute that back in:

```
x * (1 + 2 + ... + N) = x * N(N+1)/2 = x * (N² + N) / 2
```

Now apply the usual Big O simplification rules: **drop the constant
factor** (`x` and `1/2` don't grow with `N`, so they don't matter at
scale) and **drop the lower-order term** (`N` is insignificant next to
`N²` once `N` is large):

```
x * (N² + N) / 2   -->   O(N²)
```

### StringBuilder Solution O(N)

`StringBuilder` is mutable: it keeps a resizable internal `char[]` buffer and `append()` writes into it directly (amortized O(1) per call, same idea as `ArrayList` growth), no copying the whole string built so far.

```java
// O(N) time: one append per word, no re-copying on each iteration
String joinWords(String[] words){
  StringBuilder sb = new StringBuilder();

  for(String w: words){
    sb.append(w);
  }

  return sb.toString();
}
```
