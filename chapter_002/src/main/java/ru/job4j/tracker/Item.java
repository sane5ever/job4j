package ru.job4j.tracker;

import com.google.common.base.Objects;

import java.util.Date;

/**
 * Заявка.
 */
public class Item {
    private String id;

    private String name;

    private String description;

    private long create;

    public Item(String name, String description, long create) {
        this.name = name;
        this.description = description;
        this.create = create;
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public long getCreate() {
        return this.create;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreate(long create) {
        this.create = create;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return create == item.create
                && Objects.equal(id, item.id)
                && Objects.equal(name, item.name)
                && Objects.equal(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, create);
    }

    @Override
    public String toString() {
        return String.format(
                "%s|%s|%s|%td-%<tm-%<ty",
                this.getId(),
                this.getName(),
                this.getDescription(),
                new Date(this.getCreate())
        );
    }
}