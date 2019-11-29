package ru.job4j.pool;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

/**
 * Пул нитей. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-25
 */
public class SimpleThreadPoolTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Set<Integer> result = new CopyOnWriteArraySet<>();
    private final Set<Integer> expected = Set.of(
            0, 1, 4, 9, 16,
            25, 36, 49, 64, 81,
            100, 121, 144, 169, 196,
            225, 256, 289, 324, 361
    );
    private final AtomicInteger count = new AtomicInteger(0);

    private Runnable task = () -> {
        int num = count.getAndIncrement();
        result.add(num * num);
    };
    SimpleThreadPool pool = new SimpleThreadPool();

    @Test
    public void when20TasksToComplete() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            pool.work(task);
        }
        while (result.size() < 20) {
            Thread.sleep(1);
        }
        assertThat(result, is(expected));
    }

    @Test
    public void whenShutdownThreadPool() throws InterruptedException {
        var latch = new CountDownLatch(100);

        Thread addThread = new Thread(
                () ->
                        IntStream.range(0, 10000).forEach(i -> {
                            try {
                                pool.work(task);
                                latch.countDown();
                            } catch (InterruptedException | RejectedExecutionException e) {
                                Thread.currentThread().interrupt();
                            }
                        }));
        Thread shutdownThread = new Thread(() -> pool.shutdown());
        addThread.start();
        latch.await(); // wait until part of tasks would be added

        shutdownThread.start();
        addThread.join();
        assertNotEquals(result.size(), 10000);
    }

    @Test
    public void whenAddTasksAfterShutdown() throws InterruptedException {
        pool.shutdown();
        thrown.expect(RejectedExecutionException.class);
        pool.work(
                () -> {
                });
    }
}
