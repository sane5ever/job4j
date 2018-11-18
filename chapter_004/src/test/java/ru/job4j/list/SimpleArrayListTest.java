package ru.job4j.list;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Динамический список на базе массива. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-18
 */
public class SimpleArrayListTest {
    private SimpleArrayList<Integer> numbers;
    private Iterator<Integer> iterator;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void prepare() {
        this.numbers = new SimpleArrayList<>();
        IntStream.range(0, 10).forEach(this.numbers::add);
        this.iterator = this.numbers.iterator();
    }

    @Test
    public void whenAddThreeElementsShouldBeAddedInSameOrder() {
        assertThat(this.numbers.get(0), is(0));
        assertThat(this.numbers.get(1), is(1));
        assertThat(this.numbers.get(2), is(2));
    }

    @Test
    public void whenAddThreeElementsWithStartCapacityThreeThenAddMoreElementsShouldBeAvailable() {
        assertThat(this.numbers.add(0), is(true));
        assertThat(this.numbers.get(10), is(0));
    }

    @Test
    public void sequentialHasNextInvocationDoesNotAffectRetrievalOrder() {
        assertThat(this.iterator.hasNext(), is(true));
        assertThat(this.iterator.hasNext(), is(true));
        IntStream.range(0, 10).forEach(i -> assertThat(this.iterator.next(), is(i)));
        assertThat(this.iterator.hasNext(), is(false));
        assertThat(this.iterator.hasNext(), is(false));
    }

    @Test
    public void hasNextNextSequentialInvocation() {
        IntStream.range(0, 10).forEach(i -> {
            assertThat(this.iterator.hasNext(), is(true));
            assertThat(this.iterator.next(), is(i));
        });
        assertThat(this.iterator.hasNext(), is(false));
    }

    @Test
    public void whenRemoveElementsSequentially() {
        this.numbers = new SimpleArrayList<>(3);
        IntStream.range(0, 3).forEach(this.numbers::add);
        this.iterator = this.numbers.iterator();
        this.iterator.next();
        this.iterator.remove();
        assertThat(this.numbers.get(0), is(1));
        this.iterator.next();
        this.iterator.remove();
        assertThat(this.numbers.get(0), is(2));
        this.iterator.next();
        this.iterator.remove();
        this.thrown.expect(UnsupportedOperationException.class);
        this.iterator.remove();
    }

    @Test
    public void whenInitSizeIsNegativeExceptionShouldBeThrown() {
        thrown.expect(IllegalArgumentException.class);
        new SimpleArrayList<>(-1);
    }

    @Test
    public void whenIndexOutOfBoundGetInvocation() {
        this.thrown.expect(IndexOutOfBoundsException.class);
        this.numbers.get(11);
    }

    @Test
    public void whenNegativeIndexGetInvocation() {
        this.thrown.expect(IndexOutOfBoundsException.class);
        this.numbers.get(-1);
    }

    @Test
    public void whenRemoveInvocationWithoutGetNextExceptionShouldBeThrown() {
        this.thrown.expect(UnsupportedOperationException.class);
        this.iterator.remove();
    }


    @Test
    public void whenIteratorHasNotNextExceptionShouldBeThrown() {
        IntStream.range(0, 10).forEach(i -> this.iterator.next());
        this.thrown.expect(NoSuchElementException.class);
        this.iterator.next();
    }

    @Test
    public void whenNextInvocationAfterStateModificationExceptionShouldBeThrown() {
        this.numbers.add(0);
        this.thrown.expect(ConcurrentModificationException.class);
        this.iterator.next();
    }

    @Test
    public void whenRemoveInvocationAfterStateModificationExceptionShouldBe() {
        this.iterator.next();
        this.numbers.add(0);
        this.thrown.expect(ConcurrentModificationException.class);
        this.iterator.remove();
    }
}
