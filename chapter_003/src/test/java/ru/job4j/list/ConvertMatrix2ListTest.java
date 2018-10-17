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

    @Test
    public void when2ArraysInList() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        List<int[]> list = Arrays.asList(
                new int[]{1, 2},
                new int[]{3, 4, 5, 6}
        );
        List<Integer> expected = Arrays.asList(
                1, 2, 3, 4, 5, 6
        );
        List<Integer> result = converter.convert(list);
        assertThat(result, is(expected));
    }

    @Test
    public void when1ArrayInList() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        List<int[]> list = Arrays.asList(
                new int[]{1, 2, 3}
        );
        List<Integer> expected = Arrays.asList(
                1, 2, 3
        );
        List<Integer> result = converter.convert(list);
        assertThat(result, is(expected));
    }

    @Test
    public void when4ArraysWithSingleElement() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        List<int[]> list = Arrays.asList(
                new int[]{1},
                new int[]{2},
                new int[]{3},
                new int[]{4}
        );
        List<Integer> expected = Arrays.asList(
                1, 2, 3, 4
        );
        List<Integer> result = converter.convert(list);
        assertThat(result, is(expected));
    }

    @Test
    public void when5ArraysWithNoElement() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        List<int[]> list = Arrays.asList(
                new int[]{},
                new int[]{},
                new int[]{},
                new int[]{},
                new int[]{}
        );
        List<Integer> expected = Collections.emptyList();
        List<Integer> result = converter.convert(list);
        assertThat(result, is(expected));
    }

    @Test
    public void whenAbsolutelyEmptyList() {
        ConvertMatrix2List converter = new ConvertMatrix2List();
        List<int[]> list = Collections.emptyList();
        List<Integer> expected = Collections.emptyList();
        List<Integer> result = converter.convert(list);
        assertThat(result, is(expected));
    }
}
