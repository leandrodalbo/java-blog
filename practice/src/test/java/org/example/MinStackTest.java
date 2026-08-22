package org.example;

import org.junit.jupiter.api.Test;
import java.util.EmptyStackException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MinStackTest
{
    private MinStack underTest = new MinStack();

    @Test
    public void shouldTrackMinAsElementsArePushed(){
        underTest.push(5);
        assertThat(underTest.getMin()).isEqualTo(5);

        underTest.push(3);
        assertThat(underTest.getMin()).isEqualTo(3);

        underTest.push(7);
        assertThat(underTest.getMin()).isEqualTo(3);
    }

    @Test
    public void shouldRestorePreviousMinAfterPoppingDuplicateMin(){
        underTest.push(5);
        underTest.push(3);
        underTest.push(3);
        underTest.push(7);

        underTest.pop(); // removes 7
        assertThat(underTest.getMin()).isEqualTo(3);

        underTest.pop(); // removes one of the 3s, other 3 still there
        assertThat(underTest.getMin()).isEqualTo(3);

        underTest.pop(); // removes last 3
        assertThat(underTest.getMin()).isEqualTo(5);
    }

    @Test
    public void shouldReturnTopWithoutRemovingIt(){
        underTest.push(5);
        underTest.push(9);

        assertThat(underTest.top()).isEqualTo(9);
        assertThat(underTest.top()).isEqualTo(9); // unchanged, top() doesn't pop
    }

    @Test
    public void shouldThrowOnEmptyStackOperations(){
        assertThatThrownBy(() -> underTest.pop()).isInstanceOf(EmptyStackException.class);
        assertThatThrownBy(() -> underTest.top()).isInstanceOf(EmptyStackException.class);
        assertThatThrownBy(() -> underTest.getMin()).isInstanceOf(EmptyStackException.class);
    }
}
