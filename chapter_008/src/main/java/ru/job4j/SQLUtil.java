package ru.job4j;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Util class to handle new connections
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-08-07
 */
public class SQLUtil {
    /**
     * should not instantiate
     */
    private SQLUtil() {
    }

    public static Connection getConnection(String resource) throws SQLException {
        return getConnection(resource, null);
    }

    public static Connection getConnection(String resource, ConnectionProxy proxy) throws SQLException {
        return getConnectionFactory(resource, proxy).getConnection();
    }

    /**
     * Creates and returns new connection factory based on connection properties submitted by the resource file
     *
     * @param resource connection properties file path
     * @return connection factory
     */
    public static ConnectionFactory getConnectionFactory(String resource) {
        return getConnectionFactory(resource, null);
    }

    public static ConnectionFactory getConnectionFactory(String resource, ConnectionProxy proxy) {
        try (InputStream in = SQLUtil.class.getClassLoader().getResourceAsStream(resource)) {
            Properties properties = new Properties();
            properties.load(in);
            Class.forName(properties.getProperty("db.driver-class-name"));
            var url = properties.getProperty("db.url");
            var username = properties.getProperty("db.username");
            var password = properties.getProperty("db.password");
            return () -> getConnection(url, username, password, proxy);
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot load connection properties from " + resource, e);
        }
    }

    private static Connection getConnection(String url, String username, String password, ConnectionProxy proxy) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        return proxy == null ? connection : proxy.proxy(connection);
    }
}

