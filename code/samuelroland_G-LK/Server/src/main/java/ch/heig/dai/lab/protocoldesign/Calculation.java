package ch.heig.dai.lab.protocoldesign;

public class Calculation {
	String calculation;
	Double operand1;
	Double operand2;
	// Operator operator; // TODO: create the enum Operator

	public Calculation(String calculation) {
		this.calculation = calculation;
	}

	public Double getResult() {
		// validate
		// parse 2 operands and operator
		// do calculation and return result
		// throws exception in case of format error or calculation error

		return 8.;
	}

	private boolean validate() {
		return false;
	}

	private void parse() {

	}

	private Double calculate() {
		return 0.;
	}
}
