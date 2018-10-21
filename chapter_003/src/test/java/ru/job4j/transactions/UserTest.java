package ru.job4j.transactions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Пользователь. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-20
 */
public class UserTest {
    @Test
    public void whenBothFieldsEqualThenEqual() {
        User one = new User("Laurence", "163");
        User another = new User("Laurence", "163");
        assertThat(one.hashCode(), is(another.hashCode()));
        assertThat(one.equals(another), is(true));
    }

    @Test
    public void whenOnlyNamesEqualThenNotEqual() {
        User one = new User("Laurence", "163");
        User another = new User("Laurence", "165");
        assertThat(one.hashCode(), not(another.hashCode()));
        assertThat(one.equals(another), is(false));
    }

    @Test
    public void whenOnlyPassportsEqualThenEqual() {
        User one = new User("Laurence", "163");
        User another = new User("Kevin", "163");
        assertThat(one.hashCode(), is(another.hashCode()));
        assertThat(one.equals(another), is(true));
    }

    @Test
    public void whenBothFieldsNotEqualThenNotEqual() {
        User one = new User("Laurence", "163");
        User another = new User("Kevin", "165");
        assertThat(one.hashCode(), not(another.hashCode()));
        assertThat(one.equals(another), is(false));
    }
}
