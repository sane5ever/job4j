package ru.job4j.array;

import java.util.function.Predicate;

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
     * @return true, если главная диагональ состоят из идентичных эл-тов, иначе проверяется на то же побочная
     */
    public boolean mono(boolean[][] data) {
        int last = data.length - 1;
        return this.check(
                last,
                (index) -> data[0][0] != data[index][index]
        )
                ||
                this.check(
                        last,
                        (index) -> data[0][last] != data[index][last - index]
                );
    }

    /**
     *
     * @param last индекс крайнего элемента в матрице
     * @param predicate условие: текущий элемент диагонали не равен нулевому
     * @return true, если все элементы равны нулевому
     */
    private boolean check(int last, Predicate<Integer> predicate) {
        boolean result = true;
        for (int index = 1; index <= last; index++) {
            if (predicate.test(index)) {
                result = false;
                break;
            }
        }
        return result;
    }
}