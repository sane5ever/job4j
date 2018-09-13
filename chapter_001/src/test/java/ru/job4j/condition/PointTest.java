package ru.job4j.condition;


import org.junit.Test;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * Тест-класс для Point.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 13.09.2018
 */

public class PointTest {
    @Test
    public void when3And4Then5() {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(3, 4);
        double result = point1.distanceTo(point2);
        assertThat(result, closeTo(5D, 0.1));
    }
}
