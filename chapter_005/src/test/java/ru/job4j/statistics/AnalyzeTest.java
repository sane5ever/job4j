package ru.job4j.statistics;

import org.junit.Before;
import org.junit.Test;

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
    private List<Analyze.User> previous,
            current;

    @Before
    public void prepare() {
        this.previous = List.of(
                new Analyze.User(111, "Daredevil"),
                new Analyze.User(222, "Karen Page"),
                new Analyze.User(555, "Kingpin")
        );
        this.current = new ArrayList<>(previous);
    }

    @Test
    public void whenNoChangesThenAllInfoFieldsShouldBeZero() {
        Analyze.Info result = this.analyze.diff(previous, current);
        assertThat(result.added, is(0));
        assertThat(result.changed, is(0));
        assertThat(result.deleted, is(0));
    }

    @Test
    public void whenTwoAddedThenInfoShouldMarkThat() {
        this.current.addAll(List.of(
                new Analyze.User(333, "Elektra"),
                new Analyze.User(444, "Punisher")
        ));
        Analyze.Info result = this.analyze.diff(previous, current);
        assertThat(result.added, is(2));
        assertThat(result.changed, is(0));
        assertThat(result.deleted, is(0));
    }

    @Test
    public void whenTwoRemovedThenInfoShouldMarkThat() {
        this.current.remove(0);
        this.current.remove(0);
        Analyze.Info result = this.analyze.diff(previous, current);
        assertThat(result.added, is(0));
        assertThat(result.changed, is(0));
        assertThat(result.deleted, is(2));
    }

    @Test
    public void whenOneEditedThenInfoShouldSymmetricallyMarkThat() {
        this.current.remove(0);
        this.current.add(new Analyze.User(111, "Matt Murdock"));
        assertThat(this.analyze.diff(previous, current).changed, is(1));
        assertThat(this.analyze.diff(current, previous).changed, is(1));
    }

    @Test
    public void whenReflectiveCheckThenAllInfoFieldsShouldBeZero() {
        Analyze.Info result = this.analyze.diff(previous, previous);
        assertThat(result.added, is(0));
        assertThat(result.deleted, is(0));
        assertThat(result.changed, is(0));
    }

    
}
