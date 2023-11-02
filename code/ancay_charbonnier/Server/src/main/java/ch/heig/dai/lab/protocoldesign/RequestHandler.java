package ch.heig.dai.lab.protocoldesign;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler {
    private String response;
    private String inputRequest;
    private int errorCode;

    RequestHandler(String input) {
        inputRequest = input;
        errorCode = 0; // 0 means : no error
    }

    public String getResponse() {
        var tokens = splitIntoTokens();
        if(tokens != null && tokens.length != 0) {
            handleTokens(tokens);
        }

        if(errorCode != 0) {
            var builder = new MessageBuilder();
            response = builder.buildERRORMessage(errorCode);
        }

        return response;
    }

    private String[] splitIntoTokens() {
        var tokens = inputRequest.split(" ");
        if(tokens.length == 0) {
            errorCode = 1;
            return null;
        }
        for(String tok : tokens)
            if(tok.isEmpty()) {
                errorCode = 3;
                return null;
            }

        return tokens;
    }

    private void handleTokens(String[] tokens) {
        switch(tokens[0]) {
            case "GETOPS":
                var builder = new MessageBuilder();
                response = builder.buildOPSLSTRequest();
                break;
            case "CMPT":
                handleCMPTRequest(tokens);
                break;
            default:
                errorCode = 1;
        }
    }

    private void handleCMPTRequest(String[] tokens) {
        if(tokens.length < 3) {
            errorCode = 1;
            return;
        }

        String operation = tokens[1];

        ArrayList<BigInteger> operands = new ArrayList<BigInteger>();
        for(int i = 2; i < tokens.length; ++i) {
            var tok = tokens[i];
            BigInteger result;
            try {
                result = new BigInteger(tok);
            } catch(NumberFormatException e) {
                errorCode = 3;
                return;
            }

            operands.add(result);
        }

        switch(operation) {
            case "ADD":
                break;
            case "MPLY":
                break;
            case "AVG":
                break;
            default:
                errorCode = 2;
                return;
        }
    }

    private boolean checkOperandsCount(int actual, int expected) {
        if(actual > expected) {
            errorCode = 5;
            return false;
        }

        if(actual < expected) {
            errorCode = 4;
            return false;
        }
        return true;
    }

    private void handleADDOperation(ArrayList<BigInteger> operands) {
        if(!checkOperandsCount(operands.size(), 2))
            return;

        var op1 = operands.get(0);
        var op2 = operands.get(1);

        var builder = new MessageBuilder();
        response = builder.buildRSLTRequest(op1.add(op2));
    }

    private void handleMPLYOperation(ArrayList<BigInteger> operands) {
        if(!checkOperandsCount(operands.size(), 2))
            return;

        var op1 = operands.get(0);
        var op2 = operands.get(1);

        var builder = new MessageBuilder();
        response = builder.buildRSLTRequest(op1.multiply(op2));
    }

    private void handleAVGOperation(ArrayList<BigInteger> operands) {
        var sum = BigInteger.ZERO;
        for(var op : operands)
            sum = sum.add(op);

        String size = String.valueOf(operands.size());
        var result = sum.divide(new BigInteger(size));

        var builder = new MessageBuilder();
        response = builder.buildRSLTRequest(result);
    }
}
