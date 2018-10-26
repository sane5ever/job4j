package ru.job4j.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        return list.stream()
                .collect(Collectors.toMap(
                        User::getId,
                        Function.identity(),
                        (older, newer) -> newer,
                        HashMap::new
                ));
    }
}