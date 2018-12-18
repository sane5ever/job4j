package ru.job4j.list;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Контейнер Stack с использованием динамического контейнера
 * на базе связанного списка. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-19
 */
public class SimpleStackTest {
    private SimpleStack<Integer> stack = new SimpleStack<>();

    @Test
    public void whenEmptyStackPollInvocationShouldReturnNull() {
        assertNull(this.stack.poll());
    }

    @Test
    public void whenOneElementPushPollInvocationShouldReturnIt() {
        this.stack.push(0);
        assertThat(this.stack.poll(), is(0));
    }

    @Test
    public void whenFiveElementPushPollInvocationShouldReturnThemInReverse() {
        IntStream.range(0, 5).forEach(this.stack::push);
        IntStream.range(0, 5).forEach(i -> assertThat(this.stack.poll(), is(4 - i)));
    }
}
