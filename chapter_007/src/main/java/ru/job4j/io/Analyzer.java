package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Server availability analyzer
 */
public class Analyzer {
    private static final Logger LOG = LoggerFactory.getLogger(Analyzer.class);

    /**
     * Analyzes presented server logs and writes the file with time intervals when server was down to the target
     *
     * @param source source file path
     * @param target target file path
     */
    public void unavailable(String source, String target) {
        try {
            var parsedLines = parseLog(source);
            writeLog(target, parsedLines);
        } catch (IOException e) {
            LOG.error("Error due read/write file", e);
            throw new IllegalStateException("Error due read/write file", e);
        }
    }

    /**
     * Parses unavailable log lines from the given file path
     *
     * @param sourcePath source file path
     * @return parsed unavailable time lines
     * @throws IOException if an io error occurs
     */
    private List<String> parseLog(String sourcePath) throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourcePath)))) {
            boolean unavailable = false;
            for (String line = reader.readLine(), startTime = getTime(line);
                 line != null;
                 line = reader.readLine()) {
                if (unavailable && isAvailable(line)) {
                    result.add(startTime + ';' + getTime(line));
                    unavailable = false;
                } else if (!unavailable && !isAvailable(line)) {
                    startTime = getTime(line);
                    unavailable = true;
                }
            }
        }
        return result;
    }

    /**
     * Write the given log lines to the file
     *
     * @param targetPath target file path
     * @param logLines   log lines to be written
     * @throws FileNotFoundException if the target path file does not exist
     */
    private void writeLog(String targetPath, List<String> logLines) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(targetPath))) {
            logLines.forEach(writer::println);
        }
    }

    /**
     * Checks if a log line status code means the server was available
     *
     * @param line a log line to analyze
     * @return <tt>true</tt> if status starts with 2 or 3 (success or redirection)
     */
    private boolean isAvailable(String line) {
        char firstChar = line.charAt(0);
        return firstChar == '2' || firstChar == '3';
    }

    /**
     * Writes the given log lines to file
     *
     * @param line a log line
     * @return time of logged event
     */
    private String getTime(String line) {
        return line == null ? null : line.substring(line.indexOf(' ') + 1);
    }
}