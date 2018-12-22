package ru.job4j.queue;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

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
    private int number = ThreadLocalRandom.current().nextInt(1000);

    private final Thread producer = new Thread(() -> {
        try {
            queue.offer(number);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });

    private final Thread consumer = new Thread(() -> {
        try {
            number -= queue.poll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });

    @Test
    public void whenProducerOffersIntThenConsumerPollsSameOne() throws InterruptedException {
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();

        assertThat(number, is(0));
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
        consumer.join();

        assertThat(number, is(0));
    }

    @Test
    public void whenConsumerMeetsEmptyQueueThenItWaits() throws InterruptedException {
        consumer.start();
        Thread.sleep(10);
        queue.offer(this.number);
        consumer.join();

        assertThat(number, is(0));
        assertTrue(queue.isEmpty());
    }

}
