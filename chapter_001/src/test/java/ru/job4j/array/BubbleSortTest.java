package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест-класс для BubbleSort.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-19
 */
public class BubbleSortTest {
    @Test
    public void whenSortArrayWithTenElementsThenSortedArray() {
        BubbleSort sorter = new BubbleSort();
        int[] input = new int[] {5, 4, 2, 2, 4, 6, 7, 9, 1, 0};
        int[] result = sorter.sort(input);
        int[] expected = new int[] {0, 1, 2, 2, 4, 4, 5, 6, 7, 9};
        assertThat(result, is(expected));
    }

    @Test
    public void whenSortArrayWithFiveElementsThenSortedArray() {
        BubbleSort sorter = new BubbleSort();
        int[] input = new int[] {5, 1, 2, 7, 3};
        int[] result = sorter.sort(input);
        int[] expected = new int[] {1, 2, 3, 5, 7};
        assertThat(result, is(expected));
    }
}
