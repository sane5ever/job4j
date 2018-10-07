package ru.job4j.tracker;

/**
 * Интерфейс-реализация пункта меню трекера.
 */
public interface UserAction {
    /**
     * Возвращаем ключ опции.
     * @return ключ
     */
    int key();

    /**
     * Основной метод.
     * @param input получение данных от пользователя
     * @param tracker хранилище заявок
     */
    void execute(Input input, Tracker tracker);

    /**
     * Возвращает информацию о данном пункте меню.
     * @return строка меню
     */
    String info();
}