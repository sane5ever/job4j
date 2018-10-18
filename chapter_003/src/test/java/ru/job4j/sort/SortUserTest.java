package ru.job4j.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
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
}
