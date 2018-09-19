package ru.job4j.array;

/**
 * 6.2. Перевернуть массив.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-19
 */
public class Turn {
    /**
     * переворачиваем элементы массива без использования доп. массива
     * @param array заданный массив чисел
     * @return массив с переставленными наоборот элементами заданного
     */
    public int[] back(int[] array) {
        for (int index = 0; index < array.length / 2; index++) {
            int mirrorIndex = array.length - index - 1;
            int temp = array[index];
            array[index] = array[mirrorIndex];
            array[mirrorIndex] = temp;
        }
        return array;
    }
}
