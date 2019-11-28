package ru.job4j.vacancy;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Vacancy model class
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-30
 */
public class Vacancy {
    private final String title;
    private final String link;
    private final String description;
    private final LocalDateTime date;

    public Vacancy(String title, String link, String description, LocalDateTime date) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.date = date;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public String link() {
        return link;
    }

    public LocalDateTime date() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vacancy vacancy = (Vacancy) o;
        return title.equals(vacancy.title)
                && link.equals(vacancy.link)
                && description.equals(vacancy.description)
                && date.equals(vacancy.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, description, date);
    }

    @Override
    public String toString() {
        return title + ":" + date + ":" + link + ":  " + description;
    }
}
