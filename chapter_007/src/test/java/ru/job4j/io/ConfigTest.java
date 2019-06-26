package ru.job4j.io;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLoad() throws IOException {
        String path = createAndFillTestFile(
                new StringJoiner("\n")
                        .add("## PostgreSQL")
                        .add("")
                        .add("hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect")
                        .add("hibernate.connection.url=jdbc:postgresql://127.0.0.1:5432/trackstudio")
                        .add("hibernate.connection.driver_class=org.postgresql.Driver")
                        .add("hibernate.connection.username=postgres")
                        .add("hibernate.connection.password=password")
                        .toString()
        );
        Config config = new Config(path);
        config.load();
        assertEquals(
                "password",
                config.value("hibernate.connection.password")
        );
    }

    @Test
    public void testLoadIncorrectProp() throws IOException {
        String path = createAndFillTestFile(
                new StringJoiner("\n")
                        .add("## PostgreSQL")
                        .add("")
                        .add("hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect")
                        .add("hibernate.connection.url=jdbc:postgresql://127.0.0.1:5432/trackstudio")
                        .add("hibernate.connection.driver_class=org.postgresql.Driver")
                        .add("hibernate.connection.usernamepostgres")
                        .add("#hibernate.connection.password=password")
                        .toString()
        );
        Config config = new Config(path);
        config.load();
        assertNull(
                config.value("hibernate.connection.password")
        );
        assertNull(
                config.value("hibernate.connection.usernamepostgres")
        );
    }

    @Test
    public void testLoadBlank() throws IOException {
        String path = createAndFillTestFile("");
        Config config = new Config(path);
        config.load();
        assertNull(config.value("password"));
    }

    @Test
    public void testLoadNotExistedFile() throws Exception {
        Config config = new Config("not_existed");
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Couldn't read file");
        config.load();
    }

    @Test
    public void testToString() throws IOException {
        String lines = new StringJoiner(System.lineSeparator())
                .add("hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect")
                .add("hibernate.connection.url=jdbc:postgresql://127.0.0.1:5432/trackstudio")
                .toString();
        String path = createAndFillTestFile(lines);
        Config config = new Config(path);
        String actual = config.toString();
        assertEquals(lines, actual);
    }

    @Test
    public void testToStringNotExisted() throws IOException {
        Config config = new Config("not_existed");
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Couldn't read file");
        String lines = config.toString();
    }

    private String createAndFillTestFile(String lines) throws IOException {
        Path tmp = Files.createTempFile(null, null);
        try (PrintWriter writer = new PrintWriter(Files.newOutputStream(tmp))) {
            writer.print(lines);
        }
        return tmp.toString();
    }
}