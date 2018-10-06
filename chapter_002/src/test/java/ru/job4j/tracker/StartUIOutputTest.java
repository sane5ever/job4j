package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StartUIOutputTest {
    /** default-вывод в консоль */
    private final PrintStream stdout = System.out;
    /** буфер для результата */
    private final OutputStream buffer = new ByteArrayOutputStream();
    /** поток вывода для перехвата консоли в буфер */
    private PrintStream out;

    private final Tracker tracker = new Tracker();
    private Input input;

    @Before
    public void prepare() {
        this.out = new PrintStream(this.buffer);
        System.setOut(this.out);
        this.initData();
    }

    @After
    public void complete() {
        System.setOut(this.stdout);
        this.out.close();
    }

    @Test
    public void whenTryToShowAll() {
        input = new StubInput(new String[] {"1", "6"});
        new StartUI(this.input, this.tracker).init();
        assertThat(
                this.buffer.toString(),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add(StartUI.HEADER)
                        .add("------------ Вывод имеющихся заявок ---------------")
                        .add("11111111|first|test|01-01-70")
                        .add("22222222|second|test|01-01-70")
                        .add("33333333|third|test|01-01-70")
                        .add("---------------------------------------------------")
                        .add(StartUI.HEADER)
                        .toString()
                )
        );
    }

    @Test
    public void whenTryToFindById() {
        input = new StubInput(new String[] {"4", "22222222", "6"});
        new StartUI(this.input, this.tracker).init();
        assertThat(
                this.buffer.toString(),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add(StartUI.HEADER)
                        .add("--------------- Поиск заявки по ID ----------------")
                        .add("22222222|second|test|01-01-70")
                        .add("---------------------------------------------------")
                        .add(StartUI.HEADER)
                        .toString()
                )
        );
    }

    @Test
    public void whenTryToFindByName() {
        input = new StubInput(new String[] {"5", "first", "6"});
        new StartUI(this.input, this.tracker).init();
        assertThat(
                this.buffer.toString(),
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add(StartUI.HEADER)
                        .add("--------------- Поиск заявок по имени --------------")
                        .add("11111111|first|test|01-01-70")
                        .add("---------------------------------------------------")
                        .add(StartUI.HEADER)
                        .toString()
                )
        );
    }

    /**
     * Заполняем трекер тестовыми заявками с жёстко запрограммированными значениями.
     */
    private void initData() {
        Item first = new Item("first", "test");
        Item second = new Item("second", "test");
        Item third = new Item("third", "test");
        this.tracker.add(first);
        this.tracker.add(second);
        this.tracker.add(third);

        first.setId("11111111");
        first.setCreate(0L);

        second.setId("22222222");
        second.setCreate(0L);

        third.setId("33333333");
        third.setCreate(0L);
    }
}
