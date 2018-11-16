package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Создать convert(Iterator<Iterator>).
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-16
 */
public class IteratorOfIterators {
    public Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            /**
             * поле для хранения указателя на текущий итератор, иницилизируем ссылкой на первый итератор
             */
            private Iterator<Integer> cursor = it != null && it.hasNext() ? it.next() : null;

            /**
             * определяем есть ли ещё эл-ты в оставшихся итераторах
             * после работы {@link #check()} данное поле хранит ссылку на гарантированно не пустой
             * итератор (если null — эл-тов больше нет)
             *
             * @return true, если есть
             */
            @Override
            public boolean hasNext() {
                this.check();
                return this.cursor != null;
            }

            /**
             * выдаём следующий элемент, обращаясь к полю {@link #cursor}
             * если эл-тов больше нет — кидаем исключение
             *
             * @return следующее число
             * @throws NoSuchElementException если эл-тов не осталось
             */
            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.cursor.next();
            }

            /**
             * проверяем, что итератор в поле {@link #cursor} имеет ещё эл-ты, иначе передвигаем
             * указатель на следующий итератор (если невозможно — null) и рекурсивно вызываем
             * метод снова на случай, если след. итератор пуст по дефолту
             */
            private void check() {
                if (this.cursor != null && !this.cursor.hasNext()) {
                    this.cursor = it.hasNext() ? it.next() : null;
                    this.check();
                }
            }

        };
    }
}
