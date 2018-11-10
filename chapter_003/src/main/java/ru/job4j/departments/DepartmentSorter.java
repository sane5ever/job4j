package ru.job4j.departments;

import java.util.*;

/**
 * Класс реализует возможность сортировки массива кодов подразделеений по возрастанию и убыванию,
 * при которых сохраняется иерархическая структура, т.к. отсортированный массив используется
 * для отображения категорий пользователю.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-11-08
 */
public class DepartmentSorter {
    /**
     * в этом поле храним символ, по кот. проходит сепарация департаментов и их подразделений
     */
    private final char splitter;
    /**
     * в этом поле храним базу департаментов, отсортированную по возрастанию
     */
    private Set<String> data = new TreeSet<>(
            (first, second) -> this.compareDepartments(first, second, false)
    );

    /**
     * в этом поле храним базу департаментов, отсортированную по убыванию,
     * для поддержания правильного порядка используем компаратор
     */
    private Set<String> descendingData = new TreeSet<>(
            (first, second) -> this.compareDepartments(first, second, true)
    );

    /**
     * конструктор без параметров, инициализирует поле с разделителем
     * стандартным символом (backslash)
     *
     * @param departments строки с департаментами для послед. сортировки
     */
    public DepartmentSorter(String[] departments) {
        this(departments, '\\');
    }

    /**
     * конструктор, позволяющий задать символ, разделяющий подразделения,
     * отличный от используемого по умолчанию
     *
     * @param departments строки с департаментами для послед. сортировки
     * @param splitter    символ-разделитель
     */
    public DepartmentSorter(String[] departments, char splitter) {
        this.splitter = splitter;
        this.initData(departments);
    }

    /**
     * производим заполнение полей с базой департаментов
     *
     * @param departments строки с департаментами
     */
    private void initData(String[] departments) {
        for (String department : departments) {
            int position = department.indexOf(this.splitter);
            while (position != -1) {
                this.addToData(department.substring(0, position));
                position = department.indexOf(this.splitter, position + 1);
            }
            this.addToData(department);
        }
    }

    /**
     * осуществляем сравнение двух строк-департаментов, используем в сепараторе
     *
     * @param first   первый департамент
     * @param second  второй департамент
     * @param reverse включаем режим нисходящего порядка
     * @return аналогично стандартным принципам сравнения объектов
     */
    public int compareDepartments(String first, String second, boolean reverse) {
        int minLevel = Math.min(this.departmentLevel(first), this.departmentLevel(second));
        int result = 0;
        for (int level = 0; level <= minLevel && result == 0; level++) {
            result = this.substring(first, level).compareTo(this.substring(second, level));
        }
        return result != 0 ? (reverse ? -1 * result : result) : Integer.compare(first.length(), second.length());
    }

    /**
     * получаем super-департамент заданного уровня (определяется по нахождению {@link #splitter) в строке)
     *
     * @param department поддепартамент
     * @param level      необходимый уровень
     * @return super-департамент (только сам)
     */
    private String substring(String department, int level) {
        int start = 0;
        int finish = department.indexOf(this.splitter);
        for (int count = 0; count < level; count++) {
            start = finish + 1;
            finish = department.indexOf(this.splitter, finish + 1);
        }
        finish = finish == -1 ? department.length() : finish;
        return department.substring(start, finish);
    }

    /**
     * вычисляем уровень вложенности департамента (равен количеству {@link #splitter) в строке)
     *
     * @param department полное имя департамента
     * @return уровень вложенности
     */
    private int departmentLevel(String department) {
        int count = 0;
        int position = department.indexOf(this.splitter);
        while (position != -1) {
            count++;
            position = department.indexOf(this.splitter, position + 1);
        }
        return count;
    }

    /**
     * передаём очередной департамент на хранение в базы
     *
     * @param part департамент
     */
    private void addToData(String part) {
        this.data.add(part);
        this.descendingData.add(part);
    }

    /**
     * выдаём пользователю иерархию департаментов, нарезанную и отсортированную по возрастанию
     * (с сохранением иерархии)
     *
     * @return иерархия департаментов в виде строковго массива
     */
    public String[] getData() {
        return this.data.toArray(new String[0]);
    }

    /**
     * выдаём пользователю иерархию департаментов, нарезанную и отсортированную по убыванию
     * (с сохранением иерархии)
     *
     * @return иерархия департаментов в виде строковго массива
     */
    public String[] getDescendingData() {
        return this.descendingData.toArray(new String[0]);
    }
}