package org.example;

public class ProductExceptSelf
{
    /**
     * For every index, return the product of every other element, without
     * using division (division breaks when an element is 0).
     *
     * Build the two-array version first:
     * 1. lefts[i]  = product of everything before index i (walk left to right,
     *    carry a running product forward, write it down BEFORE folding in
     *    nums[i])
     * 2. rights[i] = product of everything after index i (same idea, walking
     *    right to left)
     * 3. result[i] = lefts[i] * rights[i]
     */
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
}
