package ru.job4j.array;

import java.util.Arrays;

/**
 * 6.8. Удаление дубликатов в массиве.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-20
 */
public class ArrayDuplicate {
    /**
     *
     * @param array массив строк
     * @return входящий массив без повторяющихся элементов
     */
    public String[] remove(String[] array) {
        int counter = array.length;
        for (int i = 0; i < counter; i++) {
            for (int j = i + 1; j < counter; j++) {
                if (array[i].equals(array[j])) {
                    counter--;
                    String temp = array[j];
                    array[j] = array[counter];
                    array[counter] = temp;
                    j--;
                }
            }
        }
        return Arrays.copyOf(array, counter);
    }
}