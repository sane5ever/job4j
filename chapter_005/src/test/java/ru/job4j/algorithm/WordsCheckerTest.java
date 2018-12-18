package ru.job4j.algorithm;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Collections Pro. Дополнительные задания. Тесты.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-14
 */
public class WordsCheckerTest {
    private final WordsChecker matcher = new WordsChecker();

    @Test
    public void ifAnagramReturnsTrueElseFalse() {
        assertTrue(this.matcher.isAnagram("мама", "амам"));
        assertTrue(this.matcher.isAnagram("мама", "мама"));
        assertTrue(this.matcher.isAnagram("мама", "аамм"));
        assertTrue(this.matcher.isAnagram("", ""));

        assertFalse(this.matcher.isAnagram("мама", "мыло"));
        assertFalse(this.matcher.isAnagram("мама", "маман"));
        assertFalse(this.matcher.isAnagram("мама", "манам"));
        assertFalse(this.matcher.isAnagram("мама", "ама"));
        assertFalse(this.matcher.isAnagram("мама", "ма"));
        assertFalse(this.matcher.isAnagram("мама", ""));
        assertFalse(this.matcher.isAnagram("", "мама"));
        assertFalse(this.matcher.isAnagram("мама", null));
        assertFalse(this.matcher.isAnagram(null, null));
    }

    @Test
    public void ifSingleSwapReturnsTrueElseFalse() {
        assertTrue(this.matcher.isSwapsCorrect(2, "мама", "маам"));

        assertFalse(this.matcher.isSwapsCorrect(2, "мама", "момо"));
        assertFalse(this.matcher.isSwapsCorrect(2, "мама", "амам"));

        assertTrue(this.matcher.isSwapsCorrect(4, "мама", "амам"));
        assertTrue(this.matcher.isSwapsCorrect(3, "абвгде", "ебагдв"));


    }
}
