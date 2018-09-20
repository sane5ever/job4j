package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * Тест-класс для ArrayDuplicate.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-20
 */
public class ArrayDuplicateTest {
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        ArrayDuplicate unduplicator = new ArrayDuplicate();
        String[] input = new String[] {"Привет", "Мир", "Привет", "Супер", "Мир"};
        String[] result = unduplicator.remove(input);
        String[] expected = new String[] {"Привет", "Мир", "Супер"};
        assertThat(result, arrayContainingInAnyOrder(expected));
    }

    @Test
    public void whenNoDuplicatesArrayThenInputArray() {
        ArrayDuplicate unduplicator = new ArrayDuplicate();
        String[] input = new String[] {"Привет", "Мир", "Супер"};
        String[] result = unduplicator.remove(input);
        String[] expected = new String[] {"Привет", "Мир", "Супер"};
        assertThat(result, arrayContainingInAnyOrder(expected));
    }

    @Test
    public void whenRepeatingOneWordArrayThenOneWordArray() {
        ArrayDuplicate unduplicator = new ArrayDuplicate();
        String[] input = new String[] {"Привет", "Привет", "Привет", "Привет", "Привет"};
        String[] result = unduplicator.remove(input);
        String[] expected = new String[] {"Привет"};
        assertThat(result, arrayContainingInAnyOrder(expected));
    }

    @Test
    public void whenRemovingDuplicatesChangeExpectedOrderThenMethodStillWorking() {
        ArrayDuplicate unduplicator = new ArrayDuplicate();
        String[] input = new String[] {"Привет", "Привет", "Супер", "Мир", "Мир"};
        String[] result = unduplicator.remove(input);
        String[] expected = new String[] {"Привет", "Супер", "Мир"};
        assertThat(result, arrayContainingInAnyOrder(expected));
    }
}