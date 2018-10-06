package ru.job4j.pseudo;

import org.junit.Test;

import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестируем формирование квалрата на вывод.
 */
public class SquareTest {
    @Test
    public void whenDrawSquare() {
        Square square = new Square(5);
        assertThat(
                square.draw(),
                is(
                        new StringJoiner(System.lineSeparator())
                        .add("+++++")
                        .add("+++++")
                        .add("+++++")
                        .add("+++++")
                        .add("+++++")
                        .toString()
                )
        );
    }
}
