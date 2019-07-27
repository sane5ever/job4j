package ru.job4j.tracker;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TrackerSQLTest extends ITrackerTest {
    public TrackerSQLTest() {
        super(new TrackerSQL("tracker.properties"));
    }

    @Test
    public void testClose() throws Exception {
        Item item = new Item("test1", "testDescription", 123L);
        tracker.add(item);
        assertEquals(1, tracker.findAll().size());
        ((TrackerSQL) tracker).close();
        List<Item> all = tracker.findAll();
        assertTrue(all.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void testInitNotExisted() {
        ITracker tracker = new TrackerSQL("not_existed");
        assertFalse(tracker.init());
    }
}