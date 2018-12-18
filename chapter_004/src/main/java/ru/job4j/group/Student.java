package ru.job4j.group;

/**
 * Студент.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-19
 */
public class Student {
    private String name;
    private int scope;

    public Student(String name, int scope) {
        this.name = name;
        this.scope = scope;
    }

    public int getScope() {
        return scope;
    }
}
