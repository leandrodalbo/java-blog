package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LRUCacheTest
{
    private LRUCache underTest = new LRUCache(2);

    @Test
    public void shouldReturnMinusOneForMissingKey(){
        assertThat(underTest.get(1)).isEqualTo(-1);
    }

    @Test
    public void shouldReturnValueJustPut(){
        underTest.put(1, 100);

        assertThat(underTest.get(1)).isEqualTo(100);
    }

    @Test
    public void shouldUpdateValueForExistingKeyWithoutEvicting(){
        underTest.put(1, 100);
        underTest.put(2, 200);
        underTest.put(1, 111); // update, not a new key

        assertThat(underTest.get(1)).isEqualTo(111);
        assertThat(underTest.get(2)).isEqualTo(200); // still there
    }

    @Test
    public void shouldEvictLeastRecentlyUsedWhenCapacityExceeded(){
        underTest.put(1, 100);
        underTest.put(2, 200);
        underTest.put(3, 300); // capacity 2, evicts key 1

        assertThat(underTest.get(1)).isEqualTo(-1);
        assertThat(underTest.get(2)).isEqualTo(200);
        assertThat(underTest.get(3)).isEqualTo(300);
    }

    @Test
    public void shouldTreatGetAsUsageProtectingFromEviction(){
        underTest.put(1, 100);
        underTest.put(2, 200);
        underTest.get(1); // key 1 is now most-recently-used
        underTest.put(3, 300); // capacity 2, should evict key 2 instead

        assertThat(underTest.get(1)).isEqualTo(100);
        assertThat(underTest.get(2)).isEqualTo(-1);
        assertThat(underTest.get(3)).isEqualTo(300);
    }

    @Test
    public void shouldTreatPutOnExistingKeyAsUsage(){
        underTest.put(1, 100);
        underTest.put(2, 200);
        underTest.put(1, 111); // updates key 1, marks it most-recently-used
        underTest.put(3, 300); // capacity 2, should evict key 2

        assertThat(underTest.get(1)).isEqualTo(111);
        assertThat(underTest.get(2)).isEqualTo(-1);
        assertThat(underTest.get(3)).isEqualTo(300);
    }
}
