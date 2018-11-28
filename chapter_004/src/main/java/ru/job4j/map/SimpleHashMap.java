package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Ассоциативный массив на базе хэш-таблицы.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-28
 */
public class SimpleHashMap<K, V> implements Iterable<V> {
    /**
     * Начальная вместимость по-умолчанию (1 << 4 = 16).
     */
    private static final int DEFAULT_CAPACITY = 1 << 4;
    /**
     * Порог заполнения (при его превышении происходит увеличение вместимости хранилища.
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /**
     * Храналище данных (в виде массива).
     */
    private Entry<K, V>[] data;
    /**
     * Количество добавленных элементов.
     */
    private int size;
    /**
     * Количество изменений состояния контейнера (для корректной работы итератора).
     */
    private int modCount;

    /**
     * Конструирует мапу с начальной вместимостью по-умолчанию.
     */
    public SimpleHashMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Конструирует мапу с заданной начальной вместимостью.
     *
     * @param capacity начальная вместимость
     */
    public SimpleHashMap(int capacity) {
        this.init(capacity);
    }

    /**
     * Возвращает количество элементов.
     *
     * @return колво элементов
     */
    public int size() {
        return this.size;
    }

    /**
     * Добавляет в хранилище объект на основе заданной пары ключ-значение.
     * При возникновении коллизии добавление не происходит.
     *
     * @param key   ключ
     * @param value значение
     * @return true, если успешно
     */
    public boolean insert(K key, V value) {
        boolean result = false;
        int index = this.indexFor(this.hash(key), this.data.length);
        if (this.data[index] == null) {
            this.addEntry(key, value, index);
            this.size++;
            this.modCount++;
            result = true;
            this.checkCapacity();
        }

        return result;
    }

    /**
     * Проверяет необходимость расширения хранилища (колво элементов превышает установленные ограничения).
     * В случае необходимости — вызывает метод замены хранилища на новое с удвоенным размером.
     */
    private void checkCapacity() {
        if (this.size >= DEFAULT_LOAD_FACTOR * this.data.length) {
            this.init(this.data.length << 1);
        }
    }

    /**
     * Устанавливает новое храналище заданной вместимости.
     * Осуществляет перенос элементов из старого хранилища (при наличии).
     *
     * @param size вместимость
     */
    @SuppressWarnings("unchecked")
    private void init(int size) {
        Entry<K, V>[] newData = (Entry<K, V>[]) new Entry[size];
        if (this.data != null) {
            for (Entry<K, V> entry : this.data) {
                if (entry != null) {
                    int index = this.indexFor(this.hash(entry.key), size);
                    newData[index] = entry;
                }
            }
        }
        this.data = newData;
    }

    /**
     * Возвращает значение по заданному ключу.
     * При отсутствии — null.
     *
     * @param key ключ
     * @return значение
     */
    public V get(K key) {
        int index = this.indexFor(this.hash(key), this.data.length);
        Entry<K, V> selected = this.data[index];
        return selected != null && (key == null ? selected.key == null : key.equals(selected.key))
                ? selected.value
                : null;
    }

    /**
     * Убирает из хранилища данные по заданному ключу.
     *
     * @param key ключ
     * @return true, если успешно
     */
    public boolean delete(K key) {
        boolean result = false;
        int index = this.indexFor(this.hash(key), this.data.length);
        Entry<K, V> selected = this.data[index];
        if (selected != null && selected.key.equals(key)) {
            this.data[index] = null;
            this.size--;
            this.modCount++;
            result = true;
        }
        return result;
    }

    /**
     * Хеш-функция на основе хешкода ключа.
     * Переносим старшие разряды (>>> 16) вниз и «тасуем» с младшими (через XOR).
     * Тем самым обеспечиваем более равномерное распределение значений хеш-функции.
     *
     * @param key ключ
     * @return хеш
     */
    private int hash(K key) {
        int hash = 0;
        if (key != null) {
            hash = key.hashCode();
            hash ^= hash >>> 16;
        }
        return hash;
    }

    /**
     * Вычисляет индекс ячейки массива (bucket), в кот. будет храниться новый элемент.
     *
     * @param hash   хеш-фукнция нового элемента
     * @param length колво ячеек (bucket)
     * @return индекс в массиве
     */
    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    /**
     * Помещает новый данные  в хранилище, представляя их в виде объекта {@link Entry}
     *
     * @param key   ключ
     * @param value значение
     */
    private void addEntry(K key, V value, int bucketIndex) {
        this.data[bucketIndex] = new Entry<>(key, value);
    }

    /**
     * Возвращает итератор, позволяющий пройти по всем значениям, находящимся в коллекции.
     * @return итератор
     */
    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            private int expectedModCount = SimpleHashMap.this.modCount;
            private int cursor = -1;

            {
                this.shift();
            }

            @Override
            public boolean hasNext() {
                if (SimpleHashMap.this.modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return this.cursor != SimpleHashMap.this.data.length;
            }

            @Override
            public V next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return SimpleHashMap.this.data[this.shift()].value;
            }

            private int shift() {
                int previous = this.cursor;
                do {
                    this.cursor++;
                } while (this.hasNext() && SimpleHashMap.this.data[this.cursor] == null);
                return previous;
            }
        };
    }

    /**
     * Класс, испольщующийся для внутреннего представления данных, переданных в коллекцию на хранение.
     */
    private static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
