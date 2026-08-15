package org.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day4Test
{
    @Test
    public void shouldRotateKElements(){
        int[] data = new int[]{1, 2, 3, 4, 5};

        Day4.rotateArray(data, 2);

        assertThat(data).isEqualTo(new int[]{4, 5, 1, 2, 3});
    }

    @Test
    public void shouldReverseIteratively(){
        Day4.ListNode head = fromArray(1, 2, 3, 4, 5);

        Day4.ListNode reversed = Day4.reverseIterative(head);

        assertThat(toArray(reversed)).isEqualTo(new int[]{5, 4, 3, 2, 1});
    }

    @Test
    public void shouldReverseIterativelyWhenEmpty(){
        assertThat(Day4.reverseIterative(null)).isNull();
    }

    @Test
    public void shouldReverseIterativelyWhenSingleNode(){
        Day4.ListNode head = fromArray(1);

        Day4.ListNode reversed = Day4.reverseIterative(head);

        assertThat(toArray(reversed)).isEqualTo(new int[]{1});
    }

    private static Day4.ListNode fromArray(int... values){
        Day4.ListNode dummy = new Day4.ListNode(0);
        Day4.ListNode tail = dummy;

        for (int value : values){
            tail.next = new Day4.ListNode(value);
            tail = tail.next;
        }

        return dummy.next;
    }

    private static int[] toArray(Day4.ListNode head){
        List<Integer> values = new ArrayList<>();

        for (Day4.ListNode node = head; node != null; node = node.next){
            values.add(node.val);
        }

        return values.stream().mapToInt(Integer::intValue).toArray();
    }
}
