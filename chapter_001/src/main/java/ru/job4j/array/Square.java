package ru.job4j.array;

/**
 * 6.0 Заполнить массив степенями чисел.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-18
 */
public class Square {
    /**
     * @param bound длина массива
     * @return массив, заполненный элементами от 1 до bound, возведёнными в квадрат
     */
    public int[] calculate(int bound) {
        int[] result = new int[bound];
        for (int index = 1; index != bound; index++) {
            result[index - 1] = index * index;
        }
        return result;
    }

}
