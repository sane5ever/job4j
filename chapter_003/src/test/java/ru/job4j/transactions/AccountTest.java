package ru.job4j.transactions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Банковский счёт. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-20
 */
public class AccountTest {
    @Test
    public void whenBothFieldsEqualThenEqual() {
        Account one = new Account(50d, "163");
        Account another = new Account(50d, "163");
        assertThat(one.hashCode(), is(another.hashCode()));
        assertThat(one.equals(another), is(true));
    }

    @Test
    public void whenOnlyValuesEqualThenNotEqual() {
        Account one = new Account(50d, "163");
        Account another = new Account(50d, "165");
        assertThat(one.hashCode(), not(another.hashCode()));
        assertThat(one.equals(another), is(false));
    }

    @Test
    public void whenOnlyRequisitesEqualThenEqual() {
        Account one = new Account(50d, "163");
        Account another = new Account(100d, "163");
        assertThat(one.hashCode(), is(another.hashCode()));
        assertThat(one.equals(another), is(true));
    }

    @Test
    public void whenBothFieldsNotEqualThenNotEqual() {
        Account one = new Account(50d, "163");
        Account another = new Account(100d, "165");
        assertThat(one.hashCode(), not(another.hashCode()));
        assertThat(one.equals(another), is(false));
    }
}