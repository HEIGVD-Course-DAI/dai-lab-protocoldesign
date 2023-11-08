package ch.heig.dai.lab.protocoldesign_common;

public enum Operator {
    ADD,
    SUB,
    MUL,
    DIV,
    MOD,
    POW;

    public static Operator fromString(String str) {
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

    public static Operator fromName(String str) {
        return switch (str) {
            case "ADD" -> ADD;
            case "SUB" -> SUB;
            case "MUL" -> MUL;
            case "DIV" -> DIV;
            case "MOD" -> MOD;
            case "POW" -> POW;
            default -> throw new IllegalArgumentException("Unknown operator: " + str);
        };
    }
}
