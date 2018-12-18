package ru.job4j.list;

/**
 * Очередь на двух стеках.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-20
 */
public class SimpleQueue<E> {
    /**
     * Стек для добавления элементов в очередь.
     */
    private final SimpleStack<E> input = new SimpleStack<>();
    /**
     * Стек для получения элементов из очереди.
     */
    private final SimpleStack<E> output = new SimpleStack<>();

    /**
     * Возвращает первый добавленный в очередь элемент, исключая его из неё.
     * При отсутствии в output-стеке элементов осуществляем их перенос из input-стека.
     *
     * @return первый элемент (null при пустой очереди)
     */
    public E poll() {
        if (this.output.isEmpty()) {
            this.shift(this.input, this.output);
        }
        return this.output.poll();
    }

    /**
     * Добавляет в конец очереди новый элемент.
     * При отсутствии в input-стеке элементов осуществляем их перенос из output-стека.
     *
     * @param value добавляемое значение
     */
    public void push(E value) {
        this.input.push(value);
    }

    /**
     * Производим перенос элементов из одного стека в другой.
     * При переносе порядок следования становится обратным.
     * При отсутствии в источнике элементов никаких изменений не происходит.
     *
     * @param from источник элементов
     * @param dest приёмник элементов
     */
    private void shift(SimpleStack<E> from, SimpleStack<E> dest) {
        E current = from.poll();
        while (current != null) {
            dest.push(current);
            current = from.poll();
        }
    }
}
