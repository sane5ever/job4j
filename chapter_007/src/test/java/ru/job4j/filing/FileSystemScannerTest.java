package ru.job4j.filing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileSystemScannerTest {
    private static final Logger LOG = LogManager.getLogger(FileSystemScannerTest.class);

    private final static String TMP = String.format("%s/%s", System.getProperty("java.io.tmpdir"), "TEST_SYSTEM");
    private final static List<File> DIRS = List.of(
            new File(TMP),
            new File(TMP, "1"),
            new File(TMP, "2"),
            new File(TMP, "3"),
            new File(TMP + "/1", "4"),
            new File(TMP + "/1", "5"),
            new File(TMP + "/2", "6")
    );

    FileSystemScanner scanner = new FileSystemScanner();

    private static File file1;
    private static File file2;
    private static File file3;

    @BeforeClass
    public static void init() {
        makeDirs();
        file1 = new File(DIRS.get(0), "1.java");
        file2 = new File(DIRS.get(4), "2.xml");
        file3 = new File(DIRS.get(5), "3.txt");
        File fileWithoutExt = new File(DIRS.get(2), "4txt");
        createFiles(file1, file2, file3, fileWithoutExt);
    }

    private static void makeDirs() {
        for (File d : DIRS) {
            if (!d.mkdir()) {
                LOG.error("dir {} is not created", d);
            }
            d.deleteOnExit();
        }
    }

    private static void createFiles(File... files) {
        try {
            for (File f : files) {
                if (!f.createNewFile()) {
                    LOG.error("file {} is not created", f);
                }
                f.deleteOnExit();
            }
        } catch (IOException e) {
            LOG.error("io error during creating test files", e);
        }
    }

    @Test
    public void whenJavaAndXml() {
        assertEquals(
                List.of(file1, file2),
                scanner.files(TMP, List.of("java", "xml"))
        );
    }

    @Test
    public void whenTxt() {
        assertEquals(
                List.of(file3),
                scanner.files(TMP, List.of("txt"))
        );
    }

    @Test
    public void whenEmptyList() {
        assertEquals(
                Collections.<File>emptyList(),
                scanner.files(TMP, Collections.emptyList())
        );
    }

}