package ch.heig.dai.lab.protocoldesign;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class CalculationTest {
	@Test
	public void basicAdditionWorks() {
		Calculation c = new Calculation("5+3");
		assertEquals(8, c.getResult());
	}
}
