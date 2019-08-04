package ru.job4j.find;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.TempIOData;

import java.io.IOException;

import static ru.job4j.TempIOData.TMP_TEST_ROOT;
import static ru.job4j.TempIOData.assertOut;
import static ru.job4j.find.ArgsTest.LOG_PATH;

public class MainFinderTest {
    @BeforeClass
    public static void init() throws IOException {
        TempIOData.init();
    }

    @Test
    public void testStart() throws IOException {
        String expectedLine = "Search results successfully written to file '"
                + LOG_PATH + '\'';
        assertOut(expectedLine, MainFinder::main,
                "-d", TMP_TEST_ROOT,
                "-n", "error*.log", "-m",
                "-o", LOG_PATH
        );
    }

    @Test
    public void testStartError() throws IOException {
        String expectedLine = "ERROR: directory parameter should be set (-d directory)";
        assertOut(expectedLine, MainFinder::main,
                "-n", "error*.log", "-m",
                "-o", LOG_PATH
                );
    }
}
