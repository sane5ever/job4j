package ru.job4j.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Динамический список на базе массива.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-18
 */
public class SimpleArrayList<E> implements Iterable<E> {
    /**
     * Начальная вместимость списка по-умолчанию.
     */
    private final static int DEFAULT_CAPACITY = 10;
    /**
     * Счётчик вносимых структурных изменений (для обеспечения fail-fast поведение итератора)
     */
    private int modCount;
    /**
     * Количество добавленных элементов в список
     */
    private int size;
    /**
     * Буфер-массив, используется для хранения элементов списка.
     */
    private Object[] container;

    /**
     * Формирует пустой список с начальной вместимостью по-умолчанию.
     */
    public SimpleArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Формирует пустой список с заданной начальной вместимостью.
     *
     * @param capacity начальная вместимость
     * @throws IllegalArgumentException при попытке задать отрицательную вместимость
     */
    public SimpleArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.container = new Object[capacity];
    }

    /**
     * Возвращает количество элементов, содержащихся в списке.
     *
     * @return кол-во эл-тов в списке
     */
    public int size() {
        return size;
    }

    /**
     * Добавляет заданный элемент в конец списка.
     *
     * @param value элемент для добавления
     * @return true, при успешном добавлении
     */
    public boolean add(E value) {
        this.checkCapacity(this.size + 1);
        this.modCount++;
        this.container[this.size++] = value;
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
        return (E) this.container[index];
    }

    /**
     * Проверяет необходимость расширения буфера, если она есть — расширяет его.
     *
     * @param minSize минимально необходимый размер нового буфера
     *                (для корректного расширения при малых начальных размерах)
     */
    private void checkCapacity(int minSize) {
        if (this.size == this.container.length) {
            int newSize = Math.max(minSize, this.size + this.size >> 1);
            this.container = Arrays.copyOf(this.container, newSize);
        }
    }

    /**
     * Возвращает итератор для последовательного прохода по элементам списка.
     *
     * @return итератор
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int expectedModCount = SimpleArrayList.this.modCount;
            private int last = -1;
            private int cursor;

            @Override
            public boolean hasNext() {
                this.checkModCount();
                return this.cursor != SimpleArrayList.this.size;
            }

            @Override
            public E next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.cursor++;
                return SimpleArrayList.this.get(this.last);
            }

            @Override
            public void remove() {
                this.checkModCount();
                if (this.last == -1) {
                    throw new UnsupportedOperationException();
                }
                System.arraycopy(container, this.last + 1, container, this.last, --size - this.last);
                this.last = -1;
                this.cursor--;

            }

            private void checkModCount() {
                if (SimpleArrayList.this.modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }
}
