package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест-класс для Max
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 13.09.2018
 */
public class MaxTest {

    @Test
    public void whenFirstLessSecond() {
        Max maximum = new Max();
        int result = maximum.max(1, 2);
        assertThat(result, is(2));
    }

    @Test
    public void whenSecondLessFirst() {
        Max maximum = new Max();
        int result = maximum.max(2, 1);
        assertThat(result, is(2));
    }

    @Test
    public void whenFirstEqualsSecond() {
        Max maximum = new Max();
        int result = maximum.max(0, 0);
        assertThat(result, is(0));
    }
}
