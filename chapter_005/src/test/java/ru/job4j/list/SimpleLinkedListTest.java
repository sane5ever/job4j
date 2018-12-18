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
 * Динамический контейнер на базе связанного списка. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-18
 */
public class SimpleLinkedListTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private SimpleLinkedList<Integer> numbers;
    private Iterator<Integer> iterator;

    @Before
    public void prepare() {
        this.numbers = new SimpleLinkedList<>();
        IntStream.range(0, 10).forEach(this.numbers::add);
        this.iterator = this.numbers.iterator();
    }

    @Test
    public void whenAddTenElementsShouldBeAddedInReverseOrder() {
        IntStream.range(0, 10).forEach(i -> assertThat(this.numbers.get(i), is(9 - i)));
    }

    @Test
    public void sequentialHasNextInvocationDoesNotAffectRetrievalOrder() {
        assertThat(this.iterator.hasNext(), is(true));
        assertThat(this.iterator.hasNext(), is(true));
        assertThat(this.iterator.hasNext(), is(true));
        IntStream.range(0, 10).forEach(i -> assertThat(this.iterator.next(), is(9 - i)));
        assertThat(this.iterator.hasNext(), is(false));
        assertThat(this.iterator.hasNext(), is(false));
    }

    @Test
    public void hasNextNextSequentialInvocation() {
        IntStream.range(0, 10).forEach(i -> {
            assertThat(this.iterator.hasNext(), is(true));
            assertThat(this.iterator.next(), is(9 - i));
        });
        assertThat(this.iterator.hasNext(), is(false));
    }

    @Test
    public void whenRemoveElementsSequentially() {
        IntStream.range(0, 9).forEach(i -> {
                    this.iterator.next();
                    this.iterator.remove();
                    assertThat(this.numbers.get(0), is(8 - i));
                }
        );
        this.iterator.next();
        this.iterator.remove();
        this.thrown.expect(UnsupportedOperationException.class);
        this.iterator.remove();
    }

    @Test
    public void whenNegativeIndexGetInvocation() {
        thrown.expect(IndexOutOfBoundsException.class);
        this.numbers.get(-1);
    }

    @Test
    public void whenIndexOutOfBoundGetInvocation() {
        thrown.expect(IndexOutOfBoundsException.class);
        this.numbers.get(11);
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
        this.iterator.next();
        this.numbers.add(10);
        this.thrown.expect(ConcurrentModificationException.class);
        this.iterator.remove();
    }

    @Test
    public void deleteEveryThirdElementDoesNotAffectRetrievalOrder() {
        IntStream.range(0, 3).forEach(i -> {
                    this.iterator.next();
                    this.iterator.next();
                    this.iterator.remove();
                    assertThat(this.iterator.next(), is(7 - i * 3));
                }
        );
    }


}
