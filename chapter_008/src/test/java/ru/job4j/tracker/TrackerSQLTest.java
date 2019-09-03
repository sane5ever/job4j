package ru.job4j.tracker;

import org.junit.After;
import org.junit.Test;
import ru.job4j.SQLUtil;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;

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

    private static Connection proxy(Connection connection) throws SQLException {
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

    private static TrackerSQL createTracker() throws SQLException {
        return new TrackerSQL(
                SQLUtil.getConnection("tracker.properties", TrackerSQLTest::proxy));
    }
}