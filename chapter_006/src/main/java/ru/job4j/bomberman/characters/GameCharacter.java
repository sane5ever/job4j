package ru.job4j.bomberman.characters;

import ru.job4j.bomberman.Board;

/**
 * Игровой персонаж.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-27
 */
public abstract class GameCharacter extends Thread {
    /**
     * Игровое поле, по кот. осуществляется движение персонажа.
     */
    final Board board;

    /**
     * Инициализирует игровой персонаж.
     * Связывает его с заданным игровым полем.
     *
     * @param board игровое поле
     * @param name  имя нити (для удобства отладки)
     */
    public GameCharacter(Board board, String name) {
        super(name);
        this.board = board;
    }

    /**
     * Поток, обеспечивает совершение ходов игровым персонажем.
     */
    @Override
    public void run() {
        for (
                Board.Cell position = board.getStartPosition();
                board.isAlive();
                board.checkIntersect(position)
        ) {
            try {
                position = singleMove(position);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Метод для реализации подклассами.
     * Должен описывать логику совершения одиночного хода игрового персонажа.
     * <p>
     * Возвращает ячейку, на кот. переместился игровой персонаж в процессе хода.
     * Используется для обновления текущей позиции персонажа в методе {@link #run()}.
     *
     * @param position текущая позиция
     * @return позиция, на кот. совершён ход
     * @throws InterruptedException в случае прерывания нити во время хода
     */
    abstract Board.Cell singleMove(Board.Cell position) throws InterruptedException;
}