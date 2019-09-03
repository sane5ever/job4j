package ru.job4j.xml;

import one.util.streamex.IntStreamEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.SQLUtil.ResultSetHandler;
import ru.job4j.SQLUtil.SQLExecutor;
import ru.job4j.xml.schema.EntryType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Proceeds the logic of numbers storing to and getting from DB
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-26
 */
public class SQLStore {
    private static final Logger LOG = LoggerFactory.getLogger(SQLStore.class);

    private final Connection connection;

    public SQLStore(Connection connection) {
        this.connection = connection;
    }

    /**
     * Generates and add in DB number sequence from 1 to size, defined on the method call, in transaction
     *
     * @param size last number value
     */
    public void generate(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("amount cannot be less than 0");
        }
        int result = transactionalExecute(connection -> generateNumbers(size));
        LOG.info("Successfully generated and added in DB {}-number sequence", result);
    }

    /**
     * Load from DB all the entries as EntryType list
     *
     * @return entries
     */
    public List<EntryType> load() {
        return executeQuery(this::getEntries, "SELECT * FROM entry");
    }

    /**
     * Grabs entries from defined result set
     *
     * @param resultSet executed query's result set
     * @return entries
     * @throws SQLException if sql connection error occurs
     */
    private List<EntryType> getEntries(ResultSet resultSet) throws SQLException {
        List<EntryType> result = new ArrayList<>(resultSet.getFetchSize());
        while (resultSet.next()) {
            EntryType entry = new EntryType(resultSet.getInt("field"));
            result.add(entry);
        }
        return result;
    }

    /**
     * Creates or clears entry table in DB, then generates and inserts in DB defined amount of numbers
     *
     * @param size number amount
     * @return number amount to be added
     * @throws SQLException if sql connection error occurs
     */
    private int generateNumbers(int size) throws SQLException {
        createTableIfNotExist();
        clearTableValues();
        return insertNumbers(size);
    }

    /**
     * Inserts in DB defined amount of numbers from 1 to N
     *
     * @param size number amount
     * @return number amount to be added
     * @throws SQLException if sql connection error occurs
     */
    private int insertNumbers(int size) throws SQLException {
        LOG.info("Start generating and adding in DB {} numbers.", size);
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO entry (field) VALUES (?) ")) {
            for (int i = 1; i <= size; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.addBatch();
            }
            int[] result = preparedStatement.executeBatch();
            int counts = IntStreamEx.of(result).sum();
            LOG.info("Been added {} numbers in DB.", counts);
            return counts;
        }
    }

    /**
     * Creates 'entry' table in DB if necessary
     *
     * @throws SQLException if sql connection error occurs
     */
    private void createTableIfNotExist() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS entry ("
                            + "field INTEGER"
                            + ");");
            LOG.info("table 'entry' is created");
        }
    }

    /**
     * Clears 'entry' table values in DB if necessary
     *
     * @throws SQLException if sql connection error occurs
     */
    private void clearTableValues() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM entry LIMIT 1")) {
            if (resultSet.next()) {
                // noinspection SqlWithoutWhere: SQLite not supported TRUNCATE
                statement.execute("DELETE FROM entry");
                LOG.info("Already presented values in table 'entry' were cleared");
            } else {
                LOG.info("Table 'entry' is already empty. Clearing has been denied.");
            }
        }
    }

    /**
     * Provides transactional execution of sql-queries defined given sql executor object
     *
     * @param executor sql executor
     * @return result of sql executor work
     */
    public <T> T transactionalExecute(SQLExecutor<T> executor) {
        T result;
        try {
            connection.setAutoCommit(false);
            result = executor.execute(connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new IllegalStateException("cannot rollback db", ex);
            }
            throw new IllegalStateException("cannot execute transactional query: " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * Executes sql query, then submits result set to specified result set handler and returns its work result
     *
     * @param handler  object to handle executed query's result set
     * @param sqlQuery sql query needed to be execute
     * @return handler work result
     */
    public <T> T executeQuery(ResultSetHandler<T> handler, String sqlQuery) {
        T result;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)
        ) {
            result = handler.execute(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException("cannot execute query: " + sqlQuery, e);
        }
        return result;
    }
}