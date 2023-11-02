package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;

import static java.lang.Character.isDigit;
import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 42069;

    private enum Operation  {
        ADD('+'),
        SUB('-'),
        DIV('/'),
        MULT('*'),
        POW('^'),
        BRACKET_OPEN('('),
        BRACKET_CLOSE(')');

        public final char label;
        Operation(char label) {
            this.label = label;
        }
    }

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server up and running on port " + SERVER_PORT);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(
                             new InputStreamReader( socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter( new OutputStreamWriter(
                             socket.getOutputStream(), UTF_8))){

                    // Send WELCOME message (with the possible operators) to the clients on new connection
                    out.write("WELCOME 4*(12 + 12)| " + getOperations() + "\n");
                    out.flush();

                    while (true){
                        String msg = in.readLine();
                        String[] msgParts = msg.split("\\|");

                        if(msg == null)
                            break;

                        for(String i : msgParts){
                            System.out.println(i);
                        }

                        switch (msgParts[0]){
                            case "CALCULATION":
                                out.write("RESULT|" + calculate(msgParts[1]) + "\n");
                                break;
                            case "":
                                break;
                        }
                    }

                }
                catch (IOException e){
                    System.out.println("Server: server socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }

    private String getOperations(){
        StringBuilder operation = new StringBuilder();

        for (Operation op : Operation.values()){
            operation.append(op.label).append(" ");
        }

        return operation.toString();
    }

    private int calculate(String str){
        if(!(isDigit(str.charAt(0)) && isDigit(str.charAt(2)))){
            throw new RuntimeException("Not a number");
        }

        int n1 = str.charAt(0);
        int n2 = str.charAt(2);
        char op = str.charAt(1);

        if(op == Operation.ADD.label){
            return n1 + n2;
        } else if (op == Operation.SUB.label) {
            return n1 - n2;
        }
        else if (op == Operation.DIV.label){
            return n1 / n2;
        }
        else if (op == Operation.MULT.label){
            return n1 * n2;
        } else if (op == Operation.POW.label) {
            return n1 ^ n2;
        }else {
            throw new RuntimeException("OPERATION_NOT_VALID");
        }
    }
}