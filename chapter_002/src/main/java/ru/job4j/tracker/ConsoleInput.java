package ru.job4j.tracker;

import java.util.Scanner;

/**
 * Используется для ввода пользовательских данных из консоли.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-03
 */
public class ConsoleInput implements Input {
    /** объект для считывания данных с консоли */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Выводит в консоль переданную строку, возвращает полученный на неё ответ от пользователя.
     *
     * @param question строка в консоль
     * @return строка из консоли
     */
    public String ask(String question) {
        System.out.print(question);
        return this.scanner.nextLine();
    }

    @Override
    public int ask(String question, int[] range) {
        int key = Integer.valueOf(this.ask(question));
        if (!isCorrect(key, range)) {
            throw new MenuOutException("Out of menu range.");
        }
        return key;
    }

    boolean isCorrect(int key, int[] range) {
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        return exist;
    }
}