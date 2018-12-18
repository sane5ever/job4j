package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для двухмерного массива int[][]
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-16
 */
public class MatrixIterator implements Iterator<Integer> {
    /**
     * поле для хранения итерируемого массива массивов
     */
    private int[][] data;

    /**
     * указатель на массив, в кот. располагается следующий для выдачи эл-т
     */
    private int cursorY;

    /**
     * указатель на позицию в массиве, в кот. располагается следующий для выдачи эл-т
     */
    private int cursorX;

    /**
     * инициализируем поле предназначенным для итерации массивом массивов чисел
     *
     * @param matrix массив массивов
     */
    public MatrixIterator(final int[][] matrix) {
        this.data = matrix;
    }

    /**
     * определяем наличие неполученных эл-тов по указателю на номер массива в массиве,
     * тк такое значение этот указатель получит лишь после выдачи посл. эл-та в посл. массиве
     * (либо при отсутствии каких-либо эл-тов в принципе)
     *
     * @return true, если остались ещё не выданные эл-ты
     */
    @Override
    public boolean hasNext() {
        return this.cursorY != this.data.length;
    }

    /**
     * проверяем наличие след. эл-та с помощью {@link #hasNext}, кидаем исключение, если нет,
     * иначе выдаём следующий доступный эл-т, сдвигая указатель на след. позицию
     * (если в текущем массиве больше эл-тов нет, переходим к началу следующего)
     *
     * @return следующее число
     * @throws NoSuchElementException вызов метода при отсутствии оставшихся эл-тов
     */
    @Override
    public Integer next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        int result = this.data[cursorY][cursorX++];
        if (cursorX == this.data[cursorY].length) {
            cursorX = 0;
            cursorY++;
        }
        return result;
    }
}
