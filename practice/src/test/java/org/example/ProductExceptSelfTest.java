package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductExceptSelfTest
{
    private ProductExceptSelf underTest = new ProductExceptSelf();

    @Test
    public void shouldReturnProductOfEveryOtherElement(){
        assertThat(underTest.productExceptSelf(new int[]{1, 2, 3, 4}))
                .containsExactly(24, 12, 8, 6);
    }

    @Test
    public void shouldHandleThreeElements(){
        assertThat(underTest.productExceptSelf(new int[]{2, 3, 4}))
                .containsExactly(12, 8, 6);
    }

    @Test
    public void shouldHandleASingleZero(){
        assertThat(underTest.productExceptSelf(new int[]{0, 2, 3}))
                .containsExactly(6, 0, 0);
    }

    @Test
    public void shouldHandleMultipleZeros(){
        assertThat(underTest.productExceptSelf(new int[]{0, 0, 3}))
                .containsExactly(0, 0, 0);
    }
}
