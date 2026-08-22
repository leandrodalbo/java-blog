package org.example;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupAnagramsTest
{
    private  GroupAnagrams underTest = new GroupAnagrams();


    @Test
    public void shouldGroupAnagrams()
    {
        assertThat(underTest.groupAnagrams(List.of("eat", "tea", "tan", "ate", "nat", "bat")).values())
                .containsExactlyInAnyOrder(
                        List.of("eat", "tea", "ate"),
                        List.of("tan", "nat"),
                        List.of("bat")
                );
    }

    @Test
    public void shouldGroupAnagramsUsingSort()
    {
        assertThat(underTest.groupAnagramsSortedKeys(List.of("eat", "tea", "tan", "ate", "nat", "bat")).values())
                .containsExactlyInAnyOrder(
                        List.of("eat", "tea", "ate"),
                        List.of("tan", "nat"),
                        List.of("bat")
                );
    }
}
