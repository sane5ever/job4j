package ru.job4j.generic;

/**
 * Контейнер для моделей-пользователей.
 */
public class UserStore extends AbstractStore<User> {
    public UserStore() {
        super();
    }

    public UserStore(int capacity) {
        super(capacity);
    }
}
