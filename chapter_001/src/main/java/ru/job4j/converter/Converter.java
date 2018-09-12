package ru.job4j.converter;

/**
 * Конвертер валюты.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @since 13.09.2018
 */


public class Converter {
    /**
     * Конвертируем рубли в евро.
     * @param value — рубли.
     * @return — евро.
     */
    public int rubleToEuro(int value) {
        return value / 70;
    }

    /**
     * Конвертируем рубли в доллары.
     * @param value — рубли.
     * @return — доллары.
     */
    public int rubleToDollar(int value) {
        return value / 60;
    }

    /**
     * Конвертируем евро в рубли.
     * @param value — евро.
     * @return — рубли.
     */
    public int euroToRuble(int value) {
        return value * 70;
    }

    /**
     * Конвертируем доллары в рубли.
     * @param value — доллары.
     * @return — рубли.
     */
    public int dollarToRubble(int value) {
        return value * 60;
    }
}