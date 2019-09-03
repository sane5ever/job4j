package ru.job4j.find;


import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

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
        try (PrintWriter writer = new PrintWriter(Files.newOutputStream(output))) {
            Files.walkFileTree(root, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (matcher.matches(file.getFileName())) {
                        writer.println(file.toString());
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("cannot read/write file", e);
        }
    }
}
