package ru.job4j.bomberman;

import ru.job4j.bomberman.characters.GameCharacter;
import ru.job4j.bomberman.characters.Monster;
import ru.job4j.bomberman.characters.Player;

import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
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
     * Количество монстров по-умолчанию.
     */
    private final static int DEFAULT_MONSTER_AMOUNT = 2;

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
    private final GameCharacter player;
    /**
     * Множество, хранящее ссылки на нити, эмулирующие поведение монстров.
     */
    private final Set<Monster> monsters;
    /**
     * Игровое поле, представляет собой двухмерный массив ячеек.
     */
    private final Cell[][] board;
    /**
     * Очередь для хранения движений игрока.
     * Сответствуют нажатию заданных кнопок на клавиатуре.
     */
    private BlockingQueue<Destination> playerMoves;

    /**
     * Формирует новое игровое поле, представляющее квадратную матрицу размера по-умолчанию.
     */
    public Board() {
        this(DEFAULT_LENGTH, DEFAULT_STEP_DURATION, DEFAULT_MONSTER_AMOUNT);
    }

    /**
     * Формирует новое игровое поле, представляющее квадратную матрицу заданного размера.
     *
     * @param size  заданный размер
     * @param speed скорость совершения автоматического хода
     */
    public Board(int size, int speed, int monsterAmount) {
        this.size = size;
        this.speed = speed;
        this.board = new Cell[size][size];
        this.playerMoves = new LinkedBlockingQueue<>();
        this.player = new Player(this, "Player", playerMoves);
        this.monsters = IntStream.range(0, monsterAmount)
                .mapToObj(i -> new Monster(this, String.format("Monster #%s", i)))
                .collect(Collectors.toCollection(CopyOnWriteArraySet::new));
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
     *
     * Запускает заполнение поля.
     * Устанавливает блокировку произвольных полей (кол-во зависит от размера поля).
     * Запускает нити игровых персонажей.
     */
    public void start() {
        IntStream.range(0, size).forEach(
                i -> IntStream.range(0, size).forEach(
                        j -> board[i][j] = new Cell(i, j)
                )
        );
        blocksInit(size / 2);
        player.start();
        monsters.forEach(Monster::start);
    }

    /**
     * Метод вызывается Представлением при обработке событий клавиатуры.
     * Направление движения задаётся согласно установкам игровых клавиш.
     *
     * @param dest направление движения
     */
    public void addNewStep(Destination dest) {
        playerMoves.offer(dest);
    }

    /**
     * Инициализация блоков — полей, куда нельзя ходить.
     *
     * @param count количество блоков
     */
    private void blocksInit(int count) {
        IntStream.range(0, count).forEach(i -> getStartPosition());
    }

    /**
     * Осуществляет остановку потоков игровых персонажей.
     * Необходимо вызывать при завершении игры.
     */
    public void stop() {
        player.interrupt();
        monsters.forEach(Thread::interrupt);
    }

    /**
     * Производит переход с текущего поля на заданное следующее.
     * При этом происходит разблокировка первого и блокировка второго.
     * При невозможности совершения хода сразу (ячейка уже занята) ждёт заданное время,
     * <p>
     * Используется потоками игровых персонажей при работе.
     *
     * @param source текущее поле
     * @param dist   следующее поле
     * @return <tt>true</tt>, если ход возможен и совершён
     */
    public boolean move(Cell source, Cell dist) throws InterruptedException {
        boolean isLock = false;
        if (dist != null) {
            ReentrantLock next = board[dist.x][dist.y].lock;
            isLock = next.tryLock() || next.tryLock(TIMEOUT, TimeUnit.MILLISECONDS);
            if (isLock) {
                board[source.x][source.y].lock.unlock();
            }
        }
        return isLock;
    }

    /**
     * Произвольно выбирает совершаемое направление, после чего запускает получение
     * соответствующей ячейки. При выходе за границы поля выбирает направление заново.
     *
     * @param current ячейка, с кот. совершается ход
     * @return соседняя с заданной ячейка поля
     */
    public Cell getRandomNextCell(Cell current) {
        Cell result;
        do {
            Destination dest = Destination.values()
                    [ThreadLocalRandom.current().nextInt(Destination.values().length)];
            result = getNextCell(current, dest);
        } while (result == null);
        return result;
    }

    /**
     * Возвращает ячейку, соседнюю с заданной по заданному направлению.
     * При выходе за границы поля возращается <tt>null</tt>.
     *
     * @param current ячейка, с кот. совершается ход
     * @param dest    направление движения
     * @return соседняя ячейка поля в заданном направлении, либо <tt>null</tt> при её отсутствии
     */
    public Cell getNextCell(Cell current, Destination dest) {
        int x = current.x + dest.dx;
        int y = current.y + dest.dy;
        return !isOutOfBoard(x, y) ? board[x][y] : null;
    }

    /**
     * Проверяет, вышли ли координаты за пределы игрового поля.
     *
     * @param x координата по горизонтали
     * @param y координата по вертикали
     * @return <tt>true</tt>, если координаты за пределами игрового поля
     */
    private boolean isOutOfBoard(int x, int y) {
        return (x < 0 || y < 0 || x >= size || y >= size);
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
            this.lock = new ReentrantLock(true);
        }
    }
}
