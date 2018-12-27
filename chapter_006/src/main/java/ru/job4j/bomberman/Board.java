package ru.job4j.bomberman;

import ru.job4j.bomberman.entities.Entity;
import ru.job4j.bomberman.entities.Player;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * Игровое поле.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-27
 */
public class Board {
    /**
     * Продолжительность попытки занять новую ячейку по-умолчанию.
     */
    private final static long TIMEOUT = 500;
    /**
     * Продолжительность одного автоматического хода.
     */
    private final static int DEFAULT_STEP_DURATION = 1000;
    /**
     * Размер матрицы игрового поля по-умолчанию.
     */
    private final static int DEFAULT_LENGTH = 10;
    /**
     * Размер матрицы игрового поля.
     */
    private final int size;
    /**
     * Скорость совершения автоматического хода.
     */
    private final int speed;
    /**
     * Нить, эмулирующая поведение игрока.
     */
    private final Entity player;
    /**
     * Игровое поле, представляет собой двухмерный массив ячеек.
     */
    private final Cell[][] board;
    /**
     * Флаг, определяющий начало игры (в случае сброса текущей игры).
     * Используется для проверки необходимости очищать поле перед заполнением.
     */
    private boolean firstStart = true;

    /**
     * Формирует новое игровое поле, представляющее квадратную матрицу размера по-умолчанию.
     */
    public Board() {
        this(DEFAULT_LENGTH, DEFAULT_STEP_DURATION);
    }

    /**
     * Формирует новое игровое поле, представляющее квадратную матрицу заданного размера.
     *
     * @param size заданный размер
     */
    public Board(int size, int speed) {
        this.size = size;
        this.speed = speed;
        this.board = new Cell[size][size];
        this.player = new Player(this);
    }

    /**
     * Возвращает скорость совершения автоматического хода.
     *
     * @return скорость совершения автоматического хода
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Основной метод для запуска игры.
     * Запускает заполнение поля и игровые персонажи на старте.
     */
    public void init() {
        refresh();
        player.start();
        firstStart = false;
    }

    /**
     * Устанавливает начальные параметры игровых персонажей.
     * При необходимости осуществляет очистку и повторное заполнение поля.
     */
    public void refresh() {
        if (!firstStart) {
            this.stop();
        }
        IntStream.range(0, size).forEach(
                i -> IntStream.range(0, size).forEach(
                        j -> board[i][j] = new Cell(i, j)
                )
        );
    }

    /**
     * Осуществляет остановку потоков игровых персонажей.
     * Необходимо вызывать при завершении игры.
     */
    public void stop() {
        player.interrupt();
        firstStart = true;
    }

    /**
     * Производит переход с текущего поля на заданное следующее.
     * При этом происходит разблокировка первого и блокировка второго.
     * При невозможности совершения хода (ячейка уже занята)
     * <p>
     * Используется потоками игровых персонажей при работе.
     *
     * @param source текущее поле
     * @param dist   следующее поле
     * @return <tt>true</tt>, если ход возможен и совершён
     */
    public boolean move(Cell source, Cell dist) throws InterruptedException {
        boolean isLock;
        ReentrantLock next = board[dist.x][dist.y].lock;
        isLock = next.tryLock() || next.tryLock(TIMEOUT, TimeUnit.MILLISECONDS);
        if (isLock) {
            board[source.x][source.y].lock.unlock();
        }
        return isLock;
    }

    /**
     * Выбирает произвольную ячейку для совершения хода.
     * Эта ячейка является соседней (вправо, влево, вверх или вниз) для заданной.
     * Осуществляется проверка невыхода за пределы поля. При необходимости выбор осуществляется повторно.
     *
     * @param current ячейка, с кот. совершается ход
     * @return соседняя с заданной ячейка поля
     */
    public Cell getCell(Cell current) {
        int x = current.x;
        int y = current.y;
        do {
            double num = ThreadLocalRandom.current().nextDouble();
            if (num < 0.5) {
                x += num < 0.25 ? 1 : -1;
            } else {
                y += num < 0.75 ? 1 : -1;
            }
        } while (x < 0 || y < 0 || x >= size || y >= size);
        return board[x][y];
    }

    /**
     * Возвращает произвольную (unlocked) ячейку поля.
     * Осуществляется её блокировка нитью персонажа.
     *
     * @return random unlocked cell
     */
    public Cell getStartPosition() {
        Cell result;
        do {
            int i = ThreadLocalRandom.current().nextInt(size);
            int j = ThreadLocalRandom.current().nextInt(size);
            result = board[i][j];
        } while (result.lock.isLocked());
        result.lock.lock();
        return result;
    }

    /**
     * Класс, описывающий игровую ячейку поля.
     */
    public static class Cell {
        private final int x;
        private final int y;

        private final ReentrantLock lock;

        private Cell(int x, int y) {
            this.x = x;
            this.y = y;
            this.lock = new ReentrantLock();
        }
    }
}
