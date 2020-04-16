package ru.job4j.statistics.model;

/**
 * Контейнер для результатов статистики.
 */
public class Info {
    private int added;
    private int changed;
    private int deleted;

    public int getAdded() {
        return added;
    }

    public int getChanged() {
        return changed;
    }

    public int getDeleted() {
        return deleted;
    }

    public void incrementAdded() {
        added++;
    }

    public void incrementChanged() {
        changed++;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
