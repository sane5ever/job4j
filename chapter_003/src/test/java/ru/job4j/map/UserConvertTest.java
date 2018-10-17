package ru.job4j.map;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Тестируем конвертацию List в Map.
 */
public class UserConvertTest {
    private final UserConvert converter = new UserConvert();

    @Test
    public void when3UsersThen3Entries() {
        List<User> users = this.fill();
        HashMap<Integer, User> result = this.converter.process(users);
        assertThat(result.size(), is(3));
    }

    @Test
    public void when3UsersThenNamesMatch() {
        List<User> users = this.fill();
        HashMap<Integer, User> result = this.converter.process(users);
        assertThat(result.get(123).getName(), is("Mickey Mouse"));
    }

    @Test
    public void when3UsersThenCitiesMatch() {
        List<User> users = this.fill();
        HashMap<Integer, User> result = this.converter.process(users);
        assertThat(result.get(789).getCity(), is("NYC"));
    }

    @Test
    public void whenEmptyListThenEmptyMap() {
        List<User> users = Collections.emptyList();
        HashMap<Integer, User> result = this.converter.process(users);
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void whenIdDoesNotExist() {
        List<User> users = this.fill();
        HashMap<Integer, User> result = this.converter.process(users);
        assertNull(result.get(124));
    }


    private List<User> fill() {
        return Arrays.asList(
                new User(123, "Mickey Mouse", "Disney Land"),
                new User(456, "Clark Kent", "Metropolis"),
                new User(789, "Steve Rogers", "NYC")
        );
    }
}
