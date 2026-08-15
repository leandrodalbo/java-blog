package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day2Test
{

    @Test
    public void shouldCalculateFactorial()
    {
        Day2 day2 = new Day2();

        assertThat(day2.factorial(5)).isEqualTo(120);
    }

    @Test
    public void shouldCalculateFibonacciForN()
    {
        Day2 day2 = new Day2();
        assertThat(day2.fibonacci(5)).isEqualTo(new int[]{0, 1, 1, 2, 3});
    }

    @Test
    public void shouldReturnMinAndMax()
    {
        Day2 day2 = new Day2();
        assertThat(day2.minAndMax(new int[]{43,54,56,22,3,67,1,99,21})).isEqualTo(new int[]{1,99});
    }

    @Test
    public void shouldBuilAFreqTable(){
        Day2 day2 = new Day2();

        int[] table = day2.freqTable("AABBBC");

        assertThat(table['A']).isEqualTo(2);
        assertThat(table['B']).isEqualTo(3);
        assertThat(table['C']).isEqualTo(1);
    }

    @Test
    public void shouldCheckEveryCharIsUnique(){
        Day2 day2 = new Day2();

        assertThat(day2.everyCharIsUnique("abc")).isTrue();
        assertThat(day2.everyCharIsUnique("abcas")).isFalse();
    }

    @Test
    public void shouldCheckPermutations(){
        Day2 day2 = new Day2();

        assertThat(day2.isApermutation("ab", "ba")).isTrue();
        assertThat(day2.isApermutation("ab", "ava")).isFalse();
        assertThat(day2.isApermutation("ab", "aa")).isFalse();
    }



}
