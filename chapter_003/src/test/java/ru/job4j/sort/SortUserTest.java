package ru.job4j.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

/**
 * Тестируем сортировщик пользователей.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-18
 */
public class SortUserTest {
    @Test
    public void whenUnsortedThenFirstInSetIsYoungest() {
        SortUser sorter = new SortUser();
        List<User> users = Arrays.asList(
                new User("Clark Kent", 30),
                new User("Mickey Mouse", 10),
                new User("Steve Rogers", 55)
        );
        Set<User> result = sorter.sort(users);
        assertThat(result.iterator().next().getName(), is("Mickey Mouse"));
    }

    @Test
    public void whenUnsortedThenFirstAgeInSetIsLeast() {
        SortUser sorter = new SortUser();
        List<User> users = Arrays.asList(
                new User("Steve Rogers", 55),
                new User("Peter Parker", 30),
                new User("Clark Kent", 30)
        );
        Set<User> result = sorter.sort(users);
        assertThat(result.iterator().next().getAge(), is(30));
    }

    @Test
    public void whenEmptyListThenEmptySet() {
        SortUser sorter = new SortUser();
        List<User> users = Collections.emptyList();
        Set<User> result = sorter.sort(users);
        assertThat(result, is(Collections.emptySet()));
    }

    @Test
    public void whenUnsortedThenSortedByNameLength() {
        SortUser sorter = new SortUser();
        List<User> users = Arrays.asList(
                new User("Sergei", 25),
                new User("Ivan", 30),
                new User("Tom", 15),
                new User("Sergei", 20)
        );
        List<User> expected = Arrays.asList(
                new User("Tom", 15),
                new User("Ivan", 30),
                new User("Sergei", 25),
                new User("Sergei", 20)
        );
        assertThat(
                sorter.sortNameLength(users).toString(),
                is(expected.toString())
        );
    }

    @Test
    public void whenUnsortedThenSortedByNameAndAge() {
        SortUser sorter = new SortUser();
        List<User> users = Arrays.asList(
                new User("Sergei", 25),
                new User("Ivan", 30),
                new User("Sergei", 20),
                new User("Ivan", 25)
        );
        List<User> expected = Arrays.asList(
                new User("Ivan", 25),
                new User("Ivan", 30),
                new User("Sergei", 20),
                new User("Sergei", 25)
        );
        assertThat(
                sorter.sortByAllFields(users).toString(),
                is(expected.toString())
        );
    }
}
