package ch.heig.dai.lab.protocoldesign;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculationTest {

	@Test
	public void basicAdditionWorks() {
		Calculation c = new Calculation("5+3");
		assertEquals(8, c.getResult());
	}

	@Test
	public void basicSubtractionWorks() {
		Calculation c = new Calculation("7-5");
		assertEquals(2, c.getResult());
	}

	@Test
	public void basicMultiplicationWorks() {
		Calculation c = new Calculation("7*5");
		assertEquals(35, c.getResult());
	}

	@Test
	public void basicDivisionWorks() {
		Calculation c = new Calculation("7/2");
		assertEquals(3.5, c.getResult());
	}

	@Test
	public void itSupportExtraSpaces() {
		Calculation c = new Calculation("    7	/ 2   ");
		assertEquals(3.5, c.getResult());
	}

	@Test
	public void itFailsWhenInvalidFormat() {
		Calculation c = new Calculation("78");
		assertThrows(RuntimeException.class, () -> {
			c.getResult();
		});

		Calculation c2 = new Calculation("asdf / 2");
		assertThrows(RuntimeException.class, () -> {
			c.getResult();
		});
	}

	@Test
	public void itSupportsCalculationsWithNegativeAndFloatingValues() {
		assertEquals(-0.24, (new Calculation("-2.4/ 10  ")).getResult());

		assertEquals(-6.6, (new Calculation("3.4    + -10")).getResult());

		assertEquals(-0.24, (new Calculation("-2.4/ 10  ")).getResult());
	}

	@Test
	public void itFailsOnDivisionByZero() {
		Calculation c = new Calculation("    2 /0 ");
		assertThrows(RuntimeException.class, () -> {
			assertEquals(12, c.getResult());
		});
	}
}
