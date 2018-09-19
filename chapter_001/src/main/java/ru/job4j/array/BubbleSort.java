package ru.job4j.array;

/**
 * 6.5. Создать программу для сортировки массива методом перестановки
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-19
 */
public class BubbleSort {
    public int[] sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = 1; j <= array.length - i; j++) {
                if (array[j - 1] > array[j]) {
                    int temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }
}