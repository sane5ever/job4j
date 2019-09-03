package ru.job4j.tracker;

import org.junit.After;
import org.junit.Test;
import ru.job4j.SQLUtil;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TrackerSQLTest extends ITrackerTest {
    public TrackerSQLTest() throws SQLException {
        super(createTracker());
    }

    @After
    public void close() throws SQLException {
        ((TrackerSQL) tracker).close();
    }

    @Test
    public void replaceNotExisted() throws SQLException {
        try (TrackerSQL tracker = createTracker()) {
            Item updated = new Item("updated", "updated_description");
            boolean result = tracker.replace("123", updated);
            assertFalse(result);
        }
    }

    @Test
    public void deleteNotExisted() throws SQLException {
        try (TrackerSQL tracker = createTracker()) {
            boolean result = tracker.delete("123");
            assertFalse(result);
        }
    }

    @Test
    public void addWhenErrorOccurs() throws SQLException {
        try (TrackerSQL tracker = createErrorOccurringTracker()) {
            Item added = tracker.add(new Item("name", "description"));
            assertNull(added);
        }
    }

    @Test
    public void replaceWhenErrorOccurs() throws SQLException {
        try (TrackerSQL tracker = createErrorOccurringTracker()) {
            boolean wasReplaced = tracker.replace("id", new Item("name", "description"));
            assertFalse(wasReplaced);
        }
    }

    @Test
    public void deleteWhenErrorOccurs() throws SQLException {
        try (TrackerSQL tracker = createErrorOccurringTracker()) {
            boolean wasDeleted = tracker.delete("id");
            assertFalse(wasDeleted);
        }
    }

    @Test
    public void findByIdWhenErrorOccurs() throws SQLException {
        try (TrackerSQL tracker = createErrorOccurringTracker()) {
            Item found = tracker.findById("id");
            assertNull(found);
        }
    }

    @Test(expected = NullPointerException.class)
    public void closeWhenNullConnection() throws SQLException {
        try (TrackerSQL tracker = new TrackerSQL(null)) {
            tracker.findAll();
        }
    }

    private static TrackerSQL createTracker() throws SQLException {
        return new TrackerSQL(
                SQLUtil.getConnection("tracker.properties", TrackerSQLTest::proxy));
    }

    private static TrackerSQL createErrorOccurringTracker() throws SQLException {
        return new TrackerSQL(
                SQLUtil.getConnection("tracker.properties", TrackerSQLTest::erroredProxy));
    }

    public static Connection proxy(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        return (Connection) Proxy.newProxyInstance(
                SQLUtil.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    Object result = null;
                    if ("close".equals(method.getName())) {
                        connection.rollback();
                        connection.close();
                    } else {
                        result = method.invoke(connection, args);
                    }
                    return result;
                }
        );
    }

    public static Connection erroredProxy(Connection connection) {
        return (Connection) Proxy.newProxyInstance(
                SQLUtil.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if ("close".equals(method.getName())) {
                        return method.invoke(connection, args);
                    } else {
                        throw new SQLException("mock exception");
                    }
                }
        );
    }
}