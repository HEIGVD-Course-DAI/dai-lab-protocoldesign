package ch.heig.dai.lab.protocoldesign;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculationTest {

	@Test
	public void basicAdditionWorks() throws Exception {
		Calculation c = new Calculation("5+3");
		assertEquals(8, c.getResult());
	}

	@Test
	public void basicSubtractionWorks() throws Exception {
		Calculation c = new Calculation("7-5");
		assertEquals(2, c.getResult());
	}

	@Test
	public void basicMultiplicationWorks() throws Exception {
		Calculation c = new Calculation("7*5");
		assertEquals(35, c.getResult());
	}

	@Test
	public void basicDivisionWorks() throws Exception {
		Calculation c = new Calculation("7/2");
		assertEquals(3.5, c.getResult());
	}

	@Test
	public void moreThan2DecimalsFails() throws Exception {
		Calculation c = new Calculation("6.21/3.212");
		assertThrows(InvalidFormatException.class, () -> {
			c.getResult();
		});
	}

	@Test
	public void itSupportExtraSpaces() throws Exception {
		Calculation c = new Calculation("    7	/ 2   ");
		assertEquals(3.5, c.getResult());
	}

	@Test
	public void itFailsWhenInvalidFormat() throws Exception {
		Calculation c = new Calculation("78");
		assertThrows(InvalidFormatException.class, () -> {
			c.getResult();
		});

		Calculation c2 = new Calculation("asdf / 2");
		assertThrows(InvalidFormatException.class, () -> {
			c2.getResult();
		});
	}

	@Test
	public void itSupportsCalculationsWithNegativeAndFloatingValues() throws Exception {
		assertEquals(-0.24, (new Calculation("-2.4/ 10  ")).getResult());

		assertEquals(-6.6, (new Calculation("3.4    + -10")).getResult());

		assertEquals(-0.24, (new Calculation("-2.4/ 10  ")).getResult());
	}

	@Test
	public void itFailsOnDivisionByZero() throws Exception {
		Calculation c = new Calculation("    2 /0 ");
		assertThrows(ArithmeticException.class, () -> {
			assertEquals(12, c.getResult());
		});
	}
}
