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
     * конструктор без параметров, инициализирует поле с разделителем
     * стандартным символом (backslash)
     */
    public DepartmentSorter() {
        this('\\');
    }

    /**
     * конструктор, позволяющий задать символ, разделяющий подразделения,
     * отличный от используемого по умолчанию
     *
     * @param splitter символ-разделитель
     */
    public DepartmentSorter(char splitter) {
        this.splitter = splitter;
    }

    /**
     * внутренний класс, каркас для подразделений в иерархии департаментов;
     * каждый департамент хранит ссылку на департамент, подразделением кот.
     * явл. данный (parent), а также на все собственные поразделения (children);
     * реализует интерфейс Comparable, упрощяющий последующую сортировку департаментов;
     * методы {@link #compareTo(Department)}, {@link #hashCode()} и {@link #equals(Object)}
     * включают в себя проверку по полю {@link #name}, по кот. и осуществляется сравнение департаментов
     */
    private class Department implements Comparable<Department> {
        String name;
        Department parent;
        List<Department> children;

        public Department(String name, Department parent) {
            this.name = name;
            this.parent = parent;
            this.children = new ArrayList<>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Department that = (Department) o;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public int compareTo(Department o) {
            return this.name.compareTo(o.name);
        }
    }

    /**
     * первая часть задания:
     * из набора строк с кодами подразделений воссоздаём их полную иерархию (дерево)
     * на основе внутреннего класса Department с помощью ArrayList.
     *
     * @param departments массив строк подразделений
     * @return иерархия подразделений (без null-корня)
     */
    private List<Department> collect(String[] departments) {
        Department root = new Department(null, null);
        StringBuilder buffer = new StringBuilder();
        StringBuilder source = new StringBuilder();
        for (String department : departments) {
            this.collectLine(root, source.append(department).append(this.splitter), buffer);
        }
        return root.children;
    }

    /**
     * идём посимвольно по строке с кодом подразделения, при каждой встрече символа-разделителя
     * рекурсивно создаём очередного потомка из скопившихся в буфере символов (с начала строки
     * до текущего разделителя не включачая), добавляем в иерархию департаментов (если такого
     * ещё нет, иначе используем уже существующий);
     * по завершении прохода по строке очищаем символьные буферы для следующей линии.
     *
     * @param root   корень (нулевой) создаваемой иерархии, к кот. цепляем ветви из очередной строки
     * @param source буфер-источник с кодом подразделения
     * @param buffer пустой буфер, в кот. собираем посимвольно имена подраздений (от верхнего уровня)
     */
    private void collectLine(Department root, StringBuilder source, StringBuilder buffer) {
        Department parent = root;
        for (int position = 0; position < source.length(); position++) {
            char symbol = source.charAt(position);
            if (symbol == this.splitter) {
                Department child = new Department(buffer.toString(), parent);
                int index = parent.children.indexOf(child);
                if (index == -1) {
                    parent.children.add(child);
                    parent = child;
                } else {
                    parent = parent.children.get(index);
                }
            }
            buffer.append(symbol);
        }
        buffer.setLength(0);
        source.setLength(0);
    }

    /**
     * вторая часть задания:
     * рекурсивно сортируем получаенную в методе {@link #collect} иерархию депортаментов
     * с помощью передаваемого компаратора (для обоих пунктов задания)
     *
     * @param departments подразделения одного уровня
     * @param comparator  компаратор
     * @return рекурсивно наполняемый в отсортированном порядке лист подразделений
     */
    private List<String> accumulate(List<Department> departments, Comparator<Department> comparator) {
        List<String> result = new ArrayList<>();
        departments.sort(comparator);
        for (Department department : departments) {
            result.add(department.name);
            result.addAll(this.accumulate(department.children, comparator));
        }
        return result;
    }

    /**
     * интерфейсный метод, явл. входом при восходящей сортировке
     *
     * @param departments входящий массив строк департаментов
     * @return отсортированный массив департаментов
     */
    public String[] ascending(String[] departments) {
        return this.accumulate(this.collect(departments), Comparator.naturalOrder())
                .toArray(new String[0]);
    }

    /**
     * интерфейсный метод, явл. входом при нисходящей сортировке
     *
     * @param departments входящий массив строк департаментов
     * @return отсортированный массив департаментов
     */
    public String[] descending(String[] departments) {
        return this.accumulate(this.collect(departments), Comparator.reverseOrder())
                .toArray(new String[0]);
    }
}