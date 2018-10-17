package ru.job4j.tracker;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TrackerTest {
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        Tracker tracker = new Tracker();
        Item previous = tracker.add(new Item("previous", "previous description", 123L));
        String id = previous.getId();
        Item fresh = new Item("fresh", "fresh description", 321L);
        fresh.setId(id);
        tracker.replace(id, fresh);
        assertThat(tracker.findById(id).getName(), is("fresh"));
    }

    @Test
    public void whenDeleteOnlyItemThenTrackerHasNotIt() {
        Tracker tracker = new Tracker();
        Item first = new Item("first", "desc", 100L);
        tracker.add(first);
        tracker.delete(first.getId());
        assertNull(tracker.findById(first.getId()));
    }

    @Test
    public void whenDeleteLastOf3ItemsThenTrackerHas2Items() {
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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
        Tracker tracker = new Tracker();
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