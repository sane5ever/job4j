package ru.job4j.list;

/**
 * Класс SimpleArrayList.
 */
public class SimpleArrayList<E> {
    private int size;
    private Node<E> first;

    /**
     * Метод вставляет в начало списка данные.
     *
     * @param date данные для вставки
     */
    public void add(E date) {
        Node<E> newLink = new Node<>(date);
        newLink.next = this.first;
        this.first = newLink;
        this.size++;
    }

    /**
     * Реализовать метод удаления первого элемент в списке.
     *
     * @return удалённые данные
     */
    public E delete() {
        E deleted = null;
        if (this.first != null) {
            deleted = this.first.date;
            this.first = this.first.next;
            this.size--;
        }
        return deleted;
    }

    /**
     * Метод получения элемента по индексу.
     *
     * @param index индекс элемента
     * @return запрошенные данные
     */
    public E get(int index) {
        Node<E> result = this.first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.date;
    }

    /**
     * Метод получения размера коллекции.
     *
     * @return кол-во элементов
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Класс предназначен для хранения данных.
     */
    private static class Node<E> {
        E date;
        Node<E> next;

        Node(E date) {
            this.date = date;
        }
    }
}
