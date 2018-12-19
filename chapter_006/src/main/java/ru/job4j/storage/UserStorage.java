package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.stream.Stream;

/**
 * Хранилище пользователей.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-17
 */
@ThreadSafe
public class UserStorage {
    /**
     * Максимальная вместимость хранилища по-умолчанию.
     */
    private final static int DEFAULT_CAPACITY = 100;

    /**
     * Лок для запирания меняющих внутреннее состояние хранилище операций.
     */
    private final Object lock = new Object();

    /**
     * Массив, используемый для хранения пользователей.
     */
    @GuardedBy("lock")
    private final User[] data;

    /**
     * Количество пользователей, содержащихся в хранилище.
     */
    @GuardedBy("lock")
    private int size = 0;

    /**
     * Создаёт пустое хранилище с вместимостью по-умолчанию.
     */
    public UserStorage() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Создаёт пустое хранилище с заданной вместимостью.
     *
     * @param capacity вместимость
     */
    public UserStorage(int capacity) {
        this.data = new User[capacity];
    }

    /**
     * Добавляет нового пользователя в хранилище, если хранилище не переполнено.
     *
     * @param user пользователь
     * @return <tt>true</tt>, если успешно
     */
    public boolean add(User user) {
        synchronized (lock) {
            boolean result = size < data.length;
            if (result) {
                this.data[size++] = user;
            }
            return result;
        }
    }

    /**
     * Добавляет заданного пользователя, заменяя существующего с совпадающим <tt>id</tt>.
     *
     * @param user пользователь
     * @return <tt>true</tt>, если успешно
     */
    public boolean update(User user) {
        synchronized (lock) {
            int index = this.findById(user.id);
            boolean result = index != -1;
            if (result) {
                data[index] = user;
            }
            return result;
        }
    }

    /**
     * Удаляет заданного пользователя из хранилища.
     *
     * @param user пользователь
     * @return <tt>true</tt>, если успешно
     */
    public boolean delete(User user) {
        synchronized (lock) {
            int index = findById(user.id);
            boolean result = index != -1;
            if (result) {
                System.arraycopy(
                        data,
                        index + 1,
                        data,
                        index,
                        --size - index
                );
            }
            return result;
        }
    }

    /**
     * Осуществляет перевод средств со счёта одного пользователя на счёт другого.
     * Проверяет, что отправитель и получатель существуют в хранилище, а так же,
     * что на счету отправителя достаточно средств.
     *
     * @param fromId id отправителя средств
     * @param toId   id получателя средств
     * @param amount размер перевода
     * @return <tt>true</tt>, если успешно
     */
    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (lock) {
            boolean result = false;
            int from = findById(fromId);
            User src = from != -1 ? data[from] : null;
            if (src != null && src.amount >= amount) {
                int to = findById(toId);
                User dest = to != -1 ? data[to] : null;
                result = swap(src, dest, amount);
            }
            return result;
        }
    }

    /**
     * Непосредственно переводит средства от заданного отправителя к заданному получателю.
     *
     * @param from   отправитель средств
     * @param dest   получатель средств
     * @param amount размер перевода
     * @return <tt>true</tt>, если успешно
     */
    private boolean swap(User from, User dest, int amount) {
        boolean result = dest != null;
        if (result) {
            from.amount -= amount;
            dest.amount += amount;
        }
        return result;
    }

    /**
     * Возвращает индекс ячейки, в кот. хранится пользователь с заданным id.
     * В случае отсутствия возвращает -1.
     *
     * @param id id пользователя
     * @return индекс массива
     */
    private int findById(int id) {
        int result;
        synchronized (lock) {
            result = Stream.iterate(0, i -> i < size, i -> i + 1)
                    .filter(i -> data[i].id == id)
                    .findAny()
                    .orElse(-1);
        }
        return result;
    }

    /**
     * Пользователь.
     */
    static class User {
        private int id;
        int amount;

        public User(int id, int amount) {
            this.id = id;
            this.amount = amount;
        }
    }
}
