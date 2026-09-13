# Arrays.merge Test Retrofit

## Problem

Merge two already-sorted `int[]` arrays into a single one without using a library.

```java
merge(new int[]{1, 3, 5}, new int[]{2, 4, 6}) -> [1, 2, 3, 4, 5, 6]
```

## A design-debt example first

 The implementation was written first, and only now are the tests catching up.

## Existing implementation

```java
public int[] merge(int[] arr0, int[] arr1) {
    int[] merged = new int[arr0.length + arr1.length];
    int a = 0, b = 0, z = 0;

    while (a < arr0.length && b < arr1.length) {
        if (arr0[a] < arr1[b]) {
            merged[z] = arr0[a];
            a++;
        } else {
            merged[z] = arr1[b];
            b++;
        }
        z++;
    }

    while (a < arr0.length) { merged[z] = arr0[a]; a++; z++; }
    while (b < arr1.length) { merged[z] = arr1[b]; b++; z++; }

    return merged;
}
```

## Tests written after the fact

Without a spec, you need to think carefully about what could go wrong. For a two-pointer merge, tests should cover cases like:

* Both arrays contribute elements.
* One array runs out first.
* The edge cases around these situations.


```java
@Test
public void shouldMergeTwoArraysOfEqualLength() {
    assertThat(arrays.merge(new int[]{1, 3, 5}, new int[]{2, 4, 6}))
        .containsExactly(1, 2, 3, 4, 5, 6);
}

@Test
public void shouldMergeArraysOfDifferentLengths() {
    assertThat(arrays.merge(new int[]{1, 2}, new int[]{3, 4, 5, 6}))
        .containsExactly(1, 2, 3, 4, 5, 6);
}

@Test
public void shouldMergeWhenOneArrayIsEmpty() {
    assertThat(arrays.merge(new int[]{}, new int[]{1, 2, 3}))
        .containsExactly(1, 2, 3);
}

@Test
public void shouldKeepDuplicatesWhenMerging() {
    assertThat(arrays.merge(new int[]{1, 2, 2}, new int[]{2, 3}))
        .containsExactly(1, 2, 2, 2, 3);
}

@Test
public void shouldMergeNegativeNumbers() {
    assertThat(arrays.merge(new int[]{-5, -1, 3}, new int[]{-3, 0, 2}))
        .containsExactly(-5, -3, -1, 0, 2, 3);
}
```

## Complexity

```text
Time:  O(n + m), every element from both arrays is visited once
Space: O(n + m), for the output array
```
