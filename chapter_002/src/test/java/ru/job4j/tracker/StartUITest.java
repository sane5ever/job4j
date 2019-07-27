package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Используется для проверки поведения пользователя.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-04
 */
public class StartUITest {
    @Test
    public void whenEmpty() {
        ITracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"1", "6", "y"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().isEmpty(), is(true));
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        ITracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "test name", "test desc", "6", "y"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().get(0).getName(), is("test name"));
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        ITracker tracker = new Tracker();
        Item item = new Item("name", "test");
        tracker.add(item);
        Input input = new StubInput(new String[]{"2", item.getId(), "fresh name", "update", "6", "y"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().get(0).getName(), is("fresh name"));
    }

    @Test
    public void whenDeleteOnlyItemThenTrackerHasNoItem() {
        ITracker tracker = new Tracker();
        Item item = new Item("name", "test");
        tracker.add(item);
        Input input = new StubInput(new String[]{"3", item.getId(), "6", "y"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().size(), is(0));
    }

    @Test
    public void whenTryToUpdateNonexistentItemThenTrackerHasNoChange() {
        ITracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"2", "123", "fresh", "update", "6", "y"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().size(), is(0));
    }

    @Test
    public void whenTryToDeleteNonexistentItemThenTrackerHasNoChange() {
        ITracker tracker = new Tracker();
        Item item = new Item("name", "desc");
        tracker.add(item);
        Input input = new StubInput(new String[]{"3", "123", "6", "y"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().size(), is(1));
    }
}

