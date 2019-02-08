package ru.job4j.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Проверка байтового потока.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-02-07
 */
public class InputChecker {
    private static final Logger LOG = LogManager.getLogger(InputChecker.class);

    private boolean firstLine;
    private Pattern regex;

    /**
     * Метод проверяет, что в байтовом потоке записано чётное число
     *
     * @param in байтовый поток
     * @return <tt>true</tt>, если число и оно чётное
     */
    public boolean isEvenNumber(InputStream in) {
        boolean isDigitSequence = true;
        int lastByte = -1;
        try {
            for (int b = in.read(); b != -1; b = in.read()) {
                if (b < 48 || b > 57) {
                    isDigitSequence = false;
                    break;
                }
                lastByte = b;
            }
        } catch (IOException ioe) {
            LOG.error(ioe.getMessage(), ioe);
        }
        return isDigitSequence && lastByte % 2 == 0;
    }

    /**
     * Метод удаляет запрещённые слова из потока ввода, записывая результат в выходной поток.
     * Удаление осуществляется путём перекрытия слов звёздочками в том же количества, что и длина запрещённого слова.
     *
     * @param in    входной поток
     * @param out   выходной поток
     * @param abuse массив запрещённых слов
     */
    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) {
        firstLine = true;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             PrintWriter writer = new PrintWriter(out)
        ) {
            reader.lines().map(getMapper(abuse)).forEach(writer::print);
        } catch (Exception ioe) {
            LOG.error(ioe.getMessage(), ioe);
        }
    }

    /**
     * Возвращает маппер строк на выходной поток.
     * (в случае отсутствия запрещённых слов в списке никаких проверок производить не следует)
     *
     * @param abuse запрещённые слова
     * @return маппер строк
     */
    private Function<String, String> getMapper(String[] abuse) {
        return abuse == null || abuse.length == 0
                ? this::prepareToOutput
                : line -> dropAbuses(line, abuse);
    }

    /**
     * Преобразует строки на выходной поток при отсутствии запрещённых слов.
     * (необходимо исключительно для недопущения добавления дополнительного переноса строки в конце потока)
     *
     * @param line строка из входного потока
     * @return идентичная строка при первом заходе, либо перенос строки + строка в остальных случаях
     */
    private String prepareToOutput(String line) {
        String result;
        if (firstLine) {
            firstLine = false;
            result = line;
        } else {
            result = String.format("%n%s", line);
        }
        return result;
    }

    /**
     * Преобразует строки на выходной поток при наличии запрещённых слов в переданном массиве.
     * Добавляет в начало перенос строки во все строки, кроме первой.
     *
     * @param line  строка из входного потока
     * @param abuse список запрещённых слов
     * @return строка с затёртыми запрещёнными словами.
     */
    private String dropAbuses(String line, String[] abuse) {
        StringBuilder result = new StringBuilder();
        if (firstLine) {
            regex = Pattern.compile(getCensorWordsRegex(abuse), Pattern.CASE_INSENSITIVE);
            firstLine = false;
        } else {
            result.append(System.lineSeparator());
        }
        return regex.matcher(result.append(line)).replaceAll("*");
    }

    /**
     * Создаёт регулярное выражение на базе массива запрещённых слов.
     * (?<=[].{0,N-1}.) — группа с положительной ретроспективной проверкой
     * (захватываем для перекрытия все символы,
     * последовательно соответствующие символам конкретному слова из массива)
     * \\bWORD\\b — обеспечиваем границы слова, чтобы не затирались символы в словах,
     * часть кот. совпадает с словом из списка
     * («war»: stop the war -> stop the ***, но warehouse for rent -> warehouse for rent)
     *
     * @param words запрещённые слова
     * @return регулярное выражение для фильтрации запрещённых слов
     */
    private String getCensorWordsRegex(String[] words) {
        return Arrays.stream(words).map(
                w -> String.format(
                        "(?<=(?=\\b%s\\b).{0,%d}).",
                        Pattern.quote(w),
                        w.length() - 1
                )
        ).collect(Collectors.joining("|"));
    }

}