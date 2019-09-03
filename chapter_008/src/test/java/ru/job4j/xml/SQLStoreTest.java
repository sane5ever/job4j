package ru.job4j.xml;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.job4j.SQLUtil;
import ru.job4j.xml.schema.EntryType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SQLStoreTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGenerateAndLoad() throws Exception {
        try (Connection connection = SQLUtil.getConnection("magnit.properties")) {
            SQLStore store = new SQLStore(connection);
            store.generate(100);
            List<EntryType> entries = store.load();
            assertEquals(100, entries.size());
            assertEquals(1, entries.get(0).getField());
            assertEquals(100, entries.get(99).getField());
        }
    }

    @Test
    public void testGenerateWhenNotEmpty() throws Exception {
        try (Connection connection = SQLUtil.getConnection("magnit.properties")) {
            SQLStore store = new SQLStore(connection);
            store.generate(10);
            store.generate(20);
            List<EntryType> entries = store.load();
            assertEquals(20, entries.size());
            assertEquals(20, entries.get(entries.size() - 1).getField());
        }
    }

    @Test
    public void testGenerateNegativeAmount() throws Exception {
        try (Connection connection = SQLUtil.getConnection("magnit.properties")) {
            SQLStore store = new SQLStore(connection);
            thrown.expect(IllegalArgumentException.class);
            thrown.expectMessage("amount cannot be less than 0");
            store.generate(-5);
        }
    }

    @Test
    public void testTransactionalExecute() throws Exception {
        try (Connection connection = SQLUtil.getConnection("magnit.properties")) {
            SQLStore store = new SQLStore(connection);
            thrown.expect(IllegalStateException.class);
            thrown.expectMessage("cannot execute transactional query: test");
            store.transactionalExecute(conn -> {
                throw new SQLException("test");
            });
        }
    }

    @Test
    public void testTransactionalExecuteCannotRollback() throws Exception {
        Connection connection = SQLUtil.getConnection("magnit.properties");
        SQLStore store = new SQLStore(connection);
        connection.close();
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("cannot rollback db");
        store.generate(1);
    }

    @Test
    public void testExecuteQuery() throws Exception {
        try (Connection connection = SQLUtil.getConnection("magnit.properties")) {
            SQLStore store = new SQLStore(connection);
            thrown.expect(IllegalStateException.class);
            thrown.expectMessage("cannot execute query: empty");
            store.executeQuery(resultSet -> {
                throw new SQLException("test");
            }, "empty");
        }
    }
}
