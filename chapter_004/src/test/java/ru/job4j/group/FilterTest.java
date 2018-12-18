package ru.job4j.group;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Фильтратор студентов. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-19
 */
public class FilterTest {
    Filter filter = new Filter();
    List<Student> students;

    @Before
    public void prepare() {
        students = new ArrayList<>();
        students.addAll(
                List.of(
                        new Student("A", 84),
                        new Student("B", 54),
                        new Student("C", 75),
                        new Student("D", 30)
                )
        );
    }

    @Test
    public void whenThreeNullAddedWithinThenTheySkipped() {
        List<Student> expected = List.of(
                students.get(0),
                students.get(2)
        );
        students.add(0, null);
        students.add(2, null);
        students.add(4, null);
        List<Student> result = filter.levelOf(students, 70);
        assertThat(result, is(expected));
    }

    @Test
    public void whenListStarsAndEndsWithNullThenNullsSkipped() {
        List<Student> expected = List.of(
                students.get(0),
                students.get(2),
                students.get(1),
                students.get(3)
        );
        students.add(0, null);
        students.add(0, null);
        students.add(null);
        students.add(null);
        List<Student> result = filter.levelOf(students, 0);
        assertThat(result, is(expected));
    }

    @Test
    public void whenNoElementWithinFilterThenEmptyList() {
        students.add(null);
        students.add(null);
        students.add(1, null);
        students.add(3, null);
        List<Student> result = filter.levelOf(students, 95);
        List<Student> expected = Collections.emptyList();
        assertThat(result, is(expected));
    }

    @Test
    public void whenOnlyNullsThenEmptyList() {
        students.clear();
        students.add(null);
        students.add(null);
        students.add(null);
        List<Student> result = filter.levelOf(students, 44);
        List<Student> expected = Collections.emptyList();
        assertThat(result, is(expected));
    }


}
