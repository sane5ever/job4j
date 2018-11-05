package ru.job4j.tracker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Используется как хранилище заявок.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-02
 */
public class Tracker {

    /**
     * массив для хранения заявок
     */
    private final List<Item> items = new ArrayList<>();

    /**
     * для генерации уникального номера
     */
    private static final Random RN = new Random();

    /**
     * реализует добавление заявок
     *
     * @param item новая заявка
     * @return успешно добавленная заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        item.setCreate(System.currentTimeMillis());
        this.items.add(item);
        return item;
    }

    /**
     * генерирует уникалньый ключ для заявки
     *
     * @return уникальный ключ
     */
    private String generateId() {
        return String.valueOf((RN.nextInt() + System.currentTimeMillis()) % 100000000);
    }

    /**
     * реализует редактирование заявки
     *
     * @param id   уникальный ключ заменяемой заявки
     * @param item заменяющая заявка
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        int index = this.findPositionById(id);
        if (index != -1) {
            result = true;
            item.setId(id);
            item.setCreate(this.items.get(index).getCreate());
            this.items.set(index, item);
        }
        return result;
    }

    /**
     * реализует удаление заявки
     *
     * @param id уникальный ключ удаляемой заявки
     */
    public boolean delete(String id) {
        boolean result = false;
        int index = this.findPositionById(id);
        if (index != -1) {
            result = true;
            this.items.remove(index);
        }
        return result;
    }

    /**
     * реализует получение списка всех заявок
     *
     * @return массив с заявками
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * реализует получение списка заявок с совпадающим именем
     *
     * @param key имя искомых заявок
     * @return массив заявок
     */
    public List<Item> findByName(String key) {
        return this.items.stream().filter(
                item -> item.getName().equals(key)
        )
                .collect(Collectors.toList());
    }

    /**
     * реализует получение заявки по уникальному ключу (при отсутствии - null)
     *
     * @param id уникальный ключ
     * @return заявка
     */
    public Item findById(String id) {
        int index = this.findPositionById(id);
        return index != -1 ? this.items.get(index) : null;
    }

    /**
     * возвращает позицию заявки в массиве по её уникальному ключу, при отсутствиии возвращает -1
     *
     * @param id уникальный ключ
     * @return позиция в массиве
     */
    private int findPositionById(String id) {
        return IntStream.range(0, this.items.size())
                .filter(index -> this.items.get(index).getId().equals(id))
                .findFirst().orElse(-1);

    }
}