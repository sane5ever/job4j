package ru.job4j.vacancy;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import ru.job4j.ConnectionFactory;
import ru.job4j.SQLUtil;

import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.io.Resources.getResource;
import static java.time.LocalDateTime.of;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static ru.job4j.vacancy.util.Util.now;

public class TestData {
    public static final Vacancy VACANCY1 = new Vacancy("first", "link_1", "description_1", of(2019, JUNE, 30, 15, 17));
    public static final Vacancy VACANCY2 = new Vacancy("second", "link_2", "description_2", of(2019, JUNE, 28, 10, 13));
    public static final Vacancy VACANCY3 = new Vacancy("third", "link_3", "description_3", of(2019, JUNE, 26, 21, 48));
    public static final Vacancy NEW_VACANCY = new Vacancy("new", "new_link", "new_description", of(2019, JULY, 2, 18, 0));

    public static final List<Vacancy> VACANCIES = List.of(VACANCY1, VACANCY2, VACANCY3);

    public static final List<Vacancy> UPDATED_VACANCIES = List.of(NEW_VACANCY, VACANCY1, VACANCY2, VACANCY3);

    public static final LocalDateTime LIMIT_DATE = LocalDateTime.of(2019, JULY, 25, 16, 0, 0);

    public static Vacancy getDuplicate(Vacancy vacancy) {
        return new Vacancy(vacancy.title(), "new_" + vacancy.link(), "new_" + vacancy.description(), now());
    }

    public static List<Vacancy> getAll(ConnectionFactory connectionFactory) {
        List<Vacancy> result = new ArrayList<>();
        try (var connection = connectionFactory.getConnection();
             var selectStatement = connection.createStatement()
        ) {
            ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM  vacancy ORDER BY date DESC");
            while (resultSet.next()) {
                result.add(parseVacancy(resultSet));
            }

        } catch (SQLException e) {
            throw new IllegalStateException("cannot connect to db", e);
        }
        return result;
    }

    private static Vacancy parseVacancy(ResultSet resultSet) throws SQLException {
        return new Vacancy(
                resultSet.getString("title"),
                resultSet.getString("link"),
                resultSet.getString("description"),
                resultSet.getTimestamp("date").toLocalDateTime()
        );
    }

    public static ConnectionHolder connect() throws SQLException {
        return new ConnectionHolder("job.properties");
    }

    public static String asFullPathString(String resourceName) throws URISyntaxException {
        return Paths.get(getResource(resourceName).toURI()).toString();
    }

    static class ConnectionHolder implements ConnectionFactory, AutoCloseable {
        private Connection connection;

        public ConnectionHolder(String resource) throws SQLException {
            this.connection = SQLUtil.getConnection(resource);
        }

        @Override
        public Connection getConnection() throws SQLException {
            return rollbackProxy(connection);
        }

        @Override
        public void close() throws SQLException {
            if (connection != null) {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                }
                connection.close();
            }
        }

        public static Connection rollbackProxy(Connection connection) throws SQLException {
            connection.setAutoCommit(false);
            return (Connection) Proxy.newProxyInstance(
                    ConnectionHolder.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        Object result = null;
                        // do not commit and do not close (handle it on ConnectionHolder.close() call)
                        if (!"commit".equals(method.getName()) && !"close".equals(method.getName())) {
                            result = method.invoke(connection, args);
                        }
                        return result;
                    }
            );
        }
    }

    static class JobExample implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            // do nothing
        }
    }
}