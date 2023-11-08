package ch.heig.dai.lab.protocoldesign;

public class Request {
    private Type operator;
    private double[] operands;

    public Request(Type operator, double a, double b) {
        this.operator = operator;
        this.operands = new double[]{a, b};
    }

    public Type getOperator() {
        return operator;
    }
}
