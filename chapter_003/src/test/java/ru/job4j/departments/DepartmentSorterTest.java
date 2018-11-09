package ru.job4j.departments;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DepartmentSorterTest {
    @Test
    public void whenAscending() {
        DepartmentSorter sorter = new DepartmentSorter();
        String[] input = new String[]{
                "K1\\SK1",
                "K1\\SK2",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
        String[] result = sorter.ascending(input);
        String[] expected = new String[]{
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K1\\SK2",
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"

        };
        assertThat(result, is(expected));
    }

    @Test
    public void whenDescending() {
        DepartmentSorter sorter = new DepartmentSorter();
        String[] input = new String[]{
                "K1\\SK1",
                "K1\\SK2",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
        String[] result = sorter.descending(input);
        String[] expected = new String[]{
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1",
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        };
        assertThat(result, is(expected));
    }

    @Test
    public void whenNotNestedThenSorted() {
        DepartmentSorter sorter = new DepartmentSorter();
        String[] input = new String[]{
                "K2",
                "K1",
                "K3"

        };
        String[] result = sorter.ascending(input);
        String[] expected = new String[]{
                "K1",
                "K2",
                "K3"
        };
        assertThat(result, is(expected));
    }

    @Test
    public void whenOnlyBottomLevelAndDescendingThenExpanded() {
        DepartmentSorter sorter = new DepartmentSorter();
        String[] input = new String[]{
                "K1\\SK1\\SSK1\\BSSK2\\SBSSK0",
        };
        String[] result = sorter.descending(input);
        String[] expected = new String[]{
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK1\\BSSK2",
                "K1\\SK1\\SSK1\\BSSK2\\SBSSK0"
        };
        assertThat(result, is(expected));
    }

    @Test
    public void whenSortedThenEquals() {
        DepartmentSorter sorter = new DepartmentSorter();
        String[] input = new String[]{
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK2",
                "K2\\SK1\\SSK1",
                "K1",
                "K1\\SK2",
                "K1\\SK1",
                "K1\\SK1\\SSK2",
                "K1\\SK1\\SSK1"
        };
        String[] result = sorter.descending(input);
        assertThat(result, is(input));
    }

    @Test
    public void whenEmpty() {
        DepartmentSorter sorter = new DepartmentSorter();
        String[] input = new String[]{};
        String[] result = sorter.ascending(input);
        String[] expected = new String[]{};
        assertThat(result, is(expected));
    }
}