package ru.job4j.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Конвертация двумерного массива в ArrayList
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-16
 */
public class ConvertMatrix2List {
    /**
     * Конвертируем двумерный массив в список.
     *
     * @param array матрица чисел
     * @return список чисел
     */
    public List<Integer> toList(int[][] array) {
        List<Integer> result = new ArrayList<>();
        for (int[] row : array) {
            for (int num : row) {
                result.add(num);
            }
        }
        return result;
    }

    /**
     * Объединяем список массивов в единый список.
     *
     * @param list список массимов чисел
     * @return список, заполненный числами из массивов
     */
    public List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for (int[] array : list) {
            for (Integer num : array) {
                result.add(num);
            }
        }
        return result;
    }
}