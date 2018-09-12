package ru.job4j.calculator;

/**
 * Калькулятор.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @since 12.09.2018
 */

public class Calculator {
    /**
     * Поле для хранения результата вычислений методов калькулятора.
     */
    private double result;
    /**
     * Геттер для поля result.
     * @return result;
     */
    public double getResult() {
        return this.result;
    }
    /**
     * Сложение двух чисел.
     * @param first — первое слагаемое;
     * @param second — второе слагаемое;
     */
    public void add(double first, double second) {
        this.result = first + second;
    }
    /**
     * Вычитание двух чисел.
     * @param first — вычитаемое;
     * @param second — вычитатель;
     */
    public void subtract(double first, double second) {
        this.result = first - second;
    }
    /**
     * Деление двух чисел.
     * @param first — делимое;
     * @param second — делитель;
     */
    public void div(double first, double second) {
        this.result = first / second;
    }
    /**
     * Умножение двух чисел.
     * @param first — первый множетель;
     * @param second — второй множетель;
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }
}
