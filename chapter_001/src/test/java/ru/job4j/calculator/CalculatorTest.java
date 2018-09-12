package ru.job4j.calculator;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест-класс для Calculator.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 12.09.2018
 */

public class CalculatorTest {
    /**
     * Тестируем сложение двух чисел.
     */
    @Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calculator = new Calculator();
        calculator.add(1D, 1D);
        double result = calculator.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
    /**
     * Тестируем вычитание двух чисел.
     */
    @Test
    public void whenSubtractTwoFromFourThenTwo() {
        Calculator calculator = new Calculator();
        calculator.subtract(4D, 2D);
        double result = calculator.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
    /**
     * Тестируем деление двух чисел.
     */
    @Test
    public void whenDivSixDividedByTwoThenThree() {
        Calculator calculator = new Calculator();
        calculator.div(6D, 2D);
        double result = calculator.getResult();
        double expected = 3D;
        assertThat(result, is(expected));
    }
    /**
     * Тестируем умножение двух чисел.
     */
    @Test
    public void whenMultipleTwoTimesTwoThenFour() {
        Calculator calculator = new Calculator();
        calculator.multiple(2D, 2D);
        double result = calculator.getResult();
        double expected = 4D;
        assertThat(result, is(expected));
    }
}
