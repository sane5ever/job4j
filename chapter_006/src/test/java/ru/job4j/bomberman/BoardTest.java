package ru.job4j.bomberman;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Игровое поле. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-27
 */
public class BoardTest {

    private void execute(int size, int mosterAmount, boolean isGamerAllowed, int falseCounter) throws InterruptedException {
        Map<Boolean, Integer> map = new ConcurrentHashMap<>();
        Board board = new Board(size, 5, mosterAmount) {
            @Override
            public boolean move(Cell source, Cell dist) throws InterruptedException {
                boolean result = super.move(source, dist);
                map.compute(
                        result,
                        (k, v) -> v == null ? 0 : ++v
                );

                return result;
            }
        };
        long begin = System.currentTimeMillis();
        board.start();

        while (
                !map.containsKey(false)
                        || map.get(false) < falseCounter
                        || (isGamerAllowed && !map.containsKey(true))   // map must collect true if only mainTest() execute
        ) {
            if (isGamerAllowed) {
                board.addNewStep(Destination.values()[ThreadLocalRandom.current().nextInt(4)]);
            }
            Thread.sleep(10);
        }

        board.stop();

        long duration = System.currentTimeMillis() - begin;
        int delta = 100; //погрешность

        assertThat(
                duration,
                lessThan(delta + 500L * map.get(false) + 5L * map.getOrDefault(true, 0))
        );
        assertFalse(!isGamerAllowed && map.containsKey(true));
    }


    @Test
    public void mainTest() throws InterruptedException {
        execute(3, 1, true, 5);
    }

    @Test
    public void whenNoEmptyCellsThenResultOnlyFalse() throws InterruptedException {
        execute(2, 4, false, 2);
    }

    @Test   // for 100% test coverage only
    public void initTest() {
        Board board = new Board();
        board.start();
        board.stop();
        assertThat(board.getSpeed(), is(1000));
    }
}
