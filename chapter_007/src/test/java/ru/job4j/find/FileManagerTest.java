package ru.job4j.find;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.job4j.TempIOData;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.job4j.TempIOData.TMP_TEST_ROOT;
import static ru.job4j.find.ArgsTest.LOG_PATH;
import static ru.job4j.find.Util.PATH_MATCHERS;

public class FileManagerTest {
    private static final Path LOG_OUTPUT = Paths.get(LOG_PATH);
    private static final char FS = File.separatorChar;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void init() throws IOException {
        TempIOData.init();
    }

    @Test
    public void testSearchMask() throws IOException {
        PathMatcher matcher = PATH_MATCHERS.get(Arg.m).apply("*txt");
        FileManager manager = new FileManager(TMP_TEST_ROOT, LOG_PATH, matcher);
        manager.search();
        checkLogOutputFile(2, "4txt", "3.txt");
    }

    @Test
    public void testSearchFullIdentity() throws IOException {
        PathMatcher matcher = PATH_MATCHERS.get(Arg.f).apply("4txt");
        FileManager manager = new FileManager(TMP_TEST_ROOT, LOG_PATH, matcher);
        manager.search();
        checkLogOutputFile(1, "4txt");
    }

    @Test
    public void testSearchRegex() throws IOException {
        PathMatcher matcher = PATH_MATCHERS.get(Arg.r).apply(".+[txt|java]");
        FileManager manager = new FileManager(TMP_TEST_ROOT, LOG_PATH, matcher);
        manager.search();
        checkLogOutputFile(3, "1.java", "3.txt", "4txt");
    }

    @Test
    public void testSearchCatchIOException() {
        FileManager manager = new FileManager(TMP_TEST_ROOT, "", filename -> true);
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("cannot read/write file");
        manager.search();
    }

    private void checkLogOutputFile(int size, String... fileNames) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(LOG_OUTPUT)));
        ) {
            List<String> filenames = reader.lines()
                    .map(filename -> filename.substring(filename.lastIndexOf(FS) + 1))
                    .collect(Collectors.toList());
            assertEquals(size, filenames.size());
            for (String fileName : fileNames) {
                assertTrue(filenames.contains(fileName));
            }
        }
    }

}