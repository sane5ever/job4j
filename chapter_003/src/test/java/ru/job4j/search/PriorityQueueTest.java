package ru.job4j.search;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестируем работу списка задач.
 */
public class PriorityQueueTest {
    private final PriorityQueue queue = new PriorityQueue();

    @Before
    public void prepare() {
        queue.put(new Task("low", 5));
        queue.put(new Task("urgent", 1));
        queue.put(new Task("middle", 3));
    }

    @Test
    public void whenHigherPriority() {
        Task result = this.queue.take();
        assertThat(result.getDesc(), is("urgent"));
    }

    @Test
    public void whenLowerPriority() {
        this.queue.take();
        this.queue.take();
        Task result = this.queue.take();
        assertThat(result.getDesc(), is("low"));
    }

    @Test
    public void whenDoubleMaxPriority() {
        this.queue.put(new Task("urgent too", 1));
        Task result = this.queue.take();
        assertThat(result.getDesc(), is("urgent"));
    }
}