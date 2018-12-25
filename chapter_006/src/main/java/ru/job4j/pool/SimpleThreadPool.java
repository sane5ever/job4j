package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

/**
 * Пул нитей.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-25
 */
public class SimpleThreadPool {
    /**
     * Список для хранения нитей.
     */
    private final List<Thread> threads = new LinkedList<>();
    /**
     * Очередь задач.
     */
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();
    /**
     * Переключатель на завершение работы.
     */
    private volatile boolean onWork = true;

    public SimpleThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (onWork) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    /**
     * Добавляет в пул новую задачу.
     *
     * @param job новая задача
     * @throws RejectedExecutionException при попытке добавить задание после завершения работы пула
     * @throws InterruptedException       при нарушении работы нити-исполнителя
     */
    public void work(Runnable job) throws InterruptedException {
        if (!onWork) {
            throw new RejectedExecutionException();
        }
        tasks.offer(job);
    }

    /**
     * Завершает работу пула.
     */
    public void shutdown() {
        onWork = false;
        threads.forEach(Thread::interrupt);
    }
}
