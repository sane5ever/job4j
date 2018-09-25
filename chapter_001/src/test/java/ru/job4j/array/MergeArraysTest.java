package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование слияний массивов.
 */
public class MergeArraysTest {
    @Test
    public void whenTwoSortedArraysThenThirdSortedArray() {
        MergeArrays merger = new MergeArrays();
        int[] first = {1, 5, 20, 100, 200, 201};
        int[] second = {0, 5, 100, 1000};
        int[] result = merger.merge(first, second);
        int[] expected = {0, 1, 5, 5, 20, 100, 100, 200, 201, 1000};
        assertThat(result, is(expected));
    }

    @Test
    public void whenInputArraysAreEqual() {
        MergeArrays merger = new MergeArrays();
        int[] first = {0, 1, 2, 3};
        int[] second = {0, 1, 2, 3};
        int[] result = merger.merge(first, second);
        int[] expected = {0, 0, 1, 1, 2, 2, 3, 3};
        assertThat(result, is(expected));
    }

    @Test
    public void whenTheLastElementOfFirstArrayIsLessThenTheFirstElementOfSecondOne() {
        MergeArrays merger = new MergeArrays();
        int[] first = {-5, -4, -2, 0, 0};
        int[] second = {1, 2, 4, 6};
        int[] result = merger.merge(first, second);
        int[] expected = {-5, -4, -2, 0, 0, 1, 2, 4, 6};
        assertThat(result, is(expected));
    }

    @Test
    public void whenTheLastElementOfSecondArrayIsLessThenTheFirstElementOfFirstOne() {
        MergeArrays merger = new MergeArrays();
        int[] first = {1, 2, 3};
        int[] second = {-5, -4, -2, -1, 0};
        int[] result = merger.merge(first, second);
        int[] expected = {-5, -4, -2, -1, 0, 1, 2, 3};
        assertThat(result, is(expected));
    }
}
