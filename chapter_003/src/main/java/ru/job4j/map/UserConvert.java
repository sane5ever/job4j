package ru.job4j.map;

import java.util.HashMap;
import java.util.List;

/**
 * Преобразование списка пользователей в Map.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-17
 */
public class UserConvert {
    /**
     * Помещаем переданный список пользователей в Map.
     * В качестве ключа используем ID пользователя.
     *
     * @param list список пользователей
     * @return хеш-таблица пользователей
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (User user : list) {
            result.put(user.getId(), user);
        }
        return result;
    }
}
