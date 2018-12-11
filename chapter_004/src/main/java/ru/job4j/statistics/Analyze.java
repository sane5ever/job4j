package ru.job4j.statistics;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Map<Integer, User> users = previous.stream().collect(
                Collectors.toMap(
                        user -> user.id,
                        Function.identity())
        );
        current.forEach(user -> {
            User temp = users.remove(user.id);
            if (temp == null) {
                result.added++;
            } else if (!temp.name.equals(user.name)) {
                result.changed++;
            }
        });
        result.deleted = users.size();
        return result;
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
