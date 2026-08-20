# Rotate an Array

## Problem

Write `rotateArray(int[] arr, int k)`: rotate the array in place so the last `k` elements move to the front. No new array, O(1) extra space.

```
arr = [1, 2, 3, 4, 5], k = 2
-> [4, 5, 1, 2, 3]
```

## Tests (RED)

```java
@Test
public void shouldRotateKElements(){
    int[] data = new int[]{1, 2, 3, 4, 5};

    Day4.rotateArray(data, 2);

    assertThat(data).isEqualTo(new int[]{4, 5, 1, 2, 3});
}
```

## Implementation (GREEN)

```java
/**
 * reversal trick: reverse whole array, then reverse [0, k-1], then reverse [k, n-1]
 * each element is touched a constant number of times across the three passes, in place
 *
 * O(n) time, O(1) space
 */
public static void rotateArray(int[] arr, int k){
    rotateSubarray(arr, 0, arr.length - 1);
    rotateSubarray(arr, 0, k - 1);
    rotateSubarray(arr, k, arr.length - 1);
}

public static void rotateSubarray(int[] arr, int start, int end){
    while (start < end){

        int aux = arr[start];
        arr[start] = arr[end];
        arr[end] = aux;

        start++;
        end--;
    }
}
```

## Why reverse three times, not shift k times

The obvious approach is to shift every element right by one, k times.
That's O(n·k).

Reversing the whole array first puts every element in exactly the reverse of where it needs to end up. From there, the front k and the back n-k are each internally backwards, but already in the right *halves*. Reversing each half back to front fixes that, all in place:

```
arr = [1, 2, 3, 4, 5], k = 2

reverse all:        [5, 4, 3, 2, 1]
reverse [0, k-1]:    [4, 5, 3, 2, 1]   (fixes the front 2)
reverse [k, n-1]:    [4, 5, 1, 2, 3]   (fixes the back 3)
```

Three passes, each one O(n) at worst, so the total is still O(n)
