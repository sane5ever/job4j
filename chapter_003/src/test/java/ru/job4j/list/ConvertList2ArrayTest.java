package ru.job4j.list;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестируем конвертацию ArrayList в двухмерный массив.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-18
 */
public class ConvertList2ArrayTest {
    @Test
    public void when7ElementsAnd3RowsThen9() {
        ConvertList2Array converter = new ConvertList2Array();
        int[][] result = converter.toArray(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7),
                3
        );
        int[][] expected = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 0}
        };
        assertThat(result, is(expected));
    }

    @Test
    public void when12ElementsAnd3RowsThen12() {
        ConvertList2Array converter = new ConvertList2Array();
        int[][] result = converter.toArray(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                3
        );
        int[][] expected = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        assertThat(result, is(expected));
    }

    @Test
    public void when4ElementsAnd4RowsThen4() {
        ConvertList2Array converter = new ConvertList2Array();
        int[][] result = converter.toArray(
                Arrays.asList(1, 2, 3, 4),
                4
        );
        int[][] expected = {
                {1},
                {2},
                {3},
                {4}
        };
        assertThat(result, is(expected));
    }

    @Test
    public void when3ElementsAnd5RowsThen5() {
        ConvertList2Array converter = new ConvertList2Array();
        int[][] result = converter.toArray(
                Arrays.asList(1, 2, 3),
                5
        );
        int[][] expected = {
                {1},
                {2},
                {3},
                {0},
                {0}
        };
        assertThat(result, is(expected));
    }

    @Test
    public void whenEmptyListAnd5RowsThen5() {
        ConvertList2Array converter = new ConvertList2Array();
        int[][] result = converter.toArray(
                Collections.emptyList(),
                5
        );
        int[][] expected = {
                {0},
                {0},
                {0},
                {0},
                {0}
        };
        assertThat(result, is(expected));
    }
}
