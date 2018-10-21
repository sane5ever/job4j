package ru.job4j.transactions;

/**
 * Пользователь.
 * Уникальность объекта класса определяется только по полю {@code passport}
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-19
 */
public class User {
    private String name;
    private String passport;


    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    public String getPassport() {
        return passport;
    }

    public String getName() {
        return name;
    }

    Example example = new Example("") {
        void write() {
            System.out.println(super.name);
        }
    };

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (this == o) {
            result = true;
        } else if (o != null && this.getClass() == o.getClass()) {
            User user = (User) o;
            result = this.passport != null ? this.passport.equals(user.passport) : user.passport == null;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.passport != null ? this.passport.hashCode() : 0;
    }
}

abstract class Example {
    String name;

    public Example(String name) {
        this.name = name;
    }
}
