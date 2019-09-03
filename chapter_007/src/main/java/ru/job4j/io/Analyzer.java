package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Server availability analyzer
 */
public class Analyzer {
    private static final Logger LOG = LoggerFactory.getLogger(Analyzer.class);

    /**
     * Analyzes presented server logs and writes the file with time intervals when server was down to the target
     *
     * @param source source file path
     * @param target target path file
     */
    public void unavailable(String source, String target) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
             PrintWriter writer = new PrintWriter(new FileOutputStream(target))) {
            boolean unavailable = false;
            for (String line = reader.readLine(), startTime = getTime(line);
                 line != null;
                 line = reader.readLine()) {
                if (unavailable && isAvailable(line)) {
                    writer.println(startTime + ';' + getTime(line));
                    unavailable = false;
                } else if (!unavailable && !isAvailable(line)) {
                    startTime = getTime(line);
                    unavailable = true;
                }
            }
        } catch (IOException e) {
            LOG.error("Error due read/write file", e);
            throw new IllegalStateException("Error due read/write file", e);
        }
    }

    /**
     * @param line a log line to analyze
     * @return <tt>true</tt> if status starts with 2 or 3 (success or redirection)
     */
    private boolean isAvailable(String line) {
        char firstChar = line.charAt(0);
        return firstChar == '2' || firstChar == '3';
    }

    /**
     * @param line a log line
     * @return time of logged event
     */
    private String getTime(String line) {
        return line == null ? null : line.substring(line.indexOf(' ') + 1);
    }
}
