package ru.job4j.sort;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
}