package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

/**
 * Итератор четные числа.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-16
 */
public class EvenNumIterator implements Iterator<Integer> {
    /**
     * поле для хранения итерируемого массива чисел
     */
    private int[] data;
    /**
     * указатель, с кот. осуществляется поиск след. чётного эл-та
     */
    private int cursor;

    /**
     * инициализуем поле предназначенным для итерации массивом чисел
     *
     * @param numbers массив чисел
     */
    public EvenNumIterator(final int[] numbers) {
        this.data = numbers;
    }

    /**
     * определяем наличие неполученных чётных эл-тов с помощью {@link #findNext()}
     *
     * @return true, если остались ещё не выданные чётные эл-ты
     */
    @Override
    public boolean hasNext() {
        return this.findNext() != -1;
    }

    /**
     * выдаём следуюший чётный эл-т массива с помощью {@link #findNext()}
     * сдвигаем указатель на след. эл-т в массиве, сужая диапазон поиска чётного эл-та в дальнейшем
     *
     * @return следующее число
     * @throws NoSuchElementException вызов метода при отсутствии чётных чисел среди оставшихся
     */
    @Override
    public Integer next() {
        int index = this.findNext();
        if (index == -1) {
            throw new NoSuchElementException();
        }
        this.cursor++;
        return this.data[index];
    }

    /**
     * ищем индекс в массиве первого чётного числа, начиная с текущего положения курсора до конца
     * при его отсуствии возвращаем -1
     *
     * @return индекс массива
     */
    private int findNext() {
        return IntStream.range(this.cursor, this.data.length)
                .filter(index -> this.data[index] % 2 == 0)
                .findFirst().orElse(-1);
    }
}
