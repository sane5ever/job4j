package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Потокобезопасный динамический список.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-21
 */
@ThreadSafe
public class SimpleConcurrentArrayList<E> implements Iterable<E> {
    /**
     * Динамический массив, используемый для хранения элементов контейнера.
     */
    @GuardedBy("lock")
    private final SimpleArrayList<E> array;

    /**
     * Лок для запирания меняющих внутреннее состояние хранилище операций.
     */
    private final Object lock = new Object();

    /**
     * Создаёт пустое хранилище с начальной вместимостью по-умолчанию.
     */
    public SimpleConcurrentArrayList() {
        array = new SimpleArrayList<>();
    }

    /**
     * Создаёт пустое хранилище с заданной начальной вместимостью.
     *
     * @param capacity начальная вместимость
     */
    public SimpleConcurrentArrayList(int capacity) {
        array = new SimpleArrayList<>(capacity);
    }

    /**
     * Возвращает количество элементов, содержащихся в списке.
     *
     * @return кол-во эл-тов в списке
     */
    public int size() {
        synchronized (lock) {
            return array.size();
        }
    }

    /**
     * Добавляет заданный элемент в конец списка.
     *
     * @param value элемент для добавления
     * @return <tt>true</tt>, если успешно
     */
    public boolean add(E value) {
        synchronized (lock) {
            return array.add(value);
        }
    }

    /**
     * Возвращает элемент по его порядковому номеру в списке.
     *
     * @param index порядковый номер элемента
     * @return элемент, расположенный в списке на заданной позиции
     */
    public E get(int index) {
        synchronized (lock) {
            return array.get(index);
        }
    }

    /**
     * Итератор для многопоточной коллекции.
     *
     * @return fail-safe итератор
     */
    @Override
    public Iterator<E> iterator() {
        synchronized (lock) {
            return copy(array).iterator();
        }
    }

    /**
     * Snapshot текущего состояния хранилища, необходимый для работы fail-safe итератора.
     *
     * @param original актуальное хранилище на текущий момент
     * @return коллекция-копия
     */
    private Iterable<E> copy(SimpleArrayList<E> original) {
        return new Iterable<>() {
            private final Object[] data = new Object[original.size()];

            private int size = original.size();
            private int cursor;

            {
                for (int i = 0; i < size; i++) {
                    data[i] = original.get(i);
                }
            }

            @Override
            public Iterator<E> iterator() {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return cursor != size;
                    }

                    @Override
                    @SuppressWarnings("unchecked")
                    public E next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return (E) data[cursor++];
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}
