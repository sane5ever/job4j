package ru.job4j.queue;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование корректной работы производителя-потребителя.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-22
 */
public class ParallelSearchTest {
    @Test
    public void test() throws InterruptedException {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream mock = new PrintStream(buffer);
        System.setOut(mock);

        Thread main = new Thread(() -> ParallelSearch.main(new String[0]));
        main.start();
        main.join();
        System.setOut(original);
        mock.close();
        String[] lines = buffer.toString().split(System.lineSeparator());
        assertThat(lines[0], is("0"));
        assertThat(lines[1], is("1"));
        assertThat(lines[2], is("2"));
        assertThat(lines[3], is("Producer is finishing."));
        assertThat(lines[4], is("Consumer is finishing."));
    }
}
