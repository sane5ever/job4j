package ru.job4j.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Collections Pro. Дополнительные задания.
 *
 * 1. Определить, состоят ли слова из одинаковых символов, порядок символов в словах может не совпадать.
 * 2. Определить, различаются ли слова на одну перестановку («мама» и «маам»).
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-14
 */
public class WordsChecker {
    /**
     * Метод запускает проверку полученных на сравнение слов,
     * определяя, состоят ли они из одинакового набора символов.
     *
     * @param first  первое слово
     * @param second второе слово
     * @return true, если строки состоят из одинаковых символов
     */
    public boolean isAnagram(String first, String second) {
        return this.check(first, second) && this.haveEqualSymbols(first.toCharArray(), second.toCharArray());
    }

    /**
     * Метод запускает проверку полученных на сравнение слов,
     * определяя, различаются ли слова на заданное число перестановок символов.
     * Одна перестановка — два символа поменялись местами в другом слове.
     * При большем числе перестановок могут попадаться как рефлективные перестановки (a ⇄ b),
     * так и транзистивные (a → b → c), поэтому передаваемое число определяет именно кол-во символов,
     * кот. поменяли своё расположение, а не кол-во самих перестановок по факту.
     *
     * @param swapAmount кол-во символов, изменивших своё расположение
     * @param first      первое слово
     * @param second     второе слово
     * @return true, если строки различаются на заданное кол-во перестановок
     */
    public boolean isSwapsCorrect(int swapAmount, String first, String second) {
        return this.check(first, second) && this.haveAdjustedCharSwapAmount(first, second, swapAmount);
    }

    /**
     * Метод проводит предварительную проверку переданных в программу слов:
     * для успешного сравнения они должны быть не null,
     * так же совпадать по длине (иначе проводить дальнейшую проверку бессмысленно).
     *
     * @param first  первое слово
     * @param second второе слово
     * @return true, если слова пригодны для сравнения
     */
    private boolean check(String first, String second) {
        return first != null && second != null && first.length() == second.length();
    }

    /**
     * Метод осуществляет проверку, состоят ли заданные массивы из одинакового набора элементов.
     *
     * @param first  первый массив символов
     * @param second второй массив символов
     * @return true, если наборы символов совпадают (ignoring order)
     */
    private boolean haveEqualSymbols(char[] first, char[] second) {
        boolean result = true;
        Map<Character, Integer> symbols = this.toCharacterMap(first);
        for (Character c : second) {
            Integer value = symbols.get(c);
            if (value == null) {
                result = false;
                break;
            } else if (value == 1) {
                symbols.remove(c);
            } else {
                symbols.put(c, value - 1);
            }
        }
        return result && symbols.isEmpty();
    }

    /**
     * Метод собирает в мапу статистику символов: какие имеются,
     * сколько раз встречаются в массиве.
     *
     * @param chars массив символов
     * @return отображение символов на количество их попаданий в массиве
     */
    private Map<Character, Integer> toCharacterMap(char[] chars) {
        return IntStream.range(0, chars.length)
                .mapToObj(i -> chars[i])
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                c -> 1,
                                (old, fresh) -> old + fresh,
                                HashMap::new
                        )
                );
    }

    /**
     * Метод осуществляет проверку, различаются ли слова на заданное число перестановок.
     * Для этого сначала собирает массив с индексами всех не совпадающих символов,
     * затем проверяет, что кол-во таких индексов совпадает с ожидаемым,
     * и запускает проверку, что наборы несовпадающих символов одинаковы (ignoring order).
     * Наборы одинаковы — значит действительно перестановки, а не замена символов на другие.
     *
     * @param first      первое слово
     * @param second     второе слово
     * @param swapAmount кол-во перестановок
     * @return true, если количество перестановок совпадает с заданным
     */
    private boolean haveAdjustedCharSwapAmount(String first, String second, int swapAmount) {
        int[] diffs = IntStream.range(0, first.length())
                .filter(index -> first.charAt(index) != second.charAt(index))
                .toArray();
        return diffs.length == swapAmount
                && this.haveEqualSymbols(this.getRequiredChars(diffs, first), this.getRequiredChars(diffs, second));
    }

    /**
     * Метод возвращает массив символов с указанными индексами в слове.
     *
     * @param indexes индексы символов
     * @param word    слово
     * @return набор символов слова с заданными индексами
     */
    private char[] getRequiredChars(int[] indexes, String word) {
        char[] result = new char[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            result[i] = word.charAt(indexes[i]);
        }
        return result;
    }
}
