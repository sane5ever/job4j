package ru.job4j.array;

/**
 * 6.3. Массив заполнен true или false.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-19
 */
public class Check {
    /**
     * @param data входящие массив c boolean-значениями
     * @return true, если все эл-ты массива идентичны, иначе false
     */
    public boolean mono(boolean[] data) {
        boolean result = true;
        boolean checker = data[0];
        for (int index = 1; index < data.length; index++) {
            if (checker != data[index]) {
                result = false;
                break;
            }
        }
        return result;
    }
}