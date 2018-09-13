package ru.job4j.calculator;

/**
 * Программа расчёта идеального веса.
 */
public class Fit {
    /**
     * Идеальный вес для мужчины.
     * @param height — рост.
     * @return — идеальный вес.
     */
    public double manWeight(double height) {
        return 1.15 * (height - 100);
    }

    /**
     * Идеальный вес для женщины.
     * @param height — рост.
     * @return — идеальный вес.
     */
    public double womanWeight(double height) {
        return 1.15 * (height - 110);
    }
}
