package ru.job4j.review;


import java.util.Objects;

/**
 * Bank account. Represent something.
 */
public class Account {
    /**
     * account value in euro (or something)
     */
    private double value;
    /**
     * account requisites (use to connect with someone else)
     */
    private String requisites;

    /**
     * Start an account.
     *
     * @param value      value on start
     * @param requisites requisites must not be null
     */
    public Account(double value, String requisites) {
        this.value = value;
        this.requisites = Objects.requireNonNull(requisites, "requisites must not be null");
    }

    /**
     * @return current value in euro (or something)
     */
    public double getValue() {
        return this.value;
    }

    /**
     * @return unique requisites
     */
    public String getRequisites() {
        return this.requisites;
    }

    /**
     * Make a transfer to another user.
     *
     * @param destination destination account
     * @param amount      transfer amount
     * @return {@code true} if success
     */
    protected synchronized boolean transfer(Account destination, double amount) {
        boolean success = false;
        if (destination != null && amount > 0 && amount < this.value) {
            this.value -= amount;
            destination.value += amount;
            success = true;
        }
        return success;
    }

    /**
     * String representation of account
     *
     * @return string line
     */
    @Override
    public String toString() {
        return String.format("%s: %.2f eur", this.requisites, this.value);
    }

    /**
     * Compares the specified object with this account for equality.
     * Checks {@link #requisites} field only.
     *
     * @param o another object
     * @return {@code true} if equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;
        return this.requisites.equals(account.requisites);
    }

    /**
     * Returns the hash code value for this account.
     * Based on {@link #requisites} field only.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return this.requisites.hashCode();
    }
}