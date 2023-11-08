package ch.heig.dai.lab.protocoldesign;


public class Operation {
    public Operation() {
        operations = new Type[2];
        operations[0] = Type.ADD;
        operations[1] = Type.MUL;
    }
    private Type[] operations;

    public Type[] getOperations() {
        return operations;
    }
}
