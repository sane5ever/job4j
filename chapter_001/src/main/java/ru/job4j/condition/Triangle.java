package ru.job4j.condition;

/**
 * Вычисление площади треугольника.
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-14
 */
public class Triangle {
    private Point a;
    private Point b;
    private Point c;

    /**
     * @param a вершина треугольника
     * @param b вершина треугольника
     * @param c вершина треугольника
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Метод вычисляет полупериметр треугольника по длинам сторон.
     *
     * Формула: (ab + ac + bc) / 2
     *
     * @param ab расстояние между точками a и b
     * @param ac расстояние между точками a и c
     * @param bc расстояние между точками b и c
     * @return полупериметр
     */
    public double period(double ab, double ac, double bc) {
        return (ab + ac + bc) / 2;
    }

    /**
     * Метод вычисляет площадь треугольника по формуле Герона.
     *
     * @return площадь, если треугольник существует, иначе -1
     */
    public double area() {
        double result = -1D;
        double ab = this.a.distanceTo(this.b);
        double ac = this.a.distanceTo(this.c);
        double bc = this.b.distanceTo(this.c);
        double p = this.period(ab, ac, bc);
        if (this.exist(ab, ac, bc)) {
            result = Math.sqrt(
                    p * (p - ab) * (p - ac) * (p - bc)
            );
        }
        return result;
    }

    /**
     * Метод проверяет можно ли построить треугольник с такими длинами сторон.
     * Треугольник существует если сумма любых двух сторон больше третьей.
     *
     * @param ab расстояние между точками a и b
     * @param ac расстояние между точками a и c
     * @param bc расстояние между точками b и c
     * @return true, если существует, иначе false
     */
    private boolean exist(double ab, double ac, double bc) {
        return (ab + ac > bc)
                && (ab + bc > ac)
                && (ac + bc > ab);
    }
}
