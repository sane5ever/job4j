package ru.job4j.transactions;

/**
 * Банковский счёт.
 * Уникальность объекта класса определяется только по полю {@code requisites}
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-19
 */
public class Account {
    private double value;
    private String requisites;

    public Account(double value, String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    public double getValue() {
        return value;
    }

    public String getRequisites() {
        return requisites;
    }

    void increaseValue(double amount) {
        this.value += amount;
    }

    void decreaseValue(double amount) {
        this.value -= amount;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (this == o) {
            result = true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Account other = (Account) o;
            result = this.requisites != null ? this.requisites.equals(other.requisites) : other.requisites == null;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.requisites != null ? this.requisites.hashCode() : 0;
    }
}
