package ru.job4j.bomberman.entities;

import ru.job4j.bomberman.Board;

/**
 * Монстр.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-27
 */
public class Monster extends Entity {
    public Monster(Board.Cell position, Board board) {
        super(board);
    }
}
