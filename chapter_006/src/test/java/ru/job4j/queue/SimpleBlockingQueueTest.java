package ru.job4j.queue;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Bounded Blocking Queue. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-21
 */
public class SimpleBlockingQueueTest {
    private final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
    private final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
    private final List<Integer> expected = List.of(0, 1, 2, 3, 4);

    private final Thread producer = new Thread(() -> {
        for (int i = 0; i < 5; i++) {
            try {
                queue.offer(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private final Thread consumer = new Thread(() -> {
        while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
            try {
                buffer.add(queue.poll());
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    });

    @Test
    public void whenProducerOffersIntThenConsumerPollsSameOne() throws InterruptedException {
        producer.start();
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();

        assertThat(buffer, is(expected));
    }

    @Test
    public void whenProducerMeetsOverflowThenItWaits() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            queue.offer(0);
        }
        assertFalse(queue.isEmpty());

        producer.start();
        Thread.sleep(10);

        for (int i = 0; i < 10; i++) {
            queue.poll();
        }
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();

        assertThat(buffer, is(expected));
    }

    @Test
    public void whenConsumerMeetsEmptyQueueThenItWaits() throws InterruptedException {
        consumer.start();
        Thread.sleep(10);
        producer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();

        assertThat(buffer, is(expected));
        assertTrue(queue.isEmpty());
    }

}