package ru.job4j.generic;

/**
 * Контейнер для моделей-полномочий.
 */
public class RoleStore extends AbstractStore<Role> {
    public RoleStore() {
        super();
    }

    public RoleStore(int capacity) {
        super(capacity);
    }
}
