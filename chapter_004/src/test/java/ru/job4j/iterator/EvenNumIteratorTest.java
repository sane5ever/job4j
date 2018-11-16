package ru.job4j.iterator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Итератор четные числа. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-16
 */
public class EvenNumIteratorTest {
    private Iterator it;

    public void init() {
        this.it = new EvenNumIterator(new int[]{4, 2, 1, 1});
    }

    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation() {
        init();
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(2));
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        init();
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(false));
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void hasNextNextSequentialInvocation() {
        init();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(false));
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void hasNextNextInvocationWhenEmptyInputArray() {
        this.it = new EvenNumIterator(new int[0]);
        assertThat(it.hasNext(), is(false));
        this.thrown.expect(NoSuchElementException.class);
        this.it.next();
    }

}
