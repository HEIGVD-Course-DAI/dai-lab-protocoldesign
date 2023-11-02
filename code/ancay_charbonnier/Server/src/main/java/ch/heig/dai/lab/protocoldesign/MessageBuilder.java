package ch.heig.dai.lab.protocoldesign;

import java.math.BigInteger;
import java.security.InvalidParameterException;

public class MessageBuilder {
    private static final char DELIMITER_CHAR = '\n';

    public String buildOPSLSTRequest() {
        String opslst = "OPSLST ADD 2 MPLY 2 AVG -1" + DELIMITER_CHAR;
        return opslst;
    }

    public String buildRSLTRequest(BigInteger result) {
        String rslt = "RSLT " + result.toString() + DELIMITER_CHAR;
        return rslt;
    }

    public String buildERRORMessage(int errorCode) {
        String message = getCorrespondingErrorMessage(errorCode);
        String rslt = "ERROR " + errorCode + ' ' + message + DELIMITER_CHAR;
        return rslt;
    }

    private String getCorrespondingErrorMessage(int errorCode) {
        return switch (errorCode) {
            case 1 -> "Unknown request.";
            case 2 -> "Unknown operation.";
            case 3 -> "Invalid operands format.";
            case 4 -> "Missing operands.";
            case 5 -> "Too many operands.";
            case 6 -> "Computation error.";
            default -> throw new InvalidParameterException("unknown error code");
        };
    }
}
