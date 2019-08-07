package ru.job4j.tracker;

import java.util.List;

/**
 * Template to implement tracker classes
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-27
 */
public interface ITracker {
    /**
     * реализует добавление заявок
     *
     * @param item новая заявка
     * @return успешно добавленная заявка
     */
    Item add(Item item);

    /**
     * реализует редактирование заявки
     *
     * @param id   уникальный ключ заменяемой заявки
     * @param item заменяющая заявка
     */
    boolean replace(String id, Item item);

    /**
     * реализует удаление заявки
     *
     * @param id уникальный ключ удаляемой заявки
     */
    boolean delete(String id);
    /**
     * реализует получение списка всех заявок
     *
     * @return массив с заявками
     */
    List<Item> findAll();
    /**
     * реализует получение списка заявок с совпадающим именем
     *
     * @param key имя искомых заявок
     * @return массив заявок
     */
    List<Item> findByName(String key);
    /**
     * реализует получение заявки по уникальному ключу (при отсутствии - null)
     *
     * @param id уникальный ключ
     * @return заявка
     */
    Item findById(String id);
}
