package ru.job4j.pseudo;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестируем отрисовку фигур.
 */
public class PaintTest {

    @Test
    public void whenDrawSizeIsFourAndFigureIsSquare() {
        PrintStream stdout = System.out;
        OutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer);
        System.setOut(out);
        new Paint().draw(new Square(4));
        assertThat(
                buffer.toString(),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("++++")
                        .add("++++")
                        .add("++++")
                        .add("++++")
                        .toString()
                )
        );
        System.setOut(stdout);
        out.close();
    }

    @Test
    public void whenDrawSizeIfThreeAndFigureIsTriangle() {
        PrintStream stdout = System.out;
        OutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer);
        System.setOut(out);
        new Paint().draw(new Triangle(3));
        assertThat(
                buffer.toString(),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("+")
                        .add("++")
                        .add("+++")
                        .toString()
                )
        );
        System.setOut(stdout);
        out.close();
    }
}
