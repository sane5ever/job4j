package ru.job4j.calculate;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 12.09.2018
 */

public class CalculateTest {
	/**
	 *Test echo
	 */
	@Test
	public void whenTakeNameThenThreeEchoPlusName() {
		String input = "Mickey Mouse";
		String expect = "Echo, echo, echo : Mickey Mouse";
		Calculate calc = new Calculate();
		String result = calc.echo(input);
		assertThat(result, is(expect));
	}
}