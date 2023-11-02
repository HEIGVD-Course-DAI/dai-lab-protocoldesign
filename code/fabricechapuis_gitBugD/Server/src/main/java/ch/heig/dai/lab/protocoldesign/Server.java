package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void sendWelcomeMessage(BufferedWriter out) throws IOException {
        String welcomeMessage = """
                Supported operations:
                ADD <num1> <num2> -> this returns num1 + num2
                MUL <num1> <num1> -> this returns num1 * num2
                STOP -> this stops the connection""";

        out.write(welcomeMessage + "\n");
        out.flush();
    }

    private void sendByeMessage(BufferedWriter out) throws IOException {
        String byeMessage = "Bye!";

        out.write(byeMessage + "\n");
        out.flush();
    }

    private Object[] checkRequest(String[] opRequest)
            throws IllegalNumberOfArgumentsException, UnknownOperationException,
            ArgumentFormatException{
        String operation = opRequest[0];
        if (opRequest.length != 3) {
            throw new IllegalNumberOfArgumentsException("Too many arguments given! " +
                    "Please send only one instruction for the operation " +
                    "and two floating or integer numbers!");
        }
        if (!(operation.matches("ADD")
                | operation.matches("MUL"))) {
            throw new UnknownOperationException("The given operation is not supported. " +
                    "Supported operations are: ADD for addition " +
                    "and MUL for multiplication.");
        }
        double num1, num2;
        try {
            num1 = Double.parseDouble(opRequest[1]);
            num2 = Double.parseDouble(opRequest[2]);
        } catch (NumberFormatException ex) {
            throw new ArgumentFormatException("The given arguments are not well " +
                    "formatted. Please provide two double or integer numbers.");
        }
        return new Object[]{opRequest[0], num1, num2};
    }
    private double getResultIfPossible(String line)
            throws IllegalNumberOfArgumentsException, UnknownOperationException,
            ArgumentFormatException{
        line = line.trim();
        String[] opRequest = line.split(" ");
        Object[] formatted = checkRequest(opRequest);
        if (((String) formatted[0]).matches("ADD")) {
            return ((Double) formatted[1]) + ((Double) formatted[2]);
        } else {
            return ((Double) formatted[1]) * ((Double) formatted[2]);
        }
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(
                             new InputStreamReader(
                                     socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(
                             new OutputStreamWriter(
                                     socket.getOutputStream(), UTF_8))) {

                    sendWelcomeMessage(out);
                    String line;
                    while ((line = in.readLine()) != null) {
                        if(line.matches("STOP")){
                            sendByeMessage(out);
                            break;
                        }
                        double res;
                        try{
                            res = getResultIfPossible(line);
                        }catch (MyExceptions ex){
                           out.write(ex.getMessage() + '\n');
                           sendWelcomeMessage(out);
                           out.flush();
                           continue;
                        }
                        out.write(res + "\n");
                        out.flush();
                    }

                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }
}

abstract class MyExceptions extends IllegalArgumentException{
    int errorCode;
    public MyExceptions(){
        super();
    }
    public MyExceptions(String s){
        super(s);
    }
}
class UnknownOperationException extends MyExceptions{
    int errorCode = 1;
    public UnknownOperationException() {
        super();
    }
    public UnknownOperationException(String s) {
        super(s);
    }
    @Override
    public String getMessage(){
        return "Error code: " + errorCode + "\n" + super.getMessage();
    }
}
class ArgumentFormatException extends MyExceptions{
    int errorCode = 2;

    public ArgumentFormatException() {
        super();
    }
    public ArgumentFormatException(String s) {
        super(s);
    }
    @Override
    public String getMessage(){
        return "Error code: " + errorCode + "\n" + super.getMessage();
    }
}
class IllegalNumberOfArgumentsException extends MyExceptions{
    int errorCode = 2;

    public IllegalNumberOfArgumentsException() {
        super();
    }
    public IllegalNumberOfArgumentsException(String s) {
        super(s);
    }
    @Override
    public String getMessage(){
        return "Error code: " + errorCode + "\n" + super.getMessage();
    }
}