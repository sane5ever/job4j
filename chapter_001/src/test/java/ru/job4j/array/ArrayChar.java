package ru.job4j.array;

/**
 * Обертка над строкой.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 2018-09-19
 */
public class ArrayChar {

    private char[] data;

    /**
     * переводит строку в символьный массив и заносит результат в поле data
     * @param line входящая строка
     */
    public ArrayChar(String line) {
        this.data = line.toCharArray();
    }

    /**
     * проверяет, что слово начинается с префикса
     * @param prefix префикс
     * @return true, если слово начинается с префикса, иначе — false
     */
    public boolean startWith(String prefix) {
        boolean result = true;
        char[] value = prefix.toCharArray();
        for (int index = 0; index < value.length; index++) {
            if (value[index] != this.data[index]) {
                result = false;
                break;
            }
        }
        return result;
    }
}
