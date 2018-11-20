package ru.job4j.list;

/**
 * Оеспечивает работу алгоритма, определяющего, что связанный список содержит замыкания.
 * Список содержит замыкания, если встречается хотя бы один элемент, next-указатель которого
 * ссылается на элемент уже встречавшийся ранее в списке.
 * Без создания вспомогательных структур проверить это условие можно путём сравнения каждого next-указателя
 * последовательно со всеми элементами, кот. располагаются в списке, начиная с первого и до текущего включительно
 * (если элемент ссылается сам на себя).
 *
 * @author Alexander Savchenko
 * @version 1.0
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
        boolean result = false;
        int index = 1;
        for (Node<T> cursor = first.next; cursor != null && !result; cursor = cursor.next, index++) {
            result = this.isPresentBefore(first, cursor, index);
        }
        return result;
    }

    /**
     * Проверяет, совпадает ли указатель с каким-то элементом списка, идущим до него.
     * Условие соблюдается, если указатель равен элементу, индекс кот. меньше индекса указателя.
     *
     * @param first   head-элемент списка
     * @param current проверяемый указатель
     * @param index   колво элементов, кот. необходимо проверить
     * @return true, если элемент встречается ранее
     */
    private <T> boolean isPresentBefore(Node<T> first, Node<T> current, int index) {
        boolean result = false;
        Node<T> cursor = first;
        for (int i = 0; i < index; i++) {
            if (current == cursor) {
                result = true;
                break;
            }
            cursor = cursor.next;
        }
        return result;
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


