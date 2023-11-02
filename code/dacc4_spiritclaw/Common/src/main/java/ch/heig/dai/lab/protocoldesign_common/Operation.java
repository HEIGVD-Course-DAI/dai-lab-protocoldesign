package ch.heig.dai.lab.protocoldesign_common;

public class Operation {
    private String left;
    private String right;
    private Operator operator;

    public Operation(String str){
        String[] parts = str.split(" ");
        this.left = parts[0];
        this.right = parts[2];
        this.operator = Operator.fromString(parts[1]);
    }

    public Operation(String left, String right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public String toString() {
        return operator + " " + left + " " + right;
    }
}
