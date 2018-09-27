package ru.job4j.condition;


import org.junit.Test;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * Тест-класс для Triangle
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-14
 */
public class TriangleTest {
    @Test
    public void whenAreaSetThreePointsThenTriangleArea() {
        Triangle abc = new Triangle(
                new Point(0, 0),
                new Point(0, 2),
                new Point(2, 0)
        );
        double result = abc.area();
        double expected = 2D;
        assertThat(result, closeTo(expected, 0.1));
    }

    @Test
    public void whenTriangleDoesNotExist() {
        Triangle abc = new Triangle(
                new Point(0, 0),
                new Point(0, 0),
                new Point(0, 5)
        );
        double result = abc.area();
        double expected = -1D;
        assertThat(result, closeTo(expected, 0.1));
    }
}