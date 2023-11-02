package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;

public class Server {
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server is running on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create input and output streams
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                out.println("Welcome to the Calculator Server!");
                out.println("Supported operations: ADD, MUL");

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    String[] parts = clientMessage.split(" ");
                    if (parts.length >= 3) {
                        String operation = parts[0];
                        int num1 = Integer.parseInt(parts[1]);
                        int num2 = Integer.parseInt(parts[2]);

                        int result = 0;
                        if (operation.equals("ADD")) {
                            result = num1 + num2;
                        } else if (operation.equals("MUL")) {
                            result = num1 * num2;
                        }

                        out.println("Result is: " + result);
                    }
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    } 
}