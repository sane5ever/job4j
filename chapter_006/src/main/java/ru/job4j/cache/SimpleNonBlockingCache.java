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
     * Добавляет новую модель в кэш при отсутствии.
     *
     * @param model добавляемая модель
     * @return текущая версия заданной модели (или {@code null}, если отсутствует)
     */
    public Base add(Base model) {
        return data.putIfAbsent(model.getId(), model);
    }

    /**
     * Обновляет модель в кэше, используя данные из переданной.
     *
     * @param model заменяемая модель
     * @return обновлённая модель (или {@code null}, если не обновлено)
     * @throws OptimisticException если версии моделей не совпадают
     */
    public Base update(Base model) {
        return data.computeIfPresent(
                model.getId(),
                (k, v) -> {
                    if (v.getVersion() != model.getVersion()) {
                        throw new OptimisticException();
                    }
                    v.setInfo(model.getInfo());
                    v.updateVersion();
                    return v;
                }
        );
    }

    /**
     * Удаляет заданную модель из кэша.
     *
     * @param model удаляемая модель
     * @return удалённая модель (или {@code null}, если не найдена)
     */
    public Base delete(Base model) {
        return data.remove(model.getId());
    }
}
