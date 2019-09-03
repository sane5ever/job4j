package ru.job4j.archiver;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static ru.job4j.TempIOData.TMP_DIR;
import static ru.job4j.TempIOData.TMP_TEST_ROOT;

public class ArgsTest {
    static final String ARCHIVE_PATH = TMP_DIR + "archive.zip";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testParse() {
        String[] args = {
                "-d", TMP_TEST_ROOT, "-e", "java,txt", "-o", ARCHIVE_PATH
        };
        Args arguments = new Args(args);
        arguments.parse();
        assertEquals(TMP_TEST_ROOT, arguments.directory());
        assertEquals(ARCHIVE_PATH, arguments.output());
        assertEquals(2, arguments.exclude().size());
        assertEquals("java", arguments.exclude().get(0));
        assertEquals("txt", arguments.exclude().get(1));

    }

    @Test
    public void  testNotParseError() {
        String[] args = {
                "-d", TMP_TEST_ROOT, "-e", "java,txt", "-o", ARCHIVE_PATH
        };
        Args arguments = new Args(args);
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("parameters map is empty, firstly invoke parse method");
        arguments.directory();
    }

    @Test
    public void testParseMissingArg() {
        String[] args = {
                "-d", TMP_TEST_ROOT, "-e", "java,txt"
        };
        Args arguments = new Args(args);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("output parameter should be set");
        arguments.parse();
    }

    @Test
    public void testParseMissingArgValue() {
        String[] args = {
                "-d", TMP_TEST_ROOT, "-e", "java,txt", "-o"
        };
        Args arguments = new Args(args);
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("cannot parse args: Missing argument for option: o");
        arguments.parse();
    }


}