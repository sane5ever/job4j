package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Динамический контейнер на базе связанного списка.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-18
 */
public class SimpleLinkedList<E> implements Iterable<E> {
    /**
     * Счётчик вносимых структурных изменений (для обеспечения fail-fast поведение итератора).
     */
    private int modCount;
    /**
     * Количество добавленных элементов в список.
     */
    private int size;
    /**
     * Указатель на первый элемент списка.
     */
    private Node<E> first;

    /**
     * Добавляет заданный элемент в начало списка.
     *
     * @param value элемент для добавления
     * @return true, при успешном добавлении
     */
    public boolean add(E value) {
        this.modCount++;
        Node<E> fresh = new Node<>(value);
        fresh.next = this.first;
        this.first = fresh;
        this.size++;
        return true;
    }

    /**
     * Возвращает элемент по его порядковому номеру в списке.
     *
     * @param index порядковый номер элемента
     * @return элемент, расположенный в списке на заданной позиции
     * @throws IndexOutOfBoundsException если индекс выходит за диапазон допустимых значений
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = this.first;
        for (int i = 0; i != index; i++) {
            current = current.next;
        }
        return (E) current.item;
    }

    /**
     * Возвращает итератор для последовательного прохода по элементам списка.
     *
     * @return итератор
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int expectedModCount = SimpleLinkedList.this.modCount;
            private Node<E> recent = null;
            private Node<E> previous = null;
            private Node<E> incoming = SimpleLinkedList.this.first;

            @Override
            public boolean hasNext() {
                this.checkModCount();
                return this.incoming != null;
            }

            @Override
            public E next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.previous = this.recent;
                this.recent = this.incoming;
                this.incoming = this.incoming.next;
                return this.recent.item;
            }

            @Override
            public void remove() {
                this.checkModCount();
                if (this.recent == null) {
                    throw new UnsupportedOperationException();
                }
                if (this.previous == null) {
                    SimpleLinkedList.this.first = this.recent.next;
                } else {
                    this.previous.next = this.recent.next;
                }

                this.recent = null;
                this.previous = null;
            }

            private void checkModCount() {
                if (SimpleLinkedList.this.modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    /**
     * Класс предназначен для хранения данных.
     */
    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item) {
            this.item = item;
        }
    }
}
