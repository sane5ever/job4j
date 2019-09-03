package ru.job4j.archiver;

/**
 * Arg enum contains all the parameter definitions which are necessary for archiver work
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-06-27
 */
enum Arg {
    d("directory", true),
    e("exclude", false),
    o("output", true);

    private final String description;

    private final boolean required;

    Arg(String description, boolean required) {
        this.description = description;
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public String toString() {
        return description;
    }
}
