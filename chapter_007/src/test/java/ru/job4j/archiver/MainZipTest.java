package ru.job4j.archiver;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.TempIOData;

import java.io.IOException;

import static ru.job4j.TempIOData.TMP_TEST_ROOT;
import static ru.job4j.TempIOData.assertOut;
import static ru.job4j.archiver.ArgsTest.ARCHIVE_PATH;

public class MainZipTest {

    @BeforeClass
    public static void init() throws IOException {
        TempIOData.init();
    }

    @Test
    public void testStart() throws IOException {
        String expectedLine = "Archive of '" + TMP_TEST_ROOT
                + "' excluding [java, txt] files is successfully created in " + ARCHIVE_PATH;
        assertOut(expectedLine,
                MainZip::main,
                "-d", TMP_TEST_ROOT, "-e", "java,txt", "-o", ARCHIVE_PATH);
    }

    @Test
    public void testStartWithoutExts() throws IOException {
        String expectedLine = "Archive of '" + TMP_TEST_ROOT
                + "' is successfully created in " + ARCHIVE_PATH;
        assertOut(expectedLine,
                MainZip::main,
                "-d", TMP_TEST_ROOT, "-o", ARCHIVE_PATH);
    }

    @Test
    public void testStartWithError() throws IOException {
        String expectedLine = "cannot create zip: cannot parse args: Missing argument for option: o";
        assertOut(expectedLine,
                MainZip::main,
                "-d", TMP_TEST_ROOT, "-e", "java,txt", "-o");
    }

}