package ru.job4j.find;

/**
 * Arg enum contains all the parameter definitions which are necessary for file finder work
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-25
 */
enum Arg {
    d("directory", true),
    n("name", true),
    o("output", true),

    m("mask matcher", false),
    f("fullname matcher", false),
    r("regex matcher", false);

    private final String description;

    private final boolean valueRequired;

    Arg(String description, boolean valueRequired) {
        this.description = description;
        this.valueRequired = valueRequired;
    }

    public boolean isValueRequired() {
        return valueRequired;
    }


    @Override
    public String toString() {
        return description;
    }
}
