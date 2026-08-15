package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day1Test
{
    @Test
    public void shouldCheckIsPrime()
    {
        Day1 d1 = new Day1();
        assertThat(d1.isPrime(2)).isTrue();
        assertThat(d1.isPrime(13)).isTrue();
        assertThat(d1.isPrime(29)).isTrue();
        assertThat(d1.isPrime(4)).isFalse();
        assertThat(d1.isPrime(12)).isFalse();
        assertThat(d1.isPrime(25)).isFalse();
    }

    @Test
    public void shouldCheckGCD()
    {
        Day1 d1 = new Day1();
        assertThat(d1.mcd(12, 8)).isEqualTo(4);
        assertThat(d1.mcd(12, 18)).isEqualTo(6);
        assertThat(d1.mcd(36, 48)).isEqualTo(12);
    }


    @Test
    public void shouldCheckLCM()
    {
        Day1 d1 = new Day1();
        assertThat(d1.lcm(4, 6)).isEqualTo(12);
        assertThat(d1.lcm(5, 7)).isEqualTo(35);
        assertThat(d1.lcm(15, 20)).isEqualTo(60);
    }


    @Test
    void shouldCheckPalindromes()
    {
        Day1 day1 = new Day1();

        assertThat(day1.isAPalindrome("racecar")).isTrue();
        assertThat(day1.isAPalindrome("hannah")).isTrue();
        assertThat(day1.isAPalindrome("hello")).isFalse();
    }
}
