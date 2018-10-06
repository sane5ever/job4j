package ru.job4j.pseudo;

import org.junit.After;
import org.junit.Before;
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
    /** default-вывод в консоль */
    private final PrintStream stdout = System.out;
    /** буфер для результата */
    private final OutputStream buffer = new ByteArrayOutputStream();
    /** поток вывода для перехвата консоли в буфер */
    private PrintStream out;

    @Before
    public void loadOutput() {
        this.out = new PrintStream(this.buffer);
        System.setOut(this.out);
    }

    @After
    public void backOutput() {
        System.setOut(this.stdout);
        this.out.close();
    }

    @Test
    public void whenDrawSizeIsFourAndFigureIsSquare() {
        new Paint().draw(new Square(4));
        assertThat(
                this.buffer.toString(),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("++++")
                        .add("++++")
                        .add("++++")
                        .add("++++")
                        .toString()
                )
        );
    }

    @Test
    public void whenDrawSizeIfThreeAndFigureIsTriangle() {
        new Paint().draw(new Triangle(3));
        assertThat(
                this.buffer.toString(),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("+")
                        .add("++")
                        .add("+++")
                        .toString()
                )
        );
    }
}
