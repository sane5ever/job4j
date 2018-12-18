package ru.job4j.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Универсальная обёртка над массивом.
 *
 * @param <E> тип эл-тов массива
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-17
 */
public class SimpleArray<E> implements Iterable<E> {
    /**
     * массив, в кот. храним эл-ты
     */
    private Object[] data;
    /**
     * фактический размер массива (также указатель на первую свободную ячейку)
     */
    private int size;

    /**
     * задаём при инициализации необходимое кол-во ячееек
     *
     * @param capacity кол-во ячееек
     */
    public SimpleArray(int capacity) {
        this.data = new Object[capacity];
    }

    /**
     * возвращает кол-во добавленных эл-тов в хранилище
     *
     * @return колво эл-тов
     */
    public int size() {
        return this.size;
    }

    /**
     * добавляем в массив очередной эл-т
     * добавление возможно пока хранилище не заполнено
     *
     * @param model добавляемый эл-т
     * @return true, если успешно
     * @throws UnsupportedOperationException если размер хранилища максимален
     */
    public boolean add(E model) {
        if (this.size == this.data.length) {
            throw new UnsupportedOperationException();
        }
        this.data[this.size++] = model;
        return true;

    }

    /**
     * устанавливаем в заданную ячейку заданный эл-т
     * возможно если в ячейку уже был помещено значение
     *
     * @param index номер ячейки массива
     * @param model устанавливаемое значение
     * @return true, если успешно
     * @throws IndexOutOfBoundsException если вышли за диапазон индексов
     */
    public boolean set(int index, E model) {
        this.check(index);
        this.data[index] = model;
        return true;
    }

    /**
     * удаляем значение из заданной ячейки
     * возможно, если в ячейку уже было помещено значение
     *
     * @param index номер ячейки массива
     * @return удалённое значение
     * @throws IndexOutOfBoundsException если вышли за диапазон индексов
     */
    public E delete(int index) throws IndexOutOfBoundsException {
        E removed = this.get(index);
        System.arraycopy(this.data, index + 1, this.data, index, --this.size - index);
        this.data[this.size] = null;
        return removed;
    }

    /**
     * получаем значение из заданной ячейки
     * возможно, если в ячейку уже было помещено значение
     *
     * @param index номер ячейки массива
     * @return значение из ячейки
     * @throws IndexOutOfBoundsException если вышли за диапазон инлексов
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        this.check(index);
        return (E) this.data[index];
    }

    /**
     * возвращаем итератор, позволяющий последовательно получать значения массива
     * также поддерживает удаление из массива только что полученных значений
     *
     * @return итератор
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            /**
             * указатель на текущую позицию
             */
            private int cursor;

            /**
             * указатель на только что выданный эл-т (используется для его удаления)
             */
            private int last = -1;

            /**
             * определяем наличие неполученных эл-тов по указателю
             *
             * @return true, если остались ещё не выданные эл-ты
             */
            @Override
            public boolean hasNext() {
                return this.cursor != SimpleArray.this.size;
            }

            /**
             * выдаём следующий доступный эл-т, его индексом устанавливаем указатель на удаление,
             * передвигаем указатель позиции на следующий эл-т
             *
             * @return следующий эл-т
             * @throws NoSuchElementException если отсутствуют эл-ты к выдаче
             */
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.cursor;
                return SimpleArray.this.get(this.cursor++);
            }

            /**
             * осуществляем удаление эл-та
             * возможно только после получения эл-та чз {@link #next()}, иначе кидаем исключение
             *
             * @throws UnsupportedOperationException получение эл-та не предворяет вызов метода
             */
            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new UnsupportedOperationException();
                }
                SimpleArray.this.delete(this.last);
                this.last = -1;
                this.cursor--;
            }
        };
    }

    /**
     * осуществляем проверку полученного индекса на попадение в допустимый диапазон
     *
     * @param index номер ячейки
     * @throws IndexOutOfBoundsException номер ячейки не попал в область допустимых индексов
     */
    private void check(int index) {
        if (this.size <= index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}
