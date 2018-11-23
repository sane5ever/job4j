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

    @Test
    public void when() {
        this.queue.push(0);
        this.queue.push(1);
        this.queue.push(2);
        this.queue.push(3);
        assertThat(this.queue.poll(), is(0));
        assertThat(this.queue.poll(), is(1));
        this.queue.push(4);
        this.queue.push(5);
        assertThat(this.queue.poll(), is(2));
        assertThat(this.queue.poll(), is(3));
        this.queue.push(6);
        assertThat(this.queue.poll(), is(4));
        assertThat(this.queue.poll(), is(5));
        assertThat(this.queue.poll(), is(6));
    }
}
