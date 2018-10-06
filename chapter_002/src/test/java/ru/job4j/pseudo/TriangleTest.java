package ru.job4j.pseudo;

import org.junit.Test;

import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестируем формирование треугольника на вывод.
 */
public class TriangleTest {

    @Test
    public void whenDratTriangleWithSize4() {
        Triangle triangle = new Triangle(4);
        assertThat(
                triangle.draw(),
                is(
                        new StringJoiner(System.lineSeparator())
                        .add("+")
                        .add("++")
                        .add("+++")
                        .add("++++")
                        .toString()
                )
        );
    }

}
