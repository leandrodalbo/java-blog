# Min and Max

Picked a light exercise since this entry's theory already covers a lot of ground.

## Problem

Write `minAndMax(int[] numbers)`: return an array of two elements, the smallest and the largest number in the input, in that order.

```
{43,54,56,22,3,67,1,99,21} -> {1, 99}
```

## Tests (RED)

```java
@Test
public void shouldReturnMinAndMax()
{
    Day2 day2 = new Day2();
    assertThat(day2.minAndMax(new int[]{43,54,56,22,3,67,1,99,21})).isEqualTo(new int[]{1,99});
}
```

## Implementation (GREEN)

```java
public int[] minAndMax(int[] numbers)
{
    int[] minMax = new int[]{numbers[0], numbers[0]};

    for (int i = 1; i < numbers.length; i++)
    {
        if (numbers[i] < minMax[0]) minMax[0] = numbers[i];
        if (numbers[i] > minMax[1]) minMax[1] = numbers[i];
    }

    return minMax;
}
```

## Why one pass is enough

```
Time complexity: O(N), every number is visited once
Space complexity: O(1), just two variables besides the input
```
