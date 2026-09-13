package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArraysTest
{
    @Test
    public void shouldMergeTwoArraysOfEqualLength()
    {
        Arrays arrays = new Arrays();

        int[] merged = arrays.merge(new int[]{1, 3, 5}, new int[]{2, 4, 6});

        assertThat(merged).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void shouldMergeArraysOfDifferentLengths()
    {
        Arrays arrays = new Arrays();

        int[] merged = arrays.merge(new int[]{1, 2}, new int[]{3, 4, 5, 6});

        assertThat(merged).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    public void shouldMergeWhenOneArrayIsEmpty()
    {
        Arrays arrays = new Arrays();

        int[] merged = arrays.merge(new int[]{}, new int[]{1, 2, 3});

        assertThat(merged).containsExactly(1, 2, 3);
    }

    @Test
    public void shouldKeepDuplicatesWhenMerging()
    {
        Arrays arrays = new Arrays();

        int[] merged = arrays.merge(new int[]{1, 2, 2}, new int[]{2, 3});

        assertThat(merged).containsExactly(1, 2, 2, 2, 3);
    }

    @Test
    public void shouldMergeNegativeNumbers()
    {
        Arrays arrays = new Arrays();

        int[] merged = arrays.merge(new int[]{-5, -1, 3}, new int[]{-3, 0, 2});

        assertThat(merged).containsExactly(-5, -3, -1, 0, 2, 3);
    }
}
