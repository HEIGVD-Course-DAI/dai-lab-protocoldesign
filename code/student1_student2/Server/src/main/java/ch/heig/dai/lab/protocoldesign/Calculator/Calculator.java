package ch.heig.dai.lab.protocoldesign.Calculator;

public class Calculator {

    private int operand1;
    private int operand2;

    private operator op;


    private enum operator{ADD,SUB,DIV,MUL,ERROR};

    private operator toOperator(String operateur){
        if(operateur.equals("ADD")){
            return operator.ADD;
        } else if (operateur.equals("SUB")) {
            return operator.SUB;
        } else if (operateur.equals("DIV")) {
            return operator.DIV;
        }else if (operateur.equals("MUL")){
            return operator.MUL;
        }
        else{
            return operator.ERROR;
        }
    }

    public Calculator(String clientMessage){

        String[] words = clientMessage.split(" ");

        if(words.length == 3){

            op = toOperator(words[0]);

            try{
                operand1 = Integer.parseInt(words[1]);
                operand2 = Integer.parseInt(words[2]);

            }catch (NumberFormatException e){
                op = operator.ERROR;
            }



        }else{
            op = operator.ERROR;
        }





    }


    public int resultat(){

        try{
            switch (op){
                case ADD: return operand1 + operand2;
                case SUB: return operand1 - operand2;
                case DIV: return operand1 / operand2;
                case MUL: return operand1 * operand2;
                default: return 0;
            }
        }catch (ArithmeticException e){
            op = operator.ERROR;
        }
        return 0;
    }

    public boolean isValidOperator(){
        return op != operator.ERROR;
    }




}
