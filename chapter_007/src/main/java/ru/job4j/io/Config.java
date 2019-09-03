package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Represents configs loading from file path
 */
public class Config {
    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    /**
     * Loads properties from file path to in-memory storage
     */
    public void load() {
        values.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            LOG.info("load properties from path {}", path);
            reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && line.charAt(0) != '#' && line.indexOf('=') != -1)
                    .forEach(line -> {
                                var idx = line.indexOf('=');
                                values.put(line.substring(0, idx), line.substring(idx + 1));
                            }
                    );
        } catch (IOException e) {
            LOG.error("couldn't read file", e);
            throw new IllegalStateException("Couldn't read file", e);
        }
    }

    /**
     * Gets value of property with the given key from in-memory storage
     *
     * @param key property key
     * @return property value (or {@code null} if not presented
     */
    public String value(String key) {
        return values.get(key);
    }

    /**
     * @return string representation of file with properties
     */
    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.lines().forEach(out::add);
        } catch (IOException e) {
            LOG.error("couldn't read file", e);
            throw new IllegalStateException("Couldn't read file", e);
        }
        return out.toString();
    }
}
