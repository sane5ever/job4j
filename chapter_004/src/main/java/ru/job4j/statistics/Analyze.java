package ru.job4j.statistics;

import java.util.List;
import java.util.function.Predicate;

/**
 * Статистика по коллекции.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-07
 */
public class Analyze {
    /**
     * Анализирует прошлую и текущую версии коллекций, возвращает статистику изменений.
     *
     * @param previous прошлая версия
     * @param current  текущая версия
     * @return статистика изменений
     */
    public Info diff(List<User> previous, List<User> current) {
        Info result = new Info();
        result.deleted = this.count(previous, user -> this.hasNotMatchId(current, user.id));
        result.added = this.count(current, user -> hasNotMatchId(previous, user.id));
        result.changed = this.count(current, user -> hasMatchIdDiffName(previous, user.id, user.name));
        return result;
    }

    /**
     * Возвращает количество элементов коллекции, удовлетворяющие переданному условию.
     *
     * @param users     переданная коллекция
     * @param predicate условие
     * @return количество элементов
     */
    private int count(List<User> users, Predicate<User> predicate) {
        return users.stream()
                .filter(predicate)
                .mapToInt(el -> 1)
                .reduce(0, Integer::sum);
    }

    /**
     * Проверяет, отсутствует ли в коллекции элемент с заданным id.
     *
     * @param users переданная коллекция
     * @param id    искомый id
     * @return true, если элемент с заданым id не найден
     */
    private boolean hasNotMatchId(List<User> users, int id) {
        return users.stream().noneMatch(user -> user.id == id);
    }

    /**
     * Проверяет, присутствует ли в коллекции пользователь с заданным id, но отличным именем.
     *
     * @param users переданная коллекция
     * @param id    искомый id
     * @param name  имя
     * @return true, если у пользователя с id иное имя
     */
    private boolean hasMatchIdDiffName(List<User> users, int id, String name) {
        return users.stream().anyMatch(user -> user.id == id && !user.name.equals(name));
    }

    /**
     * Пользователь.
     */
    public static class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    /**
     * Контейнер для результатов статистики.
     */
    public static class Info {
        int added;
        int changed;
        int deleted;
    }
}
