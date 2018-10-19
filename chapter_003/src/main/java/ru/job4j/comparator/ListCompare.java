package ru.job4j.comparator;

import java.util.Comparator;

/**
 * Компоратор для строк.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-18
 */
public class ListCompare implements Comparator<String> {
    /**
     * Сравниваем слова.
     *
     * @param o1 первая строка
     * @param o2 вторая строка
     * @return результат сравнения
     */
    @Override
    public int compare(String o1, String o2) {
        int result = o1.length() - o2.length();
        int length = Integer.min(o1.length(), o2.length());
        for (int index = 0; index < length; index++) {
            if (o1.charAt(index) != o2.charAt(index)) {
                result = o1.charAt(index) - o2.charAt(index);
                break;
            }
        }
        return result;
    }
}
