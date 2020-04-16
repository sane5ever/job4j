package ru.job4j.statistics;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.statistics.model.Info;
import ru.job4j.statistics.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Статистика по коллекции. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-07
 */
public class AnalyzeTest {
    private final Analyze analyze = new Analyze();
    private List<User> previous,
            current;

    @Before
    public void prepare() {
        this.previous = List.of(
                new User(111, "Daredevil"),
                new User(222, "Karen Page"),
                new User(555, "Kingpin")
        );
        this.current = new ArrayList<>(previous);
    }

    @Test
    public void whenNoChangesThenAllInfoFieldsShouldBeZero() {
        Info result = this.analyze.diff(previous, current);
        assertThat(result.getAdded(), is(0));
        assertThat(result.getChanged(), is(0));
        assertThat(result.getDeleted(), is(0));
    }

    @Test
    public void whenTwoAddedThenInfoShouldMarkThat() {
        this.current.addAll(List.of(
                new User(333, "Elektra"),
                new User(444, "Punisher")
        ));
        Info result = this.analyze.diff(previous, current);
        assertThat(result.getAdded(), is(2));
        assertThat(result.getChanged(), is(0));
        assertThat(result.getDeleted(), is(0));
    }

    @Test
    public void whenTwoRemovedThenInfoShouldMarkThat() {
        this.current.remove(0);
        this.current.remove(0);
        Info result = this.analyze.diff(previous, current);
        assertThat(result.getAdded(), is(0));
        assertThat(result.getChanged(), is(0));
        assertThat(result.getDeleted(), is(2));
    }

    @Test
    public void whenOneEditedThenInfoShouldSymmetricallyMarkThat() {
        this.current.remove(0);
        this.current.add(new User(111, "Matt Murdock"));
        assertThat(this.analyze.diff(previous, current).getChanged(), is(1));
        assertThat(this.analyze.diff(current, previous).getChanged(), is(1));
    }

    @Test
    public void whenReflectiveCheckThenAllInfoFieldsShouldBeZero() {
        Info result = this.analyze.diff(previous, previous);
        assertThat(result.getAdded(), is(0));
        assertThat(result.getDeleted(), is(0));
        assertThat(result.getChanged(), is(0));
    }
}
