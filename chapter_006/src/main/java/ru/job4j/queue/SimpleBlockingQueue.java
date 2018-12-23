package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Bounded Blocking Queue.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-21
 */
@ThreadSafe
public class SimpleBlockingQueue<E> {
    /**
     * Вместимость очереди по-умолчанию.
     */
    private final static int DEFAULT_CAPACITY = 10;

    /**
     * Вместимость очереди.
     */
    private final int capacity;
    /**
     * Лок для запирания меняющих внутреннее состояние хранилище операций.
     */
    private final Object lock = new Object();
    /**
     * Количество элементов в очереди.
     */
    @GuardedBy("lock")
    private int size;
    /**
     * Хранилище элементов.
     */
    @GuardedBy("lock")
    private Queue<E> queue = new LinkedList<>();

    public SimpleBlockingQueue() {
        this(DEFAULT_CAPACITY);
    }

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Добавляет заданный элемент в очередь, ожидая появления свободного места
     * при необходимости.
     *
     * @param value добавляемый элемент
     */
    public void offer(E value) throws InterruptedException {
        synchronized (lock) {
            while (size == capacity) {
                lock.wait();
            }
            this.queue.offer(value);
            this.size++;
            lock.notify();
        }
    }

    /**
     * Возвращает и удаляет головной элемент в очереди, ожидая при необходимости
     * его появление в хранилище.
     *
     * @return головной элемент в очереди
     */
    public E poll() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();
            }
            this.size--;
            lock.notify();
            return this.queue.poll();
        }
    }

    /**
     * Возвращает <tt>true</tt>, если в очереди отсутствуют элементы.
     *
     * @return <tt>true</tt>, если в очереди отсутствуют элементы
     */
    public boolean isEmpty() {
        synchronized (lock) {
            return size == 0;
        }
    }
}
