package ru.job4j.cache;

/**
 * Модель.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-23
 */
public class Base {
    private final int id;
    private String info;

    private int version;

    public Base(int id) {
        this.id = id;
        this.version = 0;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void updateVersion() {
        this.version++;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}