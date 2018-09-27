package ru.job4j.tictactoe;

import java.util.function.Predicate;

/**
 * Отвечает за проверку логики игры.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-21
 */
public class Logic3T {
    /** матрица игрового поля */
    private final Figure3T[][] table;
    /** размер матрицы игрового поля, инициализируется из поля table автоматически при создании класса в конструкторе */
    private final int size;

    /**
     * создаёт новый объект на основе игровой матрицы, переданной в параметрах
     * @param table игровая матрица Крестиков-ноликов, в кот. методами класса проверяются условия продолжения/завершения игры
     */
    public Logic3T(Figure3T[][] table) {
        this.table = table;
        this.size = table.length;
    }

    /**
     * проверяет, победили ли Крестики
     * @return true, если в матрице есть линия, полностью заполненная Х
     */
    public boolean isWinnerX() {
        return checkAllLines(Figure3T::hasMarkX);
    }

    /** проверяет, победили ли Нолики
     * @return true, если в матрице есть линия, полностью заполненная O
     */
    public boolean isWinnerO() {
        return checkAllLines(Figure3T::hasMarkO);
    }

    /**
     * проверяет остались ли в матрице свободные ячейки
     * @return true, если есть ячейка, не помеченная X или O
     */
    public boolean hasGap() {
        boolean result = false;
        for (int y = 0; y != this.size; y++) {
            for (int x = 0; x != this.size; x++) {
                Figure3T current = this.table[y][x];
                if (!(current.hasMarkO() || current.hasMarkX())) {
                    result = true;
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    /**
     * проверяет ячейки всех возможных линий в матрице (все горизонтали и вертикали, обе диагонали) на передаваемое в параметрах условие
     * @param predicate условие: в текущей ячейке крестик/нолик?
     * @return true, если заданное условие выполняется во всех ячейках одной из линий матрицы
     */
    private boolean checkAllLines(Predicate<Figure3T> predicate) {
        boolean result = false;
        for (int index = 0; index != this.size; index++) {
            if (this.checkTheLine(index, 0, 0, 1, predicate)
                    || this.checkTheLine(0, index, 1, 0, predicate)) {
                result = true;
                break;
            }
        }
        return result
                || this.checkTheLine(0, 0, 1, 1, predicate)
                || this.checkTheLine(this.size - 1, 0, -1, 1, predicate);
    }

    /**
     * проверяет ячейки определённой параметрами линии (любая горизонталь/вертикаль/диагональ) в матрице на передаваемое в параметрах условие
     * @param x номер столбца начальной ячейки матрицы
     * @param y номер строки начальной ячейки матрицы
     * @param dx приращение по столбцам при движении по проверяемой линии
     * @param dy приразение по строкам при движении по проверяемой линии
     * @param predicate условие: в текущей ячейке крестик/нолик?
     * @return true, если заданное условие выполняется в каждой ячейке заданной линии матрицы
     */
    private boolean checkTheLine(int x, int y, int dx, int dy, Predicate<Figure3T> predicate) {
        boolean result = true;
        for (int index = 0; index != this.size; index++) {
            Figure3T current = this.table[x][y];
            if (!predicate.test(current)) {
                result = false;
                break;
            }
            x += dx;
            y += dy;
        }
        return result;
    }
}
