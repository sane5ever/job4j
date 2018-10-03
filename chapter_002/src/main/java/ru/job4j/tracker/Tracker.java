package ru.job4j.tracker;

import java.util.*;

/**
 * Используется как хранилище заявок.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-02
 */
public class Tracker {

    /** массив для хранения заявок */
    private final Item[] items = new Item[100];

    /** указатель ячейки для новой заявки */
    private int position = 0;

    /** для генерации уникального номера */
    private static final Random RN = new Random();

    /**
     * реализует добавление заявок
     * @param item новая заявка
     * @return успешно добавленная заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        item.setCreate(System.currentTimeMillis());
        this.items[this.position++] = item;
        return item;
    }

    /**
     * генерирует уникалньый ключ для заявки
     * @return уникальный ключ
     */
    private String generateId() {
        return String.valueOf((RN.nextInt() + System.currentTimeMillis()) % 100000000);
    }

    /**
     * реализует редактирование заявки
     * @param id уникальный ключ заменяемой заявки
     * @param item заменяющая заявка
     */
    public void replace(String id, Item item) {
        int index = this.findPositionById(id);
        if (index != -1) {
            item.setCreate(this.items[index].getCreate());
            this.items[index] = item;
        }
    }

    /**
     * реализует удаление заявки
     * @param id уникальный ключ удаляемой заявки
     */
    public void delete(String id) {
        int index = this.findPositionById(id);
        if (index != -1) {
            System.arraycopy(this.items, index + 1, this.items, index, --this.position - index);
        }
    }

    /**
     * реализует получение списка всех заявок
     * @return массив с заявками
     */
    public Item[] findAll() {
        return Arrays.copyOf(this.items, position);
    }

    /**
     * реализует получение списка заявок с совпадающим именем
     * @param key имя искомых заявок
     * @return массив заявок
     */
    public Item[] findByName(String key) {
        Item[] result = new Item[this.position];
        int pos = 0;
        for (int index = 0; index != this.position; index++) {
            if (this.items[index].getName().equals(key)) {
                result[pos++] = this.items[index];
            }
        }
        return Arrays.copyOf(result, pos);
    }

    /**
     * реализует получение заявки по уникальному ключу (при отсутствии - null)
     * @param id уникальный ключ
     * @return заявка
     */
    public Item findById(String id) {
        int index = this.findPositionById(id);
        return index != -1 ? this.items[index] : null;
    }

    /**
     * возвращает позицию заявки в массиве по её уникальному ключу, при отсутствиии возвращает -1
     * @param id уникальный ключ
     * @return позиция в массиве
     */
    private int findPositionById(String id) {
        int result = -1;
        for (int index = 0; index != this.position; index++) {
            if (this.items[index].getId().equals(id)) {
                result = index;
                break;
            }
        }
        return result;
    }
}
