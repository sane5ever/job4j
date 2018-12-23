package ru.job4j.cache;

/**
 * Модель.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-23
 */
public class Base {
    int id;
    int version;

    public Base(int id) {
        this.id = id;
        this.version = 0;
    }
}
