package ru.job4j.find;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.job4j.TempIOData.TMP_DIR;
import static ru.job4j.TempIOData.TMP_TEST_ROOT;

public class ArgsTest {
    static final String LOG_PATH = TMP_DIR + "result.log";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testParse() {
        String[] args = {
                "-d", TMP_TEST_ROOT,
                "-n", "error*.log", "-m",
                "-o", LOG_PATH
        };
        Args arguments = new Args(args);
        arguments.parse();
        assertEquals(TMP_TEST_ROOT, arguments.directory());
        assertEquals("error*.log", arguments.name());
        assertEquals(LOG_PATH, arguments.output());
        // test that a matcher has been switched correct
        Path testedPath = Paths.get("error123121234.log");
        assertTrue(arguments.matcher().matches(testedPath));
    }

    @Test
    public void testNotParseError() {
        Args arguments = new Args(null);
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("parameters map is empty, firstly invoke parse method");
        arguments.directory();
    }

    @Test
    public void testParseMissingRequiredArg() {
        String[] args = {
                "-d", TMP_TEST_ROOT,
                "-n", "error*.log", "-m"
        };
        Args arguments = new Args(args);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("output parameter should be set");
        arguments.parse();
    }

    @Test
    public void testParseSeveralMatcherTypeArgs() {
        String[] args = {
                "-d", TMP_TEST_ROOT,
                "-n", "error*.log", "-m", "-f",
                "-o", LOG_PATH
        };
        Args arguments = new Args(args);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("-m presented along with -f. Accepted only one matcher type parameter");
        arguments.parse();
    }

    @Test
    public void testMatcherNoMatcherTypeArgs() {
        String[] args = {
                "-d", TMP_TEST_ROOT,
                "-n", "error*.log",
                "-o", LOG_PATH
        };
        Args arguments = new Args(args);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("One of matcher type parameters should be set");
        arguments.parse();
        arguments.matcher();
    }
}