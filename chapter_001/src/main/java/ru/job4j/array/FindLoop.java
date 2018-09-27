package ru.job4j.array;

/**
 * 6.1. Классический поиск перебором.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-19
 */
public class FindLoop {
    /**
     * @param data данный массив
     * @param el искомое в массиве значение
     * @return индекс искомого элемента массива, либо -1 при его отсутствии
     */
    public int indexOf(int[] data, int el) {
        int result = -1;
        for (int index = 0; index != data.length; index++) {
            if (data[index] == el) {
                result = index;
                break;
            }
        }
        return result;
    }
}
