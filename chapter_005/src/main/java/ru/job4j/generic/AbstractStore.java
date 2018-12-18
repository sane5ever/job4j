package ru.job4j.generic;

import java.util.Iterator;

/**
 * Класс представляет скелет-заготовку, на основе кот. реализуются
 * конечные контейнеры типов.
 *
 * @param <T> расширяет класс {@link Base}
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-17
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {
    /**
     * размер хранилища по-умолчанию
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * хранилище объектов
     */
    private final SimpleArray<T> storage;

    /**
     * констуктор без параметров при инициализирии хранилища устанавливает ему размер по-умолчанию
     */
    public AbstractStore() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * конструктор при инициализации хранилища устанавливает ему заданный размер
     *
     * @param capacity размер хранилища
     */
    public AbstractStore(int capacity) {
        this.storage = new SimpleArray<>(capacity);
    }

    /**
     * добавляем элемент в хранилище
     *
     * @param model элемент
     */
    @Override
    public void add(T model) {
        this.storage.add(model);
    }

    /**
     * заменяем элемент с заданным id в хранилище на новый
     *
     * @param id    уникальный номер
     * @param model элемент на замену
     * @return true, если успешно
     */
    @Override
    public boolean replace(String id, T model) {
        int index = this.findIndexById(id);
        return index != -1 && this.storage.set(index, model);
    }

    /**
     * удаляем элемент с заданным id из хранилища
     *
     * @param id уникальный номер
     * @return true, если успешно
     */
    @Override
    public boolean delete(String id) {
        Iterator<T> iterator = this.storage.iterator();
        boolean result = false;
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * возвращаем элемент с заданым id пользователю
     *
     * @param id уникальный номер
     * @return искомый эл-т (null при отсутствии)
     */
    @Override
    public T findById(String id) {
        int index = this.findIndexById(id);
        return index != -1 ? this.storage.get(index) : null;
    }

    /**
     * вспомогательный метод для поиска индекса эл-та с заданным id
     *
     * @param id уникальный номер
     * @return индекс элемента (-1 при отсутствии)
     */
    private int findIndexById(String id) {
        int result = -1;
        for (int index = 0; index != this.storage.size(); index++) {
            if (this.storage.get(index).getId().equals(id)) {
                result = index;
                break;
            }
        }
        return result;
    }
}
