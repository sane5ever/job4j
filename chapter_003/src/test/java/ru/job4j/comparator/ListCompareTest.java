package ru.job4j.comparator;

import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестируем работу компаратора для строк.
 */
public class ListCompareTest {
    private final ListCompare comparator = new ListCompare();

    @Test
    public void whenStringsAreEqualThenZero() {
        int result = this.comparator.compare(
                "Ivanov",
                "Ivanov"
        );
        assertThat(result, is(0));
    }

    @Test
    public void whenLeftLessThanRightResultShouldBeNegative() {
        int result = this.comparator.compare(
                "Ivanov",
                "Ivanova"
        );
        assertThat(result, lessThan(0));
    }

    @Test
    public void whenLeftGreaterThanRightResultShouldBePositive() {
        int result = this.comparator.compare(
                "Petrov",
                "Ivanova"
        );
        assertThat(result, greaterThan(0));
    }

    @Test
    public void whenSecondCharOfLeftGreaterThanRightResultShouldBePositive() {
        int result = this.comparator.compare(
                "Petrov",
                "Patrov"
        );
        assertThat(result, greaterThan(0));
    }

    @Test
    public void whenSecondCharOfLeftLessThanRightResultShouldBeNegative() {
        int result = this.comparator.compare(
                "Patrova",
                "Petrov"
        );
        assertThat(result, lessThan(0));
    }
}
