package ru.job4j.pseudo;

/**
 * Класс для вывода фигур в консоль.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-06
 *
 */
public class Paint {
    /**
     * Выводим в консоль фигуру, переданную в параметрах, размера size
     * @param shape строковая фигура
     */
    public void draw(Shape shape) {
		System.out.println(shape.draw());
	}
}