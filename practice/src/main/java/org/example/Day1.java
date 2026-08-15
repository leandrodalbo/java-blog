package org.example;

public class Day1
{
    public boolean isPrime(int number)
    {
        if (number <= 1)
        {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(number); i++)
        {
            if (number % i == 0) return false;
        }

        return true;
    }


    /**
     * Euclidean algorithm using subtraction
     *
     * @param a
     * @param b
     * @return
     */
    public int mcd(int a, int b)
    {

        while (a != b)
        {
            if (a < b)
            {
                b = b - a;
            } else
            {
                a = a - b;
            }
        }
        return a;
    }


    public int lcm(int a, int b)
    {
        return (a * b) / mcd(a, b);
    }

    /**
     * two pointers implementation
     * Space O(1)  != String.reverse()  O(N)
     * Time O(N)
     *
     * @return
     */
    public boolean isAPalindrome(String word)
    {
        int x = 0;
        int y = word.length() - 1;

        while (x < y)
        {

            if (word.charAt(x) != word.charAt(y)) return false;
            x++;
            y--;
        }

        return true;
    }
}
