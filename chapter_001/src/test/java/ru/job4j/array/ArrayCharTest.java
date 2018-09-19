package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест-класс для ArrayChar.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-09-19
 */
public class ArrayCharTest {
    @Test
    public void whenStartWithPrefixThenTrue() {
        ArrayChar word = new ArrayChar("Hello");
        boolean result  = word.startWith("He");
        assertThat(result, is(true));
    }

    @Test
    public void whenNotStartWithPrefixThenFalse() {
        ArrayChar word = new ArrayChar("Hello!");
        boolean result = word.startWith("Hi");
        assertThat(result, is(false));
    }
}
