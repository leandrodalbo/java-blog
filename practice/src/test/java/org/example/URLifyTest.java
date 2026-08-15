package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class URLifyTest
{

    @Test
    public void shouldMakeStringsURLSafe(){
        URLify underTest = new URLify();
        assertThat(underTest.urlSafeString("Mr John Smith"))
                .isEqualTo("Mr%20John%20Smith");

        assertThat(underTest.urlSafeString("Hello World"))
                .isEqualTo("Hello%20World");

        assertThat(underTest.urlSafeString("Java Is Fun"))
                .isEqualTo("Java%20Is%20Fun");

        assertThat(underTest.urlSafeString("a  b"))
                .isEqualTo("a%20%20b");

        assertThat(underTest.urlSafeString("Hello"))
                .isEqualTo("Hello");
    }
}
