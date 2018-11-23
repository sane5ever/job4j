package ru.job4j.tree;

import java.util.*;

/**
 * Элементарная структура дерева.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-22
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    /**
     * Ссылка на корневой элемент дерева.
     */
    private Node<E> root;
    /**
     * Счётчик вносимых структурных изменений (для обеспечения fail-fast поведение итератора).
     */
    private int modCount;

    /**
     * Формирует начальное состояние дерева, устанавливает переданный элемент в кач-ве корневого.
     *
     * @param root значение корневого элемента
     */
    public Tree(E root) {
        this.root = new Node<>(root);
    }

    /**
     * Добавляет новый дочерний элемент к заданному элементу.
     *
     * @param parent уже существующий элемент дерева
     * @param child  добавляемый потомок
     * @return true, если успешно
     */
    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        if (!this.findBy(child).isPresent()) {
            this.findBy(parent).ifPresent(
                    node -> node.add(new Node<>(child))
            );
            result = true;
            this.modCount++;
        }
        return result;
    }

    /**
     * Осуществляет поиск заданного элемента в дереве.
     *
     * @param value искомое значение
     * @return контейнер, кот. содержит искомый элемент (если присутствует)
     */
    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> element = data.poll();
            if (element.eqValue(value)) {
                result = Optional.of(element);
                break;
            }
            for (Node<E> child : element.leaves()) {
                data.offer(child);
            }
        }
        return result;
    }

    /**
     * Возвращает итератор для последовательного прохода в ширину по элементам дерева.
     *
     * @return итератор
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int expectedModCount = Tree.this.modCount;

            private Queue<Node<E>> data = new LinkedList<>();

            {
                this.data.offer(Tree.this.root);
            }

            @Override
            public boolean hasNext() {
                this.checkModCount();
                return !this.data.isEmpty();
            }

            @Override
            public E next() {
                this.checkModCount();
                Node<E> element = this.data.remove();
                for (Node<E> child : element.leaves()) {
                    this.data.offer(child);
                }
                return element.getValue();
            }

            private void checkModCount() {
                if (Tree.this.modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}
