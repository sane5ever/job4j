package ru.job4j.generic;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;

import static org.junit.Assert.assertThat;

/**
 * Универсальная обёртка над массивом. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-17
 */
public class SimpleArrayTest {

    private SimpleArray<Integer> wrapper;
    private Iterator<Integer> it;

    @Before
    public void prepare() {
        this.wrapper = new SimpleArray<>(5);
        IntStream.range(0, 5).forEach(wrapper::add);
        this.it = this.wrapper.iterator();
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void whenArrayOverflowsShouldBeException() {
        this.thrown.expect(UnsupportedOperationException.class);
        this.wrapper.add(0);
    }

    @Test
    public void whenInvokeWrongIndexShouldBeException() {
        this.wrapper.delete(0);
        this.thrown.expect(IndexOutOfBoundsException.class);
        this.wrapper.get(4);
    }

    @Test
    public void whenInvokeNegativeIndexShouldBeException() {
        this.thrown.expect(IndexOutOfBoundsException.class);
        this.wrapper.get(-1);
    }

    @Test
    public void whenDeleteAndAddElementShouldBeDroppedAndIncluded() {
        int deleted = this.wrapper.delete(0);
        this.wrapper.add(deleted);
        assertThat(this.wrapper.get(4), is(deleted));
    }

    @Test
    public void whenSetElementShouldBeNewValue() {
        int forReplace = this.wrapper.get(4);
        this.wrapper.set(4, 0);
        assertThat(this.wrapper.get(4), not(forReplace));
        assertThat(this.wrapper.get(4), is(0));
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(0));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(false));
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void testsThatNextMethodDoesntDependsOnPriorHasNextInvocation() {
        assertThat(it.next(), is(0));
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
    }

    @Test
    public void hasNextNextSequentialInvocation() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(0));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void whenIteratorRemoveBeforeNextShouldBeException() {
        this.thrown.expect(UnsupportedOperationException.class);
        this.it.remove();
    }

    @Test
    public void whenIteratorRemoveOneByOneAfterNextCallShouldHaveNone() {
        for (int index = 0; index < 4; index++) {
            assertThat(this.wrapper.get(0), is(it.next()));
            it.remove();
            assertThat(this.wrapper.get(0), is(index + 1));
        }
        it.next();
        it.remove();
        assertThat(it.hasNext(), is(false));
        thrown.expect(NoSuchElementException.class);
        it.next();
    }
}
