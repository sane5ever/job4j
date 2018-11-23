package ru.job4j.set;

import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;

/**
 * Множество на базе динамического списка.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-20
 */
public class SimpleArraySet<E> implements Iterable<E> {
    /**
     * Динамический список для хранения элементов множества.
     */
    private SimpleArrayList<E> buffer = new SimpleArrayList<>();

    /**
     * Добавляет в множество новый элемент (при отсутствии).
     */
    public void add(E e) {
        if (!this.contains(e)) {
            this.buffer.add(e);
        }
    }

    /**
     * Проверяет наличие переданного объекта в множестве.
     *
     * @param e искомый элемент
     * @return true, если объект присутствует
     */
    public boolean contains(E e) {
        boolean result = false;
        for (E element : this) {
            if (element.equals(e)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Возвращает итератор для прохода по элементам множества.
     * Используется итератор для динамического списка.
     *
     * @return итератор
     */
    @Override
    public Iterator<E> iterator() {
        return this.buffer.iterator();
    }
}