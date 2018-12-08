package ru.job4j.tree;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Элементарная структура дерева. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-22
 */
public class TreeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Tree<Integer> tree = new Tree<>(1);
    private Iterator<Integer> iterator;

    @Before
    public void prepare() {
        this.tree = new Tree<>(1);
        this.tree.add(1, 2);
        this.tree.add(1, 3);
        this.tree.add(1, 4);
        this.tree.add(4, 5);
        this.tree.add(5, 6);
        this.iterator = this.tree.iterator();
    }

    @Test
    public void when6ElFindLastThen6() {
        assertThat(
                this.tree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExistThenOptionEmpty() {
        assertThat(
                this.tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenDuplicateAddedHasNextShouldNotThrowCME() {
        this.tree.add(3, 1);
        assertThat(this.iterator.hasNext(), is(true));
    }

    @Test
    public void testIterator() {
        assertThat(this.iterator.hasNext(), is(true));
        assertThat(this.iterator.hasNext(), is(true));
        assertThat(this.iterator.next(), is(1));
        assertThat(this.iterator.next(), is(2));
        assertThat(this.iterator.next(), is(3));
        assertThat(this.iterator.next(), is(4));
        assertThat(this.iterator.next(), is(5));
        assertThat(this.iterator.next(), is(6));
        assertThat(this.iterator.hasNext(), is(false));
        assertThat(this.iterator.hasNext(), is(false));
        this.thrown.expect(NoSuchElementException.class);
        this.iterator.next();

    }

    @Test
    public void whenNewElAddedNextInvocationShouldThrowCMW() {
        this.tree.add(5, 7);
        this.thrown.expect(ConcurrentModificationException.class);
        this.iterator.next();
    }

    @Test
    public void whenNewElAddedHasNextInvocationShouldThrowCMW() {
        this.tree.add(5, 7);
        this.thrown.expect(ConcurrentModificationException.class);
        if (this.iterator.hasNext()) {
            this.iterator.next();
        }
    }

    @Test
    public void whenChildrenAmountIsThreeThenIsBinaryReturnsFalse() {
        assertThat(this.tree.isBinary(), is(false));
    }

    @Test
    public void whenChildrenAmountTwoOrLessThenIsBinaryReturnsTrue() {
        this.tree = new Tree<>(0);
        this.tree.add(0, 1);
        this.tree.add(0, 2);
        this.tree.add(1, 3);
        this.tree.add(1, 4);
        this.tree.add(2, 5);
        this.tree.add(2, 6);
        this.tree.add(4, 7);
        this.tree.add(5, 8);
        assertThat(this.tree.isBinary(), is(true));
    }
}