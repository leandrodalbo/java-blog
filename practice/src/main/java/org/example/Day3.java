package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day3
{

    /**
     * sliding window / two-pointer technique
     * The current window must contain only unique characters.
     * <p>
     * "abcabcbb" => "abc" => 3
     * "bbbbb" → "b" → 1
     * "pwwkew" → "wke" → 3
     * <p>
     * O(N + N) = O(2N) => O(N)
     */
    public int longestSubstring(String input)
    {
        Set<Character> characters = new HashSet<>();

        int left = 0;
        int max = 0;

        for (int right = 0; right < input.length(); right++)
        {

            while (characters.contains(input.charAt(right)))
            {
                characters.remove(input.charAt(left));
                left++;
            }

            characters.add(input.charAt(right));
            max = Math.max(max, right - left + 1);
        }

        return max;
    }


    /**
     *
     * n = length of input
     * k = size of the frequency table
     * <p>
     * frq table O(n)
     * <p>
     * frq iteration O(k)
     * <p>
     * O(N + K)  K is constant (128)  => O(n)
     */

    public boolean isAPalindromePermutation(String input)
    {
        Day2 day2 = new Day2();

        int[] frequencies = day2.freqTable(input.replace(" ", "").toLowerCase());

        int oddsFrequencies = 0;

        for (int i = 0; i < frequencies.length; i++)
        {
            if ((frequencies[i] % 2) != 0) oddsFrequencies++;
            if (oddsFrequencies > 1) return false;
        }

        return true;
    }


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

    public String stringReverse(String word)
    {
        StringBuffer buffer = new StringBuffer();

        for (int i = word.length() - 1; i >= 0; i--)
        {
            buffer.append(word.charAt(i));
        }

        return buffer.toString();
    }
}
