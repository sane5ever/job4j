package ru.job4j.statistics;

import ru.job4j.statistics.model.Info;
import ru.job4j.statistics.model.User;

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
                        User::getId,
                        Function.identity())
        );
        current.forEach(user -> {
            User temp = users.remove(user.getId());
            if (temp == null) {
                result.incrementAdded();
            } else if (!temp.getName().equals(user.getName())) {
                result.incrementChanged();
            }
        });
        result.setDeleted(users.size());
        return result;
    }

}
