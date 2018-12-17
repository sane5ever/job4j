package ru.job4j.counter;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Счётчик.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-17
 */

@ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

    public void increment() {
        synchronized (this) {
            this.value++;
        }
    }

    public int get() {
        synchronized (this) {
            return this.value;
        }
    }
}
