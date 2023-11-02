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
        String operation = arguments[0];
        /// TODO Verifier que les arguments sont bien des entiers et que si c'est pas le cas, pose pas de problème
        int operand1 = Integer.parseInt(arguments[1]);
        int operand2 = Integer.parseInt(arguments[2]);

        // Compute the result
        String result = "";
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

                    /// TODO : Print possible operation as welcome message

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