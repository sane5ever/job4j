package ru.job4j.group;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Фильтратор студентов.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-19
 */
public class Filter {
    /**
     * Возвращает список студентов, у кот. балл аттестата больше заданного числа.
     *
     * @param students список студентов
     * @param bound заданный балл
     * @return список студентов с баллом выше заданного
     */
    public List<Student> levelOf(List<Student> students, int bound) {
        return students.stream()
                .flatMap(Stream::ofNullable)
                .sorted(Comparator.comparingInt(Student::getScope).reversed())
                .takeWhile(s -> s.getScope() > bound)
                .collect(Collectors.toList());
    }
}
