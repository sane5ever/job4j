package ru.job4j.generic;

/**
 * Класс представляет скелет-заготовку, на основе кот.
 * реализуются конечные модели данных.
 */
public abstract class Base {
    /**
     * уникальный номер модели
     */
    private final String id;

    /**
     * инициализируем объект id
     *
     * @param id уникальный номер
     */
    protected Base(final String id) {
        this.id = id;
    }

    /**
     * возвращаем пользователю id модели
     *
     * @return уникальный номер
     */
    public String getId() {
        return this.id;
    }
}
