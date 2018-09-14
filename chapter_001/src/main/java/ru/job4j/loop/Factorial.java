package ru.job4j.loop;

/**
 * Вычисление факториала заданного числа.
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-14
 */
public class Factorial {
    /**
     *
     * @param n положительное целое число
     * @return n-факториал
     */
    public int calc(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
