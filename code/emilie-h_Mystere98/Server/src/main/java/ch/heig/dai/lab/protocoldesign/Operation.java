package ch.heig.dai.lab.protocoldesign;

public class Operation {
    public static Type getType() {
        return Type.ADD;
    }

    enum Type {
        ADD,
        MUL
    }
}
