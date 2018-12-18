package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class SingleLinkedListTest {

    private SingleLinkedList<Integer> list;

    @Before
    public void prepare() {
        this.list = new SingleLinkedList<>();
        this.list.add(1);
        this.list.add(2);
        this.list.add(3);
    }

    @Test
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(this.list.get(1), is(2));
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(this.list.getSize(), is(3));
    }

    @Test
    public void whenAddThreeElementsThenUseDeleteThenGetSizeResultTwo() {
        this.list.delete();
        assertThat(this.list.getSize(), is(2));
    }

    @Test
    public void whenAddThreeElementsThenUseDeleteTwoTimesThenGetZeroResultThree() {
        this.list.delete();
        this.list.delete();
        assertThat(this.list.get(0), is(1));
    }

    @Test
    public void whenAddThreeElementsThenUseDeleteFourTimesThenResultNullAndSizeZero() {
        this.list.delete();
        this.list.delete();
        this.list.delete();
        assertThat(this.list.getSize(), is(0));
        assertNull(this.list.delete());
    }
}
