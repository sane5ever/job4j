package ru.job4j.sort;

import java.util.*;

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
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(o1.getName().length(), o2.getName().length());
            }
        });
        return users;
    }

    /**
     * Сортируем список пользователей по обоим полям класса User.
     *
     * @param users список пользователей
     * @return отсортированный список
     */
    public List<User> sortByAllFields(List<User> users) {
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                final int result = o1.getName().compareTo(o2.getName());
                return result != 0 ? result : o1.compareTo(o2);
            }
        });
        return users;
    }
}