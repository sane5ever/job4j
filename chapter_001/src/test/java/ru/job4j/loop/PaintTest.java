package ru.job4j.loop;

import org.junit.Test;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест-класс для Paint.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-14
 */
public class PaintTest {
    @Test
    public void whenPyramid4Right() {
        Paint paint = new Paint();
        String result = paint.rightTrl(4);
        System.out.println(result);
        assertThat(result,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("^   ")
                        .add("^^  ")
                        .add("^^^ ")
                        .add("^^^^")
                        .toString()
                )
                );
    }

    @Test
    public void whenPyramid4Left() {
        Paint paint = new Paint();
        String result = paint.leftTrl(4);
        System.out.println(result);
        assertThat(result,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("   ^")
                        .add("  ^^")
                        .add(" ^^^")
                        .add("^^^^")
                        .toString()
                )

        );
    }

    @Test
    public void whenPyramid4() {
        Paint paint = new Paint();
        String result = paint.pyramid(4);
        System.out.println(result);
        assertThat(result,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("   ^   ")
                                .add("  ^^^  ")
                                .add(" ^^^^^ ")
                                .add("^^^^^^^")
                                .toString()
                )

        );
    }
}
