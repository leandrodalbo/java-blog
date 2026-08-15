package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class Day5Test
{
    private Day5 underTest = new Day5();

    @Test
    public void shouldValidateBalancedBrackets(){

        assertThat(underTest.isBalancedBrackets("hello(world)")).isTrue();
        assertThat(underTest.isBalancedBrackets("foo[bar{baz}]")).isTrue();
        assertThat(underTest.isBalancedBrackets( "hello(world[123]{test})")).isTrue();
        assertThat(underTest.isBalancedBrackets("abc(def[ghi]{jkl})xyz")).isTrue();
        assertThat(underTest.isBalancedBrackets( "foo(bar[baz}qux)")).isFalse();
    }
}
