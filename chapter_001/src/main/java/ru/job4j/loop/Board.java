package ru.job4j.loop;

/**
 * Построение шахматной доски в псевдографике.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-14
 */
public class Board {
    /**
     * @param width ширина шахматной доски
     * @param height высота шахматной доски
     * @return шахматная доска в виде строки
     */
    public String paint(int width, int height) {
        StringBuilder screen = new StringBuilder();
        final String line = System.lineSeparator();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i + j) % 2 == 0) {
                    screen.append("x");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(line);
        }
        return screen.toString();
    }
}
