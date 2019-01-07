package ru.job4j.bomberman;

/**
 * Возможные направления движения.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-01-07
 */
public enum Destination {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    WRITE(1, 0);
    /**
     * Смещение по горизонтали при текущем направлении.
     */
    public final int dx;
    /**
     * Смещение по вертикали при текущем направлении.
     */
    public final int dy;

    Destination(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}