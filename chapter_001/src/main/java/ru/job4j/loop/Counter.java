package ru.job4j.loop;

/**
 * Подсчет суммы чётных чисел в диапазоне.
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-14
 */
public class Counter {
    /**
     * @param start начало диапазона
     * @param finish конец диапазона
     * @return сумма чётных чисел в заданном диапазоне (включительно)
     */
    public int add(int start, int finish) {
        int result = 0;
        for (int index = start; index <= finish; index++) {
            if (index % 2 == 0) {
                result += index;
            }
        }
        return result;
    }
}