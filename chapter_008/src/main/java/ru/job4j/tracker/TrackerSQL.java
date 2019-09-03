package ru.job4j.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Item storage based on DB connection
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-27
 */
public class TrackerSQL implements ITracker, AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(TrackerSQL.class);

    private final Connection connection;

    public TrackerSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Item add(Item item) {
        Item result = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO items (name, description) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    item.setId(set.getString("id"));
                    item.setCreate(set.getDate("created").getTime());
                    result = item;
                }
            }
        } catch (SQLException e) {
            LOG.error("cannot insert item=" + item, e);
        }
        return result;
    }

    @Override
    public boolean replace(String id, Item item) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE items SET name=?, description=? WHERE id=?")) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setInt(3, Integer.valueOf(id));
            int rowCount = statement.executeUpdate();
            return rowCount != 0;
        } catch (SQLException e) {
            LOG.error("cannot update with id=" + id, e);
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE from items WHERE id=?")) {
            statement.setInt(1, Integer.valueOf(id));
            int rowCount = statement.executeUpdate();
            return rowCount != 0;
        } catch (SQLException e) {
            LOG.error("cannot delete with id=" + id, e);
            return false;
        }
    }

    @Override
    public List<Item> findAll() {
        //language=PostgreSQL
        return findItems("SELECT * FROM items");
    }

    @Override
    public List<Item> findByName(String key) {
        //language=PostgreSQL
        return findItems("SELECT * FROM items i WHERE i.name=?", st -> st.setString(1, key));
    }

    @Override
    public Item findById(String id) {
        //language=PostgreSQL
        List<Item> items = findItems(
                "SELECT * FROM items i WHERE i.id=?", st -> st.setInt(1, Integer.valueOf(id)));
        return items.isEmpty() ? null : items.get(0);
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Creates prepared statement and sets its params via suggested setters,
     * then executes prepared statement and forms an output based on db response
     *
     * @param sql     sql query
     * @param setters param setters required on sql query
     * @return list ot items conforming to the request
     */
    private List<Item> findItems(String sql, PreparedStatementSetter... setters) {
        List<Item> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (PreparedStatementSetter setter : setters) {
                setter.set(statement);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Item item = parseItem(resultSet);
                    result.add(item);
                }
            }
        } catch (SQLException e) {
            LOG.error("cannot find items", e);
        }
        return result;
    }

    /**
     * Parses item from returned result set
     *
     * @param resultSet set of data returned from database
     * @return item object with filled in fields
     * @throws SQLException if a database access error occurs or this method is
     *                      called on a closed result set
     */
    private Item parseItem(ResultSet resultSet) throws SQLException {
        Item item = new Item(
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("created").getTime()
        );
        item.setId(resultSet.getString("id"));
        return item;
    }

    /**
     * PreparedStatement setter (consumer) that can throw SQLException
     */
    @FunctionalInterface
    private interface PreparedStatementSetter {

        void set(PreparedStatement statement) throws SQLException;
    }
}