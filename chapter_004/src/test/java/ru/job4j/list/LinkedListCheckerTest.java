package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LinkedListCheckerTest {
    private LinkedListChecker checker = new LinkedListChecker();
    private Node<Integer> first;
    private Node<Integer> second;
    private Node<Integer> third;
    private Node<Integer> four;

    @Before
    public void notCycled() {
        first = new Node<>(1);
        second = new Node<>(2);
        third = new Node<>(3);
        four = new Node<>(4);

        first.next = second;
        second.next = third;
        third.next = four;
        four.next = null;
    }

    @Test
    public void whenCycledThenTrue() {
        this.four.next = this.first;
        assertThat(this.checker.hasCycle(first), is(true));
    }

    @Test
    public void whenNotCycledThenFalse() {
        assertThat(this.checker.hasCycle(first), is(false));
    }

    @Test
    public void whenFirstElementShortCutThenTrue() {
        this.first.next = this.first;
        assertThat(this.checker.hasCycle(first), is(true));
    }

    @Test
    public void whenLastElementShortCutThenTrue() {
        this.four.next = this.four;
        assertThat(this.checker.hasCycle(first), is(true));
    }

    @Test
    public void whenIntermediateElementShortCutThenTrue() {
        this.second.next = this.second;
        assertThat(this.checker.hasCycle(first), is(true));
    }

    @Test
    public void whenIntermediateElementCycledThenTrue() {
        this.third.next = this.second;
        assertThat(this.checker.hasCycle(first), is(true));
    }

    @Test
    public void whenShortAcyclicSequenceThenFalse() {
        assertThat(this.checker.hasCycle(second), is(false));
    }

    @Test
    public void whenOneElementNullNextThenFalse() {
        assertThat(this.checker.hasCycle(this.four), is(false));
    }

}
