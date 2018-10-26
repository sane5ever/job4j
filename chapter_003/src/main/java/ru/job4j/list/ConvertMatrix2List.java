package ru.job4j.list;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        return Arrays.stream(array)
                .flatMapToInt(Arrays::stream)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Объединяем список массивов в единый список.
     *
     * @param list список массимов чисел
     * @return список, заполненный числами из массивов
     */
    public List<Integer> convert(List<int[]> list) {
        return list.stream()
                .flatMapToInt(Arrays::stream)
                .boxed()
                .collect(Collectors.toList());
    }
}