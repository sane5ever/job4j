package ru.job4j.pseudo;

/**
 * Прямоугольный равнобедренный треугольник в псевдографике.
 */
public class Triangle implements Shape {
    /** размер строк, занимаемых фигурой */
    private int size;

    /**
     * Конструктор, задаём размер треугольника.
     * @param size длина катета
     */
    public Triangle(int size) {
        this.size = size;
    }

    /**
     * Формируем треугольник c заданным размером катета.
     * @return строковый треугольник
     */
    @Override
    public String draw() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row != this.size; row++) {
            if (row != 0) {
                result.append(System.lineSeparator());
            }
            for (int column = 0; column != row + 1; column++) {
                result.append('+');
            }
        }
        return result.toString();
    }

}