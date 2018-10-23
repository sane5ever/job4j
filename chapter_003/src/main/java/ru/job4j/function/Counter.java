package ru.job4j.function;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Подсчет функции в диапазоне.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-21
 */
public class Counter {
    /**
     * Считаем фукнцию в диапазоне
     *
     * @param start    начало диапазона
     * @param end      конец диапазона
     * @param function реадизация функции
     * @return значения фукнции в диапазоне
     */
    public List<Double> diapason(int start, int end, Function<Double, Double> function) {
        List<Double> result = new ArrayList<>();
        double current = start;
        while (current < end) {
            result.add(function.apply(current));
            current++;
        }
        return result;
    }

    /**
     * Линейная фунцкия.
     *
     * @param start начало лиапазона
     * @param end   конец диапазона
     * @return значения функции
     */
    public List<Double> linear(int start, int end) {
        return this.diapason(start, end, aDouble -> aDouble);
    }

    /**
     * Квадратичная фунцкия.
     *
     * @param start начало лиапазона
     * @param end   конец диапазона
     * @return значения функции
     */
    public List<Double> quadratic(int start, int end) {
        return this.diapason(start, end, aDouble -> aDouble * aDouble);
    }

    /**
     * Логарифмическая фунцкия.
     *
     * @param start начало лиапазона
     * @param end   конец диапазона
     * @return значения функции
     */
    public List<Double> logarithmic(int start, int end) {
        return this.diapason(start, end, Math::log);
    }
}
