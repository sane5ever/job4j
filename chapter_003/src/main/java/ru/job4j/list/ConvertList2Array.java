package ru.job4j.list;

import java.util.List;

/**
 * Конвертация ArrayList в лвухмерный массив.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-16
 */
public class ConvertList2Array {
    /**
     * Конвертируем список в двухмерный массив c заданным кол-вом строк.
     * Предусмотрены ситуации, когда кол-во строк превышает кол-во эл-тов в списке,
     * и когда передаётся список нулевого размера.
     *
     * @param list список
     * @param rows требуемое кол-во строк в массиве (больше нуля)
     * @return массив
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int columns = Integer.max(1, list.size() / rows);
        columns += rows * columns < list.size() ? 1 : 0;
        int[][] result = new int[rows][columns];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (i * columns + j == list.size()) {
                    i = result.length;
                    break;
                }
                result[i][j] = list.get(i * columns + j);
            }
        }
        return result;
    }
}