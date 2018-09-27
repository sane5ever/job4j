package ru.job4j.array;

/**
 * 7.1 Объединить два отсортированных массива.
 */
public class MergeArrays {
    /**
     *
     * @param first первый отсортированный массив
     * @param second второй отсортированный массив
     * @return отсортированный массив, полученный слиянием входящих
     */
    public int[] merge(int[] first, int[] second) {
        int[] result = new int[first.length + second.length];
        for (
                int firstIndex = 0, secondIndex = 0, thirdIndex = 0;
                thirdIndex != result.length;
                thirdIndex++
        ) {
            if (firstIndex == first.length) {
                result[thirdIndex] = second[secondIndex++];
            } else if (secondIndex == second.length || first[firstIndex] < second[secondIndex]) {
                result[thirdIndex] = first[firstIndex++];
            } else {
                result[thirdIndex] = second[secondIndex++];
            }
        }
        return result;
    }
}