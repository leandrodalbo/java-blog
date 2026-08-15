package org.example;

public class Day2
{

    /*
        factorial:  x!=x×(x−1)×(x−2)×⋯
        Time complexity: O(x)
        Space complexity: O(1)
     */
    public int factorial(int x)
    {
        int result = 1;

        for (int i = x; i >= 1; i--)
        {
            result *= i;
        }

        return result;
    }

    /*
        Complexity: O(x) time and O(x) space
    */
    public int[] fibonacci(int x)
    {
        int[] result = new int[x];
        int a = 0;
        int b = 1;

        if (x == 0) return new int[]{0};
        if (x == 1)
        {
            return new int[]{0, 1};
        }

        result[a] = a;
        result[b] = b;

        for (int i = 2; i < x; i++)
        {
            result[i] = result[a] + result[b];
            a++;
            b++;
        }

        return result;

    }

    /*
    | Complexity      | Answer      | Reason                            |
    | --------------- | ----------- | --------------------------------- |
    | **Time**        | **O(N)**    | Look at every number once         |
    | **Space**       | **O(1)**    | Only `minMax` and a few variables |
    | **Input space** | Not counted | `numbers` was already provided    |
     */
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

    /*
    time O(N) Space O(1)
     */
    public int[] freqTable(String word)
    {
        char[] chars = word.toCharArray();
        int[] result = new int[128];

        for (int i = 0; i < chars.length; i++)
        {
            result[chars[i]]++;
        }

        return result;
    }


    /*
        TIME O(N) * O(128) => O(N) * O(1) => O(N)
        SPACE O(1)
     */
    public boolean everyCharIsUnique(String word)
    {
        int[] frequencies = freqTable(word);

        for (int i = 0; i < frequencies.length; i++)
            if (frequencies[i] > 1) return false;

        return true;
    }

    public boolean isApermutation(String a, String b)
    {
        if(a.length() != b.length()) return false;

        int[] freqA = freqTable(a);
        char[] charsB = b.toCharArray();

        for (int i = 0; i < charsB.length; i++)
        {
            if (freqA[charsB[i]] >= 0) freqA[charsB[i]]--;

            if (freqA[charsB[i]] < 0) return false;
        }

        return true;
    }
}
