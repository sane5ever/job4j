package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест-класс для Square.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-18
 */
public class SquareTest {
    @Test
    public void whenBound3Then149() {
        int bound = 3;
        Square square = new Square();
        int[] result = square.calculate(bound);
        int[] expected = new int[] {1, 4, 9};
        assertThat(result, is(expected));
    }

    @Test
    public void whenBound5Then1491625() {
        int bound = 5;
        Square square = new Square();
        int[] result = square.calculate(bound);
        int[] expected = new int[] {1, 4, 9, 16, 25};
        assertThat(result, is(expected));
    }

    @Test
    public void whenBound10Then149162536496481100() {
        int bound = 10;
        Square square = new Square();
        int[] result = square.calculate(bound);
        int[] expected = new int[] {1, 4, 9, 16, 25, 36, 49, 64, 81, 100};
        assertThat(result, is(expected));
    }

}
