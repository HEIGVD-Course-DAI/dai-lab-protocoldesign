package ch.heig.dai.lab.protocoldesign;

import ch.heig.dai.lab.protocoldesign.operation.Add;
import ch.heig.dai.lab.protocoldesign.operation.Mul;

public class Worker {
    public Result work(Operation op) {
        Integer result = null;
        switch (Operation.getType()) {
            case ADD:
                result = Add.make(1, 2);
            case MUL:
                result = Mul.make(1, 2);
            default:
                break;
        }
        return new Result();
    }
}
