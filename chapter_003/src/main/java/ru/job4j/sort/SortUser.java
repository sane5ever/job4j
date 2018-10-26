package ru.job4j.sort;

import java.util.*;
import java.util.function.Function;

/**
 * Сортировщик пользователей.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-18
 */
public class SortUser {
    /**
     * Конвертируем список в отсортированное множество.
     *
     * @param users список пользователей
     * @return отсортированное множество
     */
    public Set<User> sort(List<User> users) {
        return new TreeSet<>(users);
    }

    /**
     * Сортируем список пользователей по длине имени пользователя.
     *
     * @param users список пользователей
     * @return отсортированный список
     */
    public List<User> sortNameLength(List<User> users) {
        users.sort(Comparator.comparingInt(user -> user.getName().length()));
        return users;
    }

    /**
     * Сортируем список пользователей по обоим полям класса User.
     *
     * @param users список пользователей
     * @return отсортированный список
     */
    public List<User> sortByAllFields(List<User> users) {
        users.sort(Comparator.comparing(User::getName).thenComparing(Function.identity()));
        return users;
    }
}