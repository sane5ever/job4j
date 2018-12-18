package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleArraySetTest {
    SimpleArraySet<Integer> set;

    @Before
    public void fill() {
        this.set = new SimpleArraySet<>();
        this.set.add(1);
        this.set.add(2);
        this.set.add(3);
    }


    @Test
    public void whenContainsContainableInvocationShouldBeTrue() {
        assertThat(this.set.contains(1), is(true));
        assertThat(this.set.contains(2), is(true));
        assertThat(this.set.contains(3), is(true));
    }

    @Test
    public void whenContainsUncontainableInvocationShouldBeFalse() {
        assertThat(this.set.contains(0), is(false));
        assertThat(this.set.contains(10), is(false));
        assertThat(this.set.contains(100), is(false));
    }

    @Test
    public void whenDuplicatesAddedIteratorCounterShouldIgnoreThem() {
        this.set.add(1);
        this.set.add(2);
        this.set.add(3);
        int counter = 0;
        for (Integer element : this.set) {
            assertThat(element, is(++counter));
        }
        assertThat(counter, is(3));
    }
}
