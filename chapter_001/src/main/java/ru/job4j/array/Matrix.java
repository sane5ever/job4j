package ru.job4j.array;

/**
 * 6.6. Двухмерный массив. Таблица умножения.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-19
 */
public class Matrix {
    /**
     * формирует и возвращает значения перемножений чисел от 1 до size в виде числовой матрицы
     * @param size размер выходной матрицы
     * @return таблица умножения
     */
    public int[][] multiple(int size) {
        int[][] table = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                table[row][column] = (row + 1) * (column + 1);
            }
        }
        return table;
    }
}
