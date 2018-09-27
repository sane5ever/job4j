package ru.job4j.max;

/**
 * Максимум из двух чисел.
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.1 2018-09-14
 * @since 13.09.2018
 */
public class Max {
    /**
     * @param first первое число
     * @param second второе число
     * @return возвращает большее из заданных чисел
     */
    public int max(int first, int second) {
        return first > second ? first : second;
    }

    /**
     * @param first первое число
     * @param second второе число
     * @param third третье число
     * @return большее из заданных чисел
     */
    public int max(int first, int second, int third) {
        return max(
                max(first, second), third
        );
    }
}
