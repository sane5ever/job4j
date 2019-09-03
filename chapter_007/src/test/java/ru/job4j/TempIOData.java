package ru.job4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.archiver.MainZip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

/**
 * Presents temp file system data for various program tests
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-08-04
 */
public class TempIOData {
    private static final Logger LOG = LoggerFactory.getLogger(TempIOData.class);

    public final static String TMP_DIR = System.getProperty("java.io.tmpdir") + File.separatorChar;

    public final static String TMP_TEST_ROOT = TMP_DIR + "TEST_SYSTEM" + File.separatorChar;

    public final static List<File> DIRS = List.of(
            new File(TMP_TEST_ROOT),
            new File(TMP_TEST_ROOT, "1"),
            new File(TMP_TEST_ROOT, "2"),
            new File(TMP_TEST_ROOT, "3"),
            new File(TMP_TEST_ROOT + "1", "4"),
            new File(TMP_TEST_ROOT + "1", "5"),
            new File(TMP_TEST_ROOT + "2", "6")
    );
    public static File file1;
    public static File file2;
    public static File file3;

    public static void init() throws IOException {
        makeDirs();
        file1 = new File(DIRS.get(0), "1.java");
        file2 = new File(DIRS.get(4), "2.xml");
        file3 = new File(DIRS.get(5), "3.txt");
        File fileWithoutExt = new File(DIRS.get(2), "4txt");
        createFiles(file1, file2, file3, fileWithoutExt);
    }

    public static String relativize(String root, String child) {
        return Paths.get(root).relativize(Paths.get(child)).toString();
    }

    public static void assertOut(String expected, Consumer<String[]> runner, String... args) throws IOException {
        PrintStream origOut = System.out;
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();
             PrintStream mockOut = new PrintStream(buffer)) {
            System.setOut(mockOut);
            runner.accept(args);
            System.setOut(origOut);
            String actual = buffer.toString();
            assertEquals(expected + lineSeparator(), actual);
        }
    }

    private static void makeDirs() {
        for (File d : DIRS) {
            if (!d.mkdir()) {
                LOG.error("dir {} is not created", d);
            }
            d.deleteOnExit();
        }
    }

    private static void createFiles(File... files) throws IOException {
        for (File f : files) {
            if (!f.createNewFile()) {
                LOG.error("file {} is not created", f);
            }
            f.deleteOnExit();
        }
    }
}

