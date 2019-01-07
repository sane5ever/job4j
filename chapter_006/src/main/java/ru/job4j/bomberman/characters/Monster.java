package ru.job4j.bomberman.characters;

import ru.job4j.bomberman.Board;

/**
 * Монстр.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-27
 */
public class Monster extends GameCharacter {
    /**
     * Создаёт новую нить, эмулирующую поведение игрового монстра.
     * Связывает его с заданным игровым полем.
     *
     * @param board игровое поле
     * @param name заданное имя нити (для удобства отладки)
     */
    public Monster(Board board, String name) {
        super(board, name);
    }

    /**
     * Описывает логику совершения автоматического одиночного хода монстром.
     *
     * @param position текущая позиция
     * @return позиция, на кот. совершён ход
     * @throws InterruptedException в случае прерывания нити во время хода
     */
    @Override
    Board.Cell singleMove(Board.Cell position) throws InterruptedException {
        Board.Cell fresh;
        do {
            fresh = board.getRandomNextCell(position);
        } while (!board.move(position, fresh));
        Thread.sleep(board.getSpeed());
        return fresh;
    }
}
