package ru.job4j.find;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides searching logic of file finder
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-25
 */
public class FileManager {
    private final Path root;
    private final Path output;
    private final PathMatcher matcher;

    public FileManager(String root, String output, PathMatcher matcher) {
        this.root = Paths.get(root);
        this.output = Paths.get(output);
        this.matcher = matcher;
    }

    /**
     * Main method to process file searching
     */
    public void search() {
        try {
            var fileNames = searchFiles();
            writeResult(fileNames);
        } catch (IOException e) {
            throw new IllegalStateException("cannot read/write file", e);
        }
    }

    /**
     * Creates a list of matched file paths
     *
     * @return matched file paths
     * @throws IOException if an io error occurs
     */
    private List<String> searchFiles() throws IOException {
        List<String> result = new ArrayList<>();
        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (matcher.matches(file.getFileName())) {
                    result.add(file.toString());
                }
                return super.visitFile(file, attrs);
            }
        });
        return result;
    }

    /**
     * Writes the given file path lines to the file
     *
     * @param lines file names to be written
     * @throws IOException if an io error occurs
     */
    private void writeResult(List<String> lines) throws IOException {
        try (PrintWriter writer = new PrintWriter(Files.newOutputStream(output))) {
            lines.forEach(writer::println);
        }
    }
}