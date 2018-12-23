package ru.job4j.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Неблокирующий кэш.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-23
 */
public class SimpleNonBlockingCache {
    /**
     * Хранилище данных.
     */
    private final ConcurrentHashMap<Integer, Base> data = new ConcurrentHashMap<>();

    /**
     * Добавляет новую модель в кэш.
     *
     * @param model добавляемая модель
     * @return <tt>true</tt>, если успешно
     */
    public boolean add(Base model) {
        return data.putIfAbsent(model.id, model) == null;
    }

    /**
     * Заменяет в кэше заданную модель на свежую.
     *
     * @param model заменяемая модель
     * @return <tt>true</tt>, если успешно
     */
    public boolean update(Base model) {
        return data.computeIfPresent(
                model.id,
                (k, v) -> {
                    if (v.version != model.version) {
                        throw new OptimisticException();
                    }
                    model.version++;
                    return model;
                }
        ) != null;
    }

    /**
     * Удаляет заданную модель из кэша.
     *
     * @param model удаляемая модель
     * @return <tt>true</tt>, если успешно
     */
    public boolean delete(Base model) {
        return data.remove(model.id) != null;
    }
}
