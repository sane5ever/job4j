package ru.job4j.map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Ассоциативный массив на базе хэш-таблицы. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-28
 */
public class SimpleHashMapTest {

    private SimpleHashMap<Integer, User> users = new SimpleHashMap<>();
    private Calendar currentTime = Calendar.getInstance();

    @Before
    public void init() {
        this.users.insert(0, new User("Mickey", 10, this.currentTime));
        this.users.insert(1, new User("Garfield", 100, this.currentTime));
        this.users.insert(2, new User("Jerry", 1000, this.currentTime));
    }

    @Test
    public void whenInsertedThenGetReturnsCorrectUser() {
        assertThat(this.users.get(0).getName(), is("Mickey"));
        assertThat(this.users.get(1).getChildren(), is(100));
        assertEquals(this.users.get(2).getBirthday().getTimeInMillis(), this.currentTime.getTimeInMillis());
    }

    @Test
    public void whenNotInsertedThenGetReturnsNull() {
        assertNull(this.users.get(-1));
        assertNull(this.users.get(5));
    }

    @Test
    public void whenInsertInvocationWithDuplicateShouldReturnFalseAndStaySameSize() {
        int expectedSize = this.users.size();
        assertFalse(this.users.insert(0, new User("Clark", 0, this.currentTime)));
        assertThat(this.users.size(), is(expectedSize));
    }

    @Test
    public void whenDeleteInvocationWithNotInsertedItemShouldReturnFalseAndStaySameSize() {
        int expectedSize = this.users.size();
        assertFalse(this.users.delete(5));
        assertThat(this.users.size(), is(expectedSize));
    }

    @Test
    public void whenNullKeyInvocationResultsShouldBeCorrect() {
        assertNull(this.users.get(null));
        this.users = new SimpleHashMap<>();
        assertTrue(this.users.insert(null, new User("Clark", 0, this.currentTime)));
        assertThat(this.users.size(), is(1));
        assertEquals(this.users.get(null).getName(), "Clark");

    }


    @Test
    public void whenInsertThenDeletedThenGetReturnsTrueThenNull() {
        assertTrue(this.users.insert(4, new User("Clark", 0, this.currentTime)));
        assertTrue(this.users.delete(4));
        assertNull(this.users.get(4));
    }

    @Test
    public void whenDeleteNotInsertedThenReturnsFalse() {
        assertFalse(this.users.delete(-1));
    }

    @Test
    public void iteratorHasNextBeforeAndAfterInvocation() {
        Iterator<User> iterator = this.users.iterator();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
        iterator.next();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nextInvocationWhenHasNotNextShouldThrowsNSEE() {
        Iterator<User> iterator = this.users.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        this.thrown.expect(NoSuchElementException.class);
        iterator.next();
    }

    @Test
    public void nextInvocationAfterChangingInnerStateShouldThrowsCME() {
        Iterator<User> iterator = this.users.iterator();
        this.users.delete(0);
        this.thrown.expect(ConcurrentModificationException.class);
        iterator.next();
    }

    @Test
    public void whenStartCapacityLessInsertedAmountShouldResizedAndAddAll() {
        this.users = new SimpleHashMap<>(2);
        this.init();
        assertThat(this.users.size(), is(3));
    }

    @Test
    public void when() {
        System.out.println("_____");
        SimpleHashMap<User, Object> map = new SimpleHashMap<>();
        map.insert(users.get(0), new Object());
        map.insert(users.get(1), new Object());
        map.insert(users.get(2), new Object());
    }


}
