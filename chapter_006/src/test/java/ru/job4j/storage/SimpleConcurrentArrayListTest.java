package ru.job4j.storage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Потокобезопасный динамический список. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-21
 */
public class SimpleConcurrentArrayListTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private SimpleConcurrentArrayList<Integer> numbers = new SimpleConcurrentArrayList<>();

    @Test
    public void whenAddFromTwoThreadThenSizeIncrementsCorrect() throws InterruptedException {
        MutatorThread<Boolean> first = new MutatorThread<>(numbers -> numbers.add(11));
        MutatorThread<Boolean> second = new MutatorThread<>(numbers -> numbers.add(12));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(numbers.size(), is(2));
        assertTrue(first.result);
        assertTrue(second.result);
    }

    @Test
    public void whenGetFromTwoThreadThenReturnsCorrectNum() throws InterruptedException {
        numbers.add(1);
        numbers.add(2);
        MutatorThread<Integer> first = new MutatorThread<>(numbers -> numbers.get(0));
        MutatorThread<Integer> second = new MutatorThread<>(numbers -> numbers.get(0));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(first.result, is(1));
        assertThat(second.result, is(1));
    }

    @Test
    public void autoExpendingStorageTest() {
        numbers = new SimpleConcurrentArrayList<>(1);
        numbers.add(0);
        numbers.add(0);
        assertThat(numbers.size(), is(2));
    }

    @Test
    public void iteratorFailSaveTest() {
        numbers.add(1);
        Iterator<Integer> iteratorA = numbers.iterator();
        numbers.add(2);
        Iterator<Integer> iteratorB = numbers.iterator();

        assertTrue(iteratorA.hasNext());
        assertThat(iteratorA.next(), is(1));
        assertFalse(iteratorA.hasNext());

        assertTrue(iteratorB.hasNext());
        assertThat(iteratorB.next(), is(1));
        assertTrue(iteratorB.hasNext());
        assertThat(iteratorB.next(), is(2));
        assertFalse(iteratorB.hasNext());

        thrown.expect(NoSuchElementException.class);
        iteratorB.next();
    }

    @Test
    public void whenRemoveInvocationThenShouldReturnExpection() {
        numbers.add(1);
        Iterator<Integer> iterator = numbers.iterator();
        iterator.next();
        thrown.expect(UnsupportedOperationException.class);
        iterator.remove();
    }


    private class MutatorThread<T> extends Thread {
        private final Function<SimpleConcurrentArrayList<Integer>, T> changer;
        T result;

        private MutatorThread(Function<SimpleConcurrentArrayList<Integer>, T> changer) {
            this.changer = changer;
        }

        @Override
        public void run() {
            result = changer.apply(numbers);
        }
    }

}
