package ru.job4j.function;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * Подсчет функции в диапазоне. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-21
 */
public class CounterTest {
    @Test
    public void whenLinearFrom3To6() {
        Counter counter = new Counter();
        List<Double> result = counter.diapason(3, 6, aDouble -> aDouble);
        List<Double> expected = Arrays.asList(
                3d, 4d, 5d
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenQuadraticFrom1To5() {
        Counter counter = new Counter();
        List<Double> result = counter.diapason(1, 5, aDouble -> aDouble * aDouble);
        List<Double> expected = Arrays.asList(
                1d, 4d, 9d, 16d
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenLogarithmicFrom1to3() {
        Counter counter = new Counter();
        List<Double> result = counter.diapason(1, 3, Math::log);
        assertThat(result.get(1), closeTo(0.69, 0.01));
    }
}
