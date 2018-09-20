package ru.job4j.array;

/**
 * 6.7. Квадратный массив заполнен true или false по диагоналям.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-19
 */
public class MatrixCheck {
    /**
     *
     * @param data матрица с boolean-значениями
     * @return true, если каждая диагональ заполнена одинаковыми элементами
     */
    public boolean mono(boolean[][] data) {
        int last = data.length - 1;
        boolean result = true;
        for (int index = 1; index <= last; index++) {
            if (data[0][0] != data[index][index] || data[0][last] != data[index][last - index]) {
                result = false;
                break;
            }
        }
        return result;
    }
}