package ru.job4j.bomberman.characters;

import ru.job4j.bomberman.Board;
import ru.job4j.bomberman.Destination;

import java.util.concurrent.BlockingQueue;

/**
 * Игрок.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-27
 */
public class Player extends GameCharacter {
    /**
     * Хранилище последующих ходов игрока.
     */
    private final BlockingQueue<Destination> moveQueue;

    /**
     * Создаёт новую нить, в кот. происходит обработка ходов игрока.
     * Связывает его с заданным игровым полем.
     * Устанавливает хранилище с ходами, согласно кот. необходимо совершать ходы.
     *
     * @param board     игровое поле
     * @param name      имя нити (для удобства отладки)
     * @param moveQueue очередь, в кот. приходят направления движений для ходов игрока
     */
    public Player(Board board, String name, BlockingQueue<Destination> moveQueue) {
        super(board, name);
        this.moveQueue = moveQueue;
    }

    /**
     * Описывает логику совершения одиночного хода игроком.
     * Направление движения забирается из установленной блокирующей очереди.
     * Если ход не совершён — возврашается текущая позиция.
     *
     * @param position текущая позиция
     * @return позиция, на кот. совершён ход
     * @throws InterruptedException в случае прерывания нити во время хода
     */
    @Override
    Board.Cell singleMove(Board.Cell position) throws InterruptedException {
        Destination dest = moveQueue.take();
        Board.Cell fresh = board.getNextCell(position, dest);
        return board.move(position, fresh) ? fresh : position;
    }
}
