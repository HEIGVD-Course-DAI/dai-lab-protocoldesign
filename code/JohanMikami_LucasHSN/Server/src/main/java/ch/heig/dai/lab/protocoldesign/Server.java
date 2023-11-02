package src.main.java.ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 54321;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    public String computeResult(String message) {
        // Retrieve each arguments from the message
        String[] arguments = message.split(" ");

        if (arguments.length != 3)
            return "ERROR: Invalid number of arguments";

        if (!arguments[1].matches("-?\\d+") || !arguments[2].matches("-?\\d+"))
            return "ERROR: Invalid arguments";

        String operation = arguments[0];
        int operand1;
        int operand2;
        try {
            operand1 = Integer.parseInt(arguments[1]);
            operand2 = Integer.parseInt(arguments[2]);
        } catch (NumberFormatException e) {
            return "ERROR: Argument too big";
        }

        // Compute the result
        String result;
        switch (operation) {
            case "ADD":
                result = String.valueOf(operand1 + operand2);
                break;
            case "SUB":
                result = String.valueOf(operand1 - operand2);
                break;
            case "MUL":
                result = String.valueOf(operand1 * operand2);
                break;
            case "DIV":
                result = String.valueOf((double) operand1 / (double) operand2);
                break;
            default:
                result = "ERROR: Unknown operation";
                break;
        }
        return result;
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                // Wait for a client to connect
                try (Socket clientSocket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), UTF_8))) {

                    // Send a welcome message to the client with all possible operation (ADD, SUB, MUL, DIV)
                    out.write("Possible operations are: ADD, SUB, MUL, DIV\n");
                    out.flush();

                    // Compute the result
                    String result = computeResult(in.readLine());

                    // Send the result back to the client
                    out.write(result);
                    out.flush();

                    // Close socket
                    clientSocket.close();

                } catch (IOException e) {
                    System.out.println("Server: client socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }
}