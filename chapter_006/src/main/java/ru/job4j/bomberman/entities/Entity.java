package ru.job4j.bomberman.entities;

import ru.job4j.bomberman.Board;

/**
 * Игровой персонаж.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-27
 */
public abstract class Entity extends Thread {
    /**
     * Игровое поле, по кот. осуществляется движение персонажа.
     */
    private final Board board;

    /**
     * Инициализирует игровой персонаж.
     * Связывает его с заданным игровым полем.
     *
     * @param board игровое поле
     */
    public Entity(Board board) {
        this.board = board;
    }

    /**
     * Поток, обеспечивает совершение ходов персонажем игры.
     */
    @Override
    public void run() {
        Board.Cell position = board.getStartPosition();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Board.Cell fresh;
                do {
                    fresh = board.getCell(position);
                } while (!board.move(position, fresh));
                position = fresh;
                Thread.sleep(board.getSpeed());
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}