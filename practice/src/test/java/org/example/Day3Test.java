package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day3Test
{

    @Test
    public void shouldIndexesForTarget(){
        Day3 d3 = new Day3();

        assertThat(d3.indexForTarget(new int[]{2,4,6,2,5,6}, 11)).isEqualTo(new int[]{2, 4});
    }


    @Test
    public void shouldReverseAString(){
        Day3 d3 = new Day3();

        assertThat(d3.stringReverse("abc")).isEqualTo("cba");
    }

    @Test
    public void shouldCheckPalindromePermutations(){
        Day3 d3 = new Day3();

        assertThat(d3.isAPalindromePermutation("Tact Coa")).isTrue();

        assertThat(d3.isAPalindromePermutation("tact coa")).isTrue();

        assertThat(d3.isAPalindromePermutation("Taco Cat")).isTrue();

        assertThat(d3.isAPalindromePermutation("This is a test")).isFalse();

        assertThat(d3.isAPalindromePermutation("abc")).isFalse();

        assertThat(d3.isAPalindromePermutation("aab")).isTrue();

        assertThat(d3.isAPalindromePermutation("aabb")).isTrue();

        assertThat(d3.isAPalindromePermutation("aabbc")).isTrue();

        assertThat(d3.isAPalindromePermutation("aabbcd")).isFalse();

        assertThat(d3.isAPalindromePermutation("racecar")).isTrue();

        assertThat(d3.isAPalindromePermutation("")).isTrue();

        assertThat(d3.isAPalindromePermutation("a")).isTrue();
    }
}
