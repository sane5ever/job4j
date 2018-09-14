package ru.job4j.loop;

/**
 * Построение пирамиды в псевдографике.
 * 
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-14
 */
public class Paint {
    /**
     * @param height высота треугольника
     * @return правосторонний треугольник в виде строки
     */
    public String rightTrl(int height) {
        StringBuilder screen = new StringBuilder();
        int weight = height;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (row >= column) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }

        return screen.toString();
    }

    /**
     * @param height высота треугольника
     * @return левосторонний треугольник в виде строки
     */
    public String leftTrl(int height) {
        StringBuilder screen = new StringBuilder();
        int weight = height;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (row >= weight - column - 1) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }

    /**
     * @param height высота пирамиды-треугольника
     * @return пирамида-треугольник в виде строки
     */
    public String pyramid(int height) {
        StringBuilder screen = new StringBuilder();
        int weight = 2 * height - 1;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (row >= height - column - 1 && row + height - 1 >= column) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }

}
