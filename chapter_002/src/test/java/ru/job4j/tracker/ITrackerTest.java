package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public abstract class ITrackerTest {
    protected final ITracker tracker;

    public ITrackerTest(ITracker tracker) {
        this.tracker = tracker;
    }

    @Before
    public void init() {
        tracker.init();
    }

    @After
    public void clear() {
        tracker.clear();
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Item previous = tracker.add(new Item("previous", "previous description", 123L));
        String id = previous.getId();
        Item fresh = new Item("fresh", "fresh description", 321L);
        fresh.setId(id);
        tracker.replace(id, fresh);
        assertThat(tracker.findById(id).getName(), is("fresh"));
    }

    @Test
    public void testReplaceNotFound() {
        Item replaced = new Item("fresh", "fresh description", 321L);
        replaced.setId("123");
        assertFalse(tracker.replace("123", replaced));
    }

    @Test
    public void whenDeleteOnlyItemThenTrackerHasNotIt() {
        Item first = new Item("first", "desc", 100L);
        tracker.add(first);
        tracker.delete(first.getId());
        assertNull(tracker.findById(first.getId()));
    }

    @Test
    public void whenDeleteLastOf3ItemsThenTrackerHas2Items() {
        Item first = new Item("first", "desc", 1L);
        Item second = new Item("second", "desc", 2L);
        Item third = new Item("third", "desc", 3L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.delete(third.getId());
        assertThat(tracker.findAll(), is(
                Arrays.asList(first, second)
                )
        );
    }

    @Test
    public void whenDeleteSecondOf3ItemsThenOtherTwoRemain() {
        Item first = new Item("one", "desc", 1L);
        Item second = new Item("two", "desc", 2L);
        Item third = new Item("three", "desc", 3L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        tracker.delete(second.getId());
        assertThat(tracker.findAll(), is(
                Arrays.asList(first, third)
                )
        );
    }

    @Test
    public void whenDeleteFirstOf2ItemsAndAddAnotherOneThenThatOnesRemain() {
        Item first = new Item("first", "desc", 1L);
        Item second = new Item("second", "desc", 2L);
        tracker.add(first);
        tracker.add(second);
        tracker.delete(first.getId());
        Item third = new Item("third", "desc", 3L);
        tracker.add(third);
        assertThat(tracker.findAll(), is(
                Arrays.asList(second, third)
                )
        );

    }

    @Test
    public void whenTwoItemsOutOfThreeHaveKeyNameThenArrayWithEm() {
        Item first = new Item("name", "desc", 1L);
        Item second = new Item("anotherName", "desc", 2L);
        Item third = new Item("name", "desc", 3L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        assertThat(tracker.findByName("name"), is(
                Arrays.asList(first, third)
                )
        );
    }

    @Test
    public void whenNoItemHasKeyNameThenEmptyArray() {
        Item first = new Item("first", "desc", 1L);
        Item second = new Item("second", "desc", 2L);
        Item third = new Item("third", "desc", 3L);
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        assertThat(tracker.findByName("name"), is(
                Collections.emptyList()
                )
        );
    }
}