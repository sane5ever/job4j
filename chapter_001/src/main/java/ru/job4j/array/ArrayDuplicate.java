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
     * @param array массив строк
     * @return входящий массив без повторяющихся элементов
     */
    public String[] remove(String[] array) {
        int cutoff = array.length;
        for (int i = 0; i < cutoff; i++) {
            for (int j = i + 1; j < cutoff; j++) {
                if (array[i].equals(array[j])) {
                    cutoff--;
                    String temp = array[j];
                    array[j] = array[cutoff];
                    array[cutoff] = temp;
                    j--;
                }
            }
        }
        return Arrays.copyOf(array, cutoff);
    }
}