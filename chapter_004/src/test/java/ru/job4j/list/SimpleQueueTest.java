package ru.job4j.list;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Очередь на двух стеках. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-20
 */
public class SimpleQueueTest {
    private SimpleQueue<Integer> queue = new SimpleQueue<>();

    @Test
    public void whenEmptyQueuePollInvocationShouldReturnNull() {
        assertNull(this.queue.poll());
    }

    @Test
    public void whenFiveElementPushThenPollInvocationShouldReturnThemInInputOrder() {
        IntStream.range(0, 5).forEach(this.queue::push);
        IntStream.range(0, 5).forEach(i -> assertThat(this.queue.poll(), is(i)));
    }

    @Test
    public void whenPushElementThenPollReturnsSameElement() {
        IntStream.range(0, 5).forEach(i -> {
                this.queue.push(i);
                assertThat(this.queue.poll(), is(i));
                }
        );
    }
}
