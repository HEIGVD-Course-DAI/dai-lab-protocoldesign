package ch.heig.dai.lab.protocoldesign.Calculator;

public class Calculator {

    private int operand1;
    private int operand2;

    private operator op;

    boolean errorSyntax;

    private enum operator{ADD,SUB,DIV,MUL,ERROR};

    private operator toOperator(String operateur){
        if(operateur.compareTo("ADD") == 0){
            return operator.ADD;
        } else if (operateur.compareTo("SUB") == 0) {
            return operator.SUB;
        } else if (operateur.compareTo("DIV") == 0) {
            return operator.DIV;
        }else if (operateur.compareTo("MUL") == 0){
            return operator.MUL;
        }
        else{
            return operator.ERROR;
        }
    }

    public Calculator(String clientMessage){
        String[] words = clientMessage.split(" ");



        if(words.length == 3){
            for(int i = 0; i < 3 ; ++i){
                switch (i){
                    case 1: op = toOperator(words[i]); break;
                    case 2: operand1 = Integer.parseInt(words[i]); break;
                    case 3: operand2 = Integer.parseInt(words[i]); break;
                }
            }
        }





    }


    public int resultat(){
        switch (op){
            case ADD: return operand1 + operand2;
            case SUB: return operand1 - operand2;
            case DIV: return operand1 / operand2;
            case MUL: return operand1 * operand2;
            default: return 0;
        }
    }

    public boolean isValidOperator(){
        return op == operator.ERROR;
    }




}
