# Two Sum

## Problem

Write `indexForTarget(int[] nums, int target)`: return the indices of the two numbers that add up to `target`. Assume exactly one valid pair exists.

```
nums = [2, 4, 6, 2, 5, 6], target = 11
-> [2, 4]     (nums[2] + nums[4] = 6 + 5 = 11)
```

## Tests (RED)

```java
@Test
public void shouldIndexesForTarget(){
    Day3 d3 = new Day3();

    assertThat(d3.indexForTarget(new int[]{2,4,6,2,5,6}, 11)).isEqualTo(new int[]{2, 4});
}
```

## Implementation (GREEN)

```java
/**
 * one-pass hash map: for each number, check whether its complement
 * (target - number) was already seen before storing the current number
 *
 * O(N) time, O(N) space
 */
public int[] indexForTarget(int[] nums, int target)
{
    Map<Integer, Integer> valueIndex = new HashMap<>();

    for (int i = 0; i < nums.length; i++)
    {
        int diff = target - nums[i];

        if (valueIndex.containsKey(diff))
        {
            return new int[]{valueIndex.get(diff), i};
        }

        valueIndex.put(nums[i], i);
    }

    return new int[]{-1, -1};
}
```

## Why a hash map, not nested loops

The brute-force solution uses two nested loops: for every number, scan
every other number looking for a complement that sums to `target`. That's
O(n²).

```
nums = [2, 4, 6, 2, 5, 6], target = 11

i=0: need 11-2=9   map has {}                 -> not found, store 2->0
i=1: need 11-4=7   map has {2:0}               -> not found, store 4->1
i=2: need 11-6=5   map has {2:0, 4:1}          -> not found, store 6->2
i=3: need 11-2=9   map has {2:0, 4:1, 6:2}     -> not found, store 2->3 (overwrites 2->0)
i=4: need 11-5=6   map has {2:3, 4:1, 6:2}     -> 6 found at index 2 -> return [2, 4]
```

