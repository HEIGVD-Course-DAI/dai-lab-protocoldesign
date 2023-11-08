package ch.heig.dai.lab.protocoldesign;

import ch.heig.dai.lab.protocoldesign_common.Operation;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Client {
    private final int SERVER_PORT = 4242;
    private final Charset charset = StandardCharsets.UTF_8;
    private final char LINE_DELIMITER = ';';

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        // Get server adress from user
        String serverAddress = askServerAddress();

        while (true) {
            // Ask user for a calculation
            String calculation = askCalculation();

            // Parse the calculation
            Operation operation;
            try {
                operation = new Operation(calculation);
            } catch (Exception e) {
                System.out.println("Invalid calculation");
                continue;
            }

            // Open a connection to the server
            try (Socket socket = new Socket(serverAddress, SERVER_PORT);
                 var in = new BufferedReader(
                         new InputStreamReader(socket.getInputStream(),
                                 charset));
                 var out = new BufferedWriter(
                         new OutputStreamWriter(socket.getOutputStream(),
                                 charset))) {

                // Send the calculation to the server
                out.write(operation.toString());
                out.write(LINE_DELIMITER);
                out.flush();

                // Read the result from the server
                StringBuilder sb = new StringBuilder();
                int c;
                while ((c = in.read()) != LINE_DELIMITER) {
                    sb.append((char)c);
                }
                String result = sb.toString();

                // Convert string to operation result
                if (result.startsWith("ERROR")) {
                    System.out.println("Error: " + result.substring(6));
                } else if (result.startsWith("RSLT")) {
                    System.out.println("Result: " + result.substring(5));
                } else {
                    System.out.println("Invalid result");
                }
            } catch (Exception e) {
                System.out.println("Error while connecting to the server");
            }
        }
    }

    private String askServerAddress() {
        System.out.println("Please enter the server address:");
        return System.console().readLine();
    }

    private String askCalculation() {
        System.out.println("Please enter a calculation:");
        return System.console().readLine();
    }
}