package ch.heig.dai.lab.protocoldesign.models;

public class Calculate {
    private int[] operands;
    private final String operator;
    public Calculate(int[] operands, String operator) {
        this.operands = operands;
        this.operator = operator;
    }
    public int[] getOperands() {
        return operands;
    }
    public void setOperands(int[] operands) {
        this.operands = operands;
    }
    public String getOperator() {
        return operator;
    }
    public int getResult() {
        int result = 0;
        switch (operator) {
            case "add":
                for (int operand : operands) {
                    result += operand;
                }
                break;
            case "sub":
                result = operands[0];
                for (int i = 1; i < operands.length; i++) {
                    result -= operands[i];
                }
                break;
            case "mul":
                result = 1;
                for (int operand : operands) {
                    result *= operand;
                }
                break;
            case "div":
                result = operands[0];
                for (int i = 1; i < operands.length; i++) {
                    result /= operands[i];
                }
                break;
            default:
                System.out.println("Unknown operator: " + operator);
        }
        return result;
    }

}
