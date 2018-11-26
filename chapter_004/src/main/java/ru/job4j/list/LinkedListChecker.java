package ru.job4j.list;

/**
 * Оеспечивает работу алгоритма, определяющего, что связанный список содержит замыкания.
 * Используется алгоритм Флойда поиска цикла (Floyd's Tortoise and Hare).
 *
 * @author Alexander Savchenko
 * @version 1.01 (2018-11-26)
 * @since 2018-11-20
 */
public class LinkedListChecker {
    /**
     * Определяет  наличие замыканий в связанном списке.
     *
     * @param first head-элемент списка
     * @return true, при нахождении первого же замыкания
     */
    public <T> boolean hasCycle(Node<T> first) {
        Node<T> basicStep = first.next;
        Node<T> doubleStep = first.next != null ? first.next.next : null;
        while (doubleStep != null && basicStep != doubleStep) {
            basicStep = basicStep.next;
            doubleStep = doubleStep.next != null ? doubleStep.next.next : null;
        }
        return doubleStep != null;
    }
}

/**
 * Класс обеспечивает элементарную реализацию связанного списка.
 */
class Node<T> {
    T value;
    Node<T> next;

    public Node(T value) {
        this.value = value;
    }
}


