package ru.job4j.coffeemaker;

import org.junit.Test;
import ru.job4j.coffemaker.Coffeemaker;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Кофемашина. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-23
 */
public class CoffeemakerTest {
    @Test
    public void when50For35Then10And5() {
        Coffeemaker maker = new Coffeemaker();
        int[] result = maker.changes(50, 35);
        int[] expected = {10, 5};
        assertThat(result, is(expected));
    }

    @Test
    public void when200for72Then12Times10And5And2And1() {
        Coffeemaker maker = new Coffeemaker();
        int[] result = maker.changes(200, 72);
        int[] expected = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 2, 1};
        assertThat(result, is(expected));
    }

    @Test
    public void when50For50Then0() {
        Coffeemaker maker = new Coffeemaker();
        int[] result = maker.changes(50, 50);
        int[] expected = {};
        assertThat(result, is(expected));
    }
}
