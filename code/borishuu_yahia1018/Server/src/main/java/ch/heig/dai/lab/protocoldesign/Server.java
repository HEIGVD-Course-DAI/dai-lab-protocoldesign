package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 54321;

    private static String[] supportedOperators = new String[] {
        "ADD",
        "SUB",
        "MUL",
        "DIV"
    };

    private boolean serverRunning = false;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();


        server.run();
    }

    private void displayWelcomeMessage() {
        System.out.println("Welcome to ptcalc server!");
        System.out.println("The supported operators are the following :");
        for (String operator : supportedOperators) {
            System.out.println(operator);
        }
    }

    private String parseQueryString(String query) {
        String[] queryElements = query.split(" ");
        Boolean operatorFound = false;
        
        for (String operator : supportedOperators) { 
            if (operator.equals(queryElements[0].trim())) {
                operatorFound = true;
                break;
            }
        }

        if (!operatorFound) {
            return "INVALID_QUERY operator";
        }

        String firstOperandString, secondOperandString;
        try {
            firstOperandString = queryElements[1];
            secondOperandString = queryElements[2];
        } catch (Exception ex) {
            return "INVALID_QUERY missing_operand";
        }

        int firstOperand, secondOperand;
        try {
            firstOperand = Integer.parseInt(firstOperandString);
            secondOperand = Integer.parseInt(secondOperandString);
        } catch (Exception ex) {
            return "INVALID_QUERY syntax";
        }

        int result = 0;
        switch (queryElements[0]) {
            case "ADD":
                result = firstOperand + secondOperand;
                break;
            case "SUB":
                result = firstOperand - secondOperand;
                break;
            case "MUL":
                result = firstOperand * secondOperand;
                break;
            case "DIV":
                result = firstOperand / secondOperand;
                break;        
            default:
                result = 0;
                break;
        }

        return "RESULT " + result;
    }

    private void run() {
        displayWelcomeMessage();
        serverRunning = true;

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (serverRunning) {
                try (Socket socket = serverSocket.accept();
                    var in = new BufferedReader(
                        new InputStreamReader(
                            socket.getInputStream(), UTF_8));
                    var out = new BufferedWriter(
                        new OutputStreamWriter(
                            socket.getOutputStream(), UTF_8))) {

                        String line;
                        while ((line = in.readLine()) != null) {
                            String response = parseQueryString(line);
                            out.write(response + "\n");
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