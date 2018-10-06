package ru.job4j.pseudo;

/**
 * Квадрат в псевдографике.
 */
public class Square implements Shape {
    /** размер строк, занимаемых фигурой */
    private int size;

    /**
     * Конструктор, задаём размер квадрата.
     * @param size длина ребра
     */
    public Square(int size) {
        this.size = size;
    }

    /**
     * Формируем квадрат с заданным размером ребра.
     * @return строковый квадрат
     */
    @Override
    public String draw() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < this.size; row++) {
            if (row != 0) {
                result.append(System.lineSeparator());
            }
            for (int column = 0; column < this.size; column++) {
                result.append('+');
            }
        }
        return result.toString();
    }
}