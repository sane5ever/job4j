package ru.job4j.io;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Удаление запрещённых слов. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-02-08
 */
public class DropAbusesInputCheckerTest {
    private static final Logger LOG = LoggerFactory.getLogger(DropAbusesInputCheckerTest.class);
    private static final String[] ABUSES = new String[]{
            "damn",
            "hell",
            "crap",
            "dammit",
            "bitch"
    };
    private static final String[] EMPTY_ABUSES = new String[0];

    private final InputChecker checker = new InputChecker();

    @Test
    public void whenSingleLineStreamThenAbusesShouldBeRemoved() {
        String line = "hello bitch! it's hell out there";
        String expected = "hello *****! it's **** out there";
        onSingleLineStream(line, expected, ABUSES);
        onSingleLineStream(line, line, EMPTY_ABUSES);
        onSingleLineStream(line, line, null);
    }

    @Test
    public void whenMultiLineStreamThenAbusesShouldBeRemoved() {
        String lines = "hello it's hell out there%s oh god DAMMIT get out of hell%<s shame on me";
        String expected = "hello it's **** out there%s oh god ****** get out of ****%<s shame on me";
        onMultiLineStream(lines, expected, ABUSES);
        onMultiLineStream(lines, lines, EMPTY_ABUSES);
        onMultiLineStream(lines, lines, null);
    }

    @Test
    public void whenEmptyStreamThenShouldBeEmptyStream() {
        assertEquals("", execute("", ABUSES));
        assertEquals("", execute("", EMPTY_ABUSES));
        assertEquals("", execute("", null));
    }

    @Test
    public void whenIOExceptionThrown() {
        try (InputStream in = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
             ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            checker.dropAbuses(in, out, EMPTY_ABUSES);
            assertThat(out.size(), is(0));
        } catch (IOException ignore) {
        }
    }

    private void onSingleLineStream(String line, String expected, String[] abuses) {
        String result = execute(line, abuses);
        assertEquals(String.format(expected, System.lineSeparator()), result);
    }

    private void onMultiLineStream(String lines, String expected, String[] abuses) {
        String result = execute(String.format(lines, System.lineSeparator()), abuses);
        assertEquals(String.format(expected, System.lineSeparator()), result);
    }

    private String execute(String lines, String[] abuses) {
        String result;
        try (InputStream in = new ByteArrayInputStream(lines.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            checker.dropAbuses(in, out, abuses);
            result = out.toString();
        } catch (IOException ioe) {
            LOG.error(ioe.getMessage(), ioe);
            result = null;
        }
        return result;
    }

}
