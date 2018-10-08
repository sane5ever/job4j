package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateInputTest {
    private final OutputStream buffer = new ByteArrayOutputStream();
    private final PrintStream stdout = System.out;
    private PrintStream out;

    @Before
    public void prepare() {
        out = new PrintStream(this.buffer);
        System.setOut(out);
    }

    @After
    public void complete() {
        System.setOut(this.stdout);
        out.close();
    }

    @Test
    public void whenInvalidInput() {
        ValidateInput input = new ValidateInput(new StubInput(new String[] {"invalid", "1"}));
        input.ask("Enter", new int[] {1});
        assertThat(
                this.buffer.toString(),
                is(
                        String.format("Please enter valid data again.%n")
                )
        );
    }
}
