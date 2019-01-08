package ru.job4j.bomberman;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
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
    public void mainTest() {
        execute(10, 50, 20, true, 10);
    }

    @Test
    public void additionalTest() {
        execute(10, 10, 50, false, 100);
    }

    @Test
    public void testWhen1x1Matrix() {
        execute(1, 10, 0, true, 10);
    }

    @Test
    public void testWhenNoEmptyCells() {
        execute(3, 20, 7, false, 10);
    }

    @Test
    public void initTest() {
        Board board = new Board();
        board.start();
        board.stop();
        assertThat(board.getSpeed(), is(1000));
    }

    private void execute(
            int size, int speed, int monsterAmount, boolean isGamerAllowed, int falseCounter
    ) {
        Map<Boolean, Integer> map = new ConcurrentHashMap<>();
        Board board = new Board(size, speed, monsterAmount, speed / 2) {
            @Override
            public boolean move(Cell source, Cell dest) throws InterruptedException {
                var result = super.move(source, dest);
                map.compute(
                        result,
                        (k, v) -> v == null ? 0 : ++v
                );
                return result;
            }
        };

        Thread mockViewThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (isGamerAllowed) {
                    board.addNewStep(
                            Destination.values()[ThreadLocalRandom.current().nextInt(4)]
                    );
                }
                try {
                    Thread.sleep(speed / 3);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        var begin = System.currentTimeMillis();
        mockViewThread.start();

        Thread controllerThread = new Thread(board::start);
        controllerThread.start();

        //board.start();

        while (board.isAlive()) {
            if (
                    map.containsKey(false)
                            && map.get(false) >= falseCounter
                            && (!isGamerAllowed || map.containsKey(true) || size == 1)
            ) {
                controllerThread.interrupt();
                mockViewThread.interrupt();
                break;
            }

        }

        var duration = System.currentTimeMillis() - begin;
        var delta = 50L; //погрешность

        assertThat(
                duration,
                lessThan(delta + speed / 2 * map.getOrDefault(false, 0) + speed * map.getOrDefault(true, 0))
        );
    }

}
