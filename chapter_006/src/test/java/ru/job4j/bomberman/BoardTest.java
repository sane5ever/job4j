package ru.job4j.bomberman;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

/**
 * Игровое поле. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-27
 */
public class BoardTest {
    @Test
    public void test() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        Board board = new Board(10, 10) {
            @Override
            public boolean move(Cell source, Cell dist) throws InterruptedException {
                boolean result = super.move(source, dist);
                if (counter.incrementAndGet() == 10) {
                    Thread.currentThread().interrupt();
                }
                return result;
            }
        };
        long begin = System.currentTimeMillis();
        board.init();
        while (counter.get() != 10) {
            Thread.sleep(1);
        }
        board.stop();
        long duration = System.currentTimeMillis() - begin;
        assertThat(duration, lessThan(150L));
    }
}
