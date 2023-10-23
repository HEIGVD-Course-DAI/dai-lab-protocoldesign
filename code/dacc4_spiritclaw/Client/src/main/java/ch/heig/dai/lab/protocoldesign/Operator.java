package ch.heig.dai.lab.protocoldesign;

public enum Operator {
    ADD,
    SUB,
    MUL,
    DIV,
    MOD,
    POW;

    static Operator fromString(String str) {
        return switch (str) {
            case "+" -> ADD;
            case "-" -> SUB;
            case "*" -> MUL;
            case "/" -> DIV;
            case "%" -> MOD;
            case "^" -> POW;
            default -> throw new IllegalArgumentException("Unknown operator: " + str);
        };
    }
}
