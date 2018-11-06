package ru.job4j.review;

import java.util.*;
import java.util.stream.*;

/**
 * Provides transfer values from matrix to List Collection and back.
 */
public class Converter {
    /**
     * Converts two dimensional array to List.
     *
     * @param array int matrix
     * @return list
     */
    public List<Integer> matrixToList(int[][] array) {
        return Arrays.stream(array)
                .flatMapToInt(Arrays::stream)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Converts List to array.
     *
     * @param list int List
     * @param rows rows
     * @return matrix
     */
    public int[][] listToMatrix(List<Integer> list, int rows) {
        Iterator<Integer> iterator = list.iterator();
        int columns = Math.max(1, (list.size() + rows - 1) / rows);
        final int[][] result = new int[rows][columns];
        IntStream.range(0, rows).forEach(
                line -> IntStream.range(0, columns)
                        .forEach(index ->
                                result[line][index] = iterator.hasNext()
                                        ? iterator.next()
                                        : 0
                        ));
        return result;
    }
}