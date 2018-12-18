package ru.job4j.generic;

/**
 * Интерфейс, предоставляющий API для хранения объектов-наследников Base.
 */
public interface Store<T extends Base> {
    /**
     * добавляем элемент в контейнер
     *
     * @param model элемент
     */
    void add(T model);

    /**
     * заменяем элемент с заданным id на новый
     *
     * @param id    уникальный номер
     * @param model элемент на замену
     * @return true, если успешно
     */
    boolean replace(String id, T model);

    /**
     * удаляем элемент с заданным id
     *
     * @param id уникальный номер
     * @return true, если успешно
     */
    boolean delete(String id);

    /**
     * поиск в контейнере эл-та с заданным id
     *
     * @param id уникальный номер
     * @return разыскиваемый эл-т (null при отсутствии)
     */
    T findById(String id);
}
