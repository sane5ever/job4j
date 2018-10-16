package ru.job4j.list;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестируем конвертацию двумерного массива в ArrayList.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-18
 */
public class ConvertMatrix2ListTest {
    @Test
    public void when2on2ArrayThenList4() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        int[][] input = {
                {1, 2},
                {3, 4}
        };
        List<Integer> expected = Arrays.asList(
                1, 2, 3, 4
        );
        List<Integer> result = converter.toList(input);
        assertThat(result, is(expected));
    }

    @Test
    public void when1on3ArrayThenList3() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        int[][] input = {
                {1, 2, 3}
        };
        List<Integer> expected = Arrays.asList(
                1, 2, 3
        );
        List<Integer> result = converter.toList(input);
        assertThat(result, is(expected));
    }

    @Test
    public void when4on1ArrayThenList4() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        int[][] input = {
                {1},
                {2},
                {3},
                {4}
        };
        List<Integer> expected = Arrays.asList(
                1, 2, 3, 4
        );
        List<Integer> result = converter.toList(input);
        assertThat(result, is(expected));
    }

    @Test
    public void when3EmptyRowsArrayThenEmptyList() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        int[][] input = {
                {},
                {},
                {}
        };
        List<Integer> expected = Collections.emptyList();
        List<Integer> result = converter.toList(input);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAbsolutelyEmptyArrayThenEmptyList() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        int[][] input = {};
        List<Integer> expected = Collections.emptyList();
        List<Integer> result = converter.toList(input);
        assertThat(result, is(expected));
    }
}
