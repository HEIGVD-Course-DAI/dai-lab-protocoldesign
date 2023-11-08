package ch.heig.dai.lab.protocoldesign;

import java.util.regex.*;

public class Calculation {
	String calculation;
	Double operand1;
	Double operand2;
	String operator;
	Pattern regex = Pattern.compile("^([+-]?(\\d+(\\.\\d{1,2})?))\\s*([+-/*])\\s*([+-]?(\\d+(\\.\\d{1,2})?))$");

	public Calculation(String calculation) {
		this.calculation = calculation.trim();
	}

	public Double getResult() {
		// validate
		// parse 2 operands and operator
		// do calculation and return result
		// throws exception in case of format error or calculation error
		if (!validate()) {
			throw new RuntimeException("Invalid format !");
		}
		parse();
		return calculate();
	}

	private boolean validate() {
		Matcher m = regex.matcher(calculation);
		return m.find();
	}

	private void parse() {
		Matcher m = regex.matcher(calculation);
		m.find();
		operator = m.group(4);
		operand1 = Double.parseDouble(m.group(1).trim());
		operand2 = Double.parseDouble(m.group(5).trim());
	}

	private Double calculate() {
		switch (operator) {
			case "+":
				return operand1 + operand2;
			case "-":
				return operand1 - operand2;
			case "*":
				return operand1 * operand2;
			case "/":
				if (operand2 == 0)
					throw new RuntimeException();
				return operand1 / operand2;
			default:
				throw new RuntimeException("Invalid operand !");
		}
	}
}
