# Product Except Self

Build a result array without using division.

## Problem

Given an array of integers, return a new array where each index holds
the product of every other element, excluding itself. Division isn't
allowed, since dividing by a zero element throws an exception.

```
input:  [1, 2, 3, 4]
output: [24, 12, 8, 6]

output[0] = 2*3*4 = 24
output[1] = 1*3*4 = 12
output[2] = 1*2*4 = 8
output[3] = 1*2*3 = 6
```

## Tests (RED)

```java
private ProductExceptSelf underTest = new ProductExceptSelf();

@Test
public void shouldReturnProductOfEveryOtherElement(){
    assertThat(underTest.productExceptSelf(new int[]{1, 2, 3, 4}))
            .containsExactly(24, 12, 8, 6);
}

@Test
public void shouldHandleThreeElements(){
    assertThat(underTest.productExceptSelf(new int[]{2, 3, 4}))
            .containsExactly(12, 8, 6);
}

@Test
public void shouldHandleASingleZero(){
    assertThat(underTest.productExceptSelf(new int[]{0, 2, 3}))
            .containsExactly(6, 0, 0);
}

@Test
public void shouldHandleMultipleZeros(){
    assertThat(underTest.productExceptSelf(new int[]{0, 0, 3}))
            .containsExactly(0, 0, 0);
}
```

## Implementation (GREEN)

```java
public int[] productExceptSelf(int[] nums)
{
    int n = nums.length;

    int[] lefts = new int[n];
    int runningLeft = 1;

    for (int i = 0; i < n; i++)
    {
        lefts[i] = runningLeft;
        runningLeft *= nums[i];
    }

    int[] rights = new int[n];
    int runningRight = 1;
    
    for (int i = n - 1; i >= 0; i--)
    {
        rights[i] = runningRight;
        runningRight *= nums[i];
    }

    int[] result = new int[n];
    for (int i = 0; i < n; i++)
    {
        result[i] = lefts[i] * rights[i];
    }

    return result;
}
```

Two build-up passes replace the one division-based pass:

1. `lefts[i]` is the product of everything before index `i`. Walk left
   to right, carrying a running product forward, and write it down
   *before* folding in `nums[i]`.
2. `rights[i]` is the product of everything after index `i`, the same
   idea walking right to left.
3. `result[i] = lefts[i] * rights[i]` combines both halves.

```
nums:    1    2    3    4
lefts:   1    1    2    6
rights:  24   12   4    1
result:  24   12   8    6
```

## Why no division

The obvious shortcut is to multiply all the numbers once, then divide
out `nums[i]` for each index. That breaks as soon as one element is
zero, since dividing by zero isn't allowed. The zero test cases above
are there to catch that shortcut.

## Complexity

Time: O(n), three passes over the array.
Space: O(n) for `lefts` and `rights`, plus the O(n) output array. The
two extra arrays can be dropped by writing straight into the result
array and keeping the running right-to-left product in a single
variable, which brings extra space down to O(1).
