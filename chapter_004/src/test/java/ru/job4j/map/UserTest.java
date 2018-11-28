package ru.job4j.map;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

/**
 * Пользователь. Тестирование.
 */
public class UserTest {

    @Test
    public void generalContractObeyingTesting() {
        User first = new User("", 0, null);
        User second = new User("", 0, null);
        User third = new User("", 0, null);

        assertEquals(first, first);

        assertEquals(first, second);
        assertEquals(second, first);

        assertEquals(first, second);
        assertEquals(second, third);
        assertEquals(first, third);

        assertNotEquals(first, null);
    }

    @Test
    public void testEqualsInvocation() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date(System.currentTimeMillis() - 100000));

        User first = new User("", 0, null);
        User second = new User("Second", 0, now);

        assertNotEquals(first, "first");
        assertNotEquals(first, second);

        second = new User(null, 0, null);
        assertNotEquals(first, second);
        assertNotEquals(second, first);

        assertNotEquals(second, new User(null, 0, now));

        second = new User("", 1, null);
        assertNotEquals(first, second);

        second = new User("", 0, now);
        assertNotEquals(first, second);

        second = new User("", 0, Calendar.getInstance());
        assertNotEquals(first, second);

        assertNotEquals(second, new User("", 0, now));
    }

    @Test
    public void testHashCodeInvocation() {
        User first = new User("", 0, null);
        User second = new User("", 0, null);
        assertThat(first.hashCode(), is(second.hashCode()));

        first = new User(null, 0, null);
        assertThat(first.hashCode(), is(0));

        second = new User(null, 0, Calendar.getInstance());
        assertTrue(second.hashCode() != 0);
    }

}
