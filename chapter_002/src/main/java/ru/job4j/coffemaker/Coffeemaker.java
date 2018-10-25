package ru.job4j.coffemaker;

import java.util.Arrays;

/**
 * Кофемашина.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-23
 */
public class Coffeemaker {
    private final int[] coins;

    /**
     * Конструктор, устанавливает доступные монеты по умолчанию.
     */
    public Coffeemaker() {
        this.coins = new int[]{1, 2, 5, 10};
    }

    /**
     * Формируем массив со сдачей, используя заданный набор монет.
     *
     * @param value купюра
     * @param price цена кофе
     * @return сдача
     */
    public int[] changes(int value, int price) {
        int entire = value - price;
        int[] result = new int[this.size(entire)];
        int position = 0;
        for (int index = this.coins.length - 1; index >= 0; index--) {
            int coin = this.coins[index];
            int quantity = entire / coin;
            while (quantity != 0) {
                result[position++] = coin;
                quantity--;
            }
            entire %= coin;
            if (entire == 0) {
                break;
            }
        }
        return result;
    }

    /**
     * Вычисляем количество монет сдачи.
     *
     * @param entire величина сдачи
     * @return количество монет
     */
    private int size(int entire) {
        int result = 0;
        for (int index = this.coins.length - 1; index >= 0; index--) {
            result += entire / this.coins[index];
            entire %= this.coins[index];
            if (entire == 0) {
                break;
            }
        }
        return result;
    }
}