package ch.heig.dai.lab.protocoldesign;


import ch.heig.dai.lab.protocoldesign.operation.Add;
import ch.heig.dai.lab.protocoldesign.operation.Mul;

public class Worker {
    public Result work(Request op) {
        Double result = null;
        switch (op.getOperator()) {
            case ADD:
                result = Add.make(1, 2);
            case MUL:
                result = Mul.make(1, 2);
            default:
                break;
        }
        if (result != null)
            return new Result("asd", result, true, null);
        else {
            return new Result("asd", 0, false, "Invalid operator");
        }
    }
}
