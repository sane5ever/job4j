package ru.job4j.email;

/**
 * Пользователь. Модель.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-26
 */
public class User {
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
