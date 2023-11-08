package ch.heig.dai.lab.protocoldesign;


import ch.heig.dai.lab.protocoldesign.operation.Add;
import ch.heig.dai.lab.protocoldesign.operation.Mul;

public class Worker {
    public Result work(Request op) {
        Double result = null;
        switch (op.getOperator()) {
            case ADD:
                result = Add.make(op.getOperands()[0], op.getOperands()[1]);
                break;
            case MUL:
                result = Mul.make(op.getOperands()[0], op.getOperands()[1]);
                break;
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
