package ru.job4j.condition;

/**
 * Класс Point, описывает точку в системе координат.
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 13.09.2018
 */

public class Point {
    /** x-координата */
    private int x;
    /** y-координата */
    private int y;

    /**
     * @param x х-координата
     * @param y y-координата
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Вычисляет расстояние до заданной точки.
     * @param that точка, расстояние до кот. надо вычислить
     * @return вычисленное расстояние
     */
    public double distanceTo(Point that) {
        return Math.sqrt(
                Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2)
        );
    }
}
