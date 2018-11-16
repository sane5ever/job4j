package ru.job4j.departments;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DepartmentSorterTest {
    private void execute(String[] input, String[] expected, boolean reverse) {
        DepartmentSorter sorter = new DepartmentSorter(input);
        String[] result = reverse ? sorter.getDescendingData() : sorter.getData();
        assertThat(result, is(expected));
    }

    @Test
    public void whenAscending() {
        String[] input = new String[]{
                "K1\\SK1",
                "K1\\SK2",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
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
        this.execute(input, expected, false);
    }

    @Test
    public void whenDescending() {
        String[] input = new String[]{
                "K1\\SK1",
                "K1\\SK2",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK2",
                "K2",
                "K2\\SK1\\SSK1",
                "K2\\SK1\\SSK2"
        };
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
        this.execute(input, expected, true);
    }

    @Test
    public void whenNotNestedThenSorted() {
        String[] input = new String[]{
                "K2",
                "K1",
                "K3"

        };
        String[] expected = new String[]{
                "K1",
                "K2",
                "K3"
        };
        this.execute(input, expected, false);
    }

    @Test
    public void whenOnlyBottomLevelAndDescendingThenExpanded() {
        String[] input = new String[]{
                "K1\\SK1\\SSK1\\BSSK2\\SBSSK0",
        };
        String[] expected = new String[]{
                "K1",
                "K1\\SK1",
                "K1\\SK1\\SSK1",
                "K1\\SK1\\SSK1\\BSSK2",
                "K1\\SK1\\SSK1\\BSSK2\\SBSSK0"
        };
        this.execute(input, expected, true);
    }

    @Test
    public void whenSortedThenEquals() {
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
        this.execute(input, input, true);
    }

    @Test
    public void whenEmpty() {
        String[] input = new String[]{};
        String[] expected = new String[]{};
        this.execute(input, expected, false);
    }

    @Test
    public void whenNonEqualLengthDescending() {
        String[] input = new String[]{
                "K2\\SK1\\SSK10",
                "K2\\SK10\\SSK1",
        };
        String[] expected = new String[]{
                "K2",
                "K2\\SK10",
                "K2\\SK10\\SSK1",
                "K2\\SK1",
                "K2\\SK1\\SSK10"
        };
        this.execute(input, expected, true);
    }

    @Test
    public void whenNonEqualLengthAscending() {
        String[] input = new String[]{
                "K2\\SK1\\SSK1",
                "K2\\SK10\\SSK1",
        };
        String[] expected = new String[]{
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK1",
                "K2\\SK10",
                "K2\\SK10\\SSK1"
        };
        this.execute(input, expected, false);
    }

    @Test
    public void whenUntypicalAscending() {
        String[] input = new String[]{
                "K2\\SK1\\SSK10",
                "K2\\SK10",
        };

        String[] expected = new String[]{
                "K2",
                "K2\\SK1",
                "K2\\SK1\\SSK10",
                "K2\\SK10"
        };
        this.execute(input, expected, false);
    }


    @Test
    public void whenUntypicalDescending() {
        String[] input = new String[]{
                "K2\\SK1\\SSK10",
                "K2\\SK11",
        };
        String[] expected = new String[]{
                "K2",
                "K2\\SK11",
                "K2\\SK1",
                "K2\\SK1\\SSK10"
        };
        this.execute(input, expected, true);
    }
}