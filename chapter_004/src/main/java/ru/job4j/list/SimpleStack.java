package ru.job4j.list;

import java.util.Iterator;

/**
 * Контейнер Stack с использованием динамического контейнера
 * на базе связанного списка.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-19
 */
public class SimpleStack<E> {
    /**
     * Используем композиционную связь с {@link SimpleLinkedList}
     * для упрощения реализации функционала данного класса.
     */
    private final SimpleLinkedList<E> base = new SimpleLinkedList<>();

    /**
     * Возвращает верхний элемент стека, исключая его из списка.
     *
     * @return верхний элемент (null, при пустом стеке)
     */
    public E poll() {
        Iterator<E> iterator = this.base.iterator();
        E result = null;
        if (iterator.hasNext()) {
            result = iterator.next();
            iterator.remove();
        }
        return result;
    }

    /**
     * Добавляет в стек новый элемент (добавление осуществляется в начало списка)
     *
     * @param value добавляемое значение
     */
    public void push(E value) {
        this.base.add(value);
    }

    /**
     * Возвращает true, если в стеке нет элементов.
     *
     * @return true, если эл-тов нет
     */
    public boolean isEmpty() {
        return !this.base.iterator().hasNext();
    }
}
