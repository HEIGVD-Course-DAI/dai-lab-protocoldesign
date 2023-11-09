package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

enum Operation{
    ADD,
    MULTIPLY,
    DIVIDE,
    SUBSTRACT,
}

public class CalcWrapper {
    Socket socket;

    StringBuilder dataToSend;

    BufferedWriter writer;

    BufferedReader reader;

    CalcWrapper(){

    }

    /**
     *
     * @param operation
     * @param operand1
     * @param operand2
     * @return
     */
    private String createMessage(Operation operation, int operand1, int operand2){
        StringBuilder stringBuilder = new StringBuilder();
        switch (operation){
            case ADD -> stringBuilder.append("ADD");
            case MULTIPLY -> stringBuilder.append("MUL");
            case DIVIDE -> stringBuilder.append("DIV");
            case SUBSTRACT -> stringBuilder.append("SUB");
        }
        stringBuilder.append(" ").append(operand1).append(" ").append(operand2).append("\n");
        return stringBuilder.toString();
    }

    /**
     *
     * @param string
     * @return
     */
    private int parseAnswer(String string){
        String[] token = string.split(" ");
        switch (token[0]) {
            case "ANSWER" -> {
                return Integer.parseInt(token[2]);
            }
            case "ERROR" -> {
                throw new RuntimeException("Error " + token[1]);
            }
        }
        return 0;
    }

    /**
     *
     * @param address
     * @param port
     */
    public void openConnection(String address, int port){
        try(Socket socket = new Socket(address,port)){
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(),
                                            StandardCharsets.UTF_8));
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(),
                                            StandardCharsets.UTF_8)
            );

            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
            reader.readLine();
        }
        catch(Exception e){
            System.out.println("Error connecting to " + address + ":" + port);
        }
    }

    /**
     *
     * @param operand1
     * @param operand2
     * @return
     */
    public int add(int operand1, int operand2) throws IOException {
        String message = createMessage(Operation.ADD,operand1,operand2);
        System.out.println(message);
        writer.write(message);
        writer.flush();
        String line;
        int result = 0;
        while((line = reader.readLine()) != null){
            try{
                result = parseAnswer(line);
            }
            catch (RuntimeException exception){
                System.err.println("Error in parsing answer from client " + exception.toString());
            }
        }
        return result;
    }

    /**
     *
     * @param operand1
     * @param operand2
     * @return
     */
    public int multiply(int operand1, int operand2) throws IOException {
        String message = createMessage(Operation.MULTIPLY, operand1, operand2);
        writer.write(message);
        writer.flush();
        int result = 0;
        String line;
        while((line = reader.readLine()) != null){
            try{
                result = parseAnswer(line);
            }
            catch (RuntimeException exception){
                System.err.println("Error in parsing answer from client " + exception.toString());
            }
        }
        return result;
    }

    /**
     *
     * @param operand1
     * @param operand2
     * @return
     */
    public int divide(int operand1, int operand2) throws IOException {
        String message = createMessage(Operation.DIVIDE,operand1,operand2);
        writer.write(message);
        writer.flush();
        int result = 0;
        String line;
        while((line = reader.readLine()) != null){
            try{
                result = parseAnswer(line);
            }
            catch (RuntimeException exception){
                System.err.println("Error in parsing answer from client " + exception.toString());
            }
        }
        return result;
    }

    public int substract(int operand1, int operand2)throws IOException{
        String message =  createMessage(Operation.SUBSTRACT,operand1,operand2);
        writer.write(message);
        writer.flush();
        int result = 0;
        String line;
        while((line = reader.readLine()) != null){
            try{
                result = parseAnswer(line);
            }
            catch (RuntimeException exception){
                System.err.println("Error in parsing answer from client " + exception.toString());
                throw exception;
            }
        }
        return result;
    }

    /**
     *
     */
    public void closeConnection(){
        try{
            this.socket.close();
        }
        catch (Exception e){
            System.out.println("Error closing socket : " + socket.toString());
        }
    }
}
