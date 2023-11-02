package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try {
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                System.out.println("Server is listening on port " + SERVER_PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                String[] parts = request.split(" ");
                if (parts.length == 3) {
                    try {
                        double operand1 = Double.parseDouble(parts[1]);
                        double operand2 = Double.parseDouble(parts[2]);

                        if (parts[0].equalsIgnoreCase("ADD")) {
                            double result = operand1 + operand2;
                            out.println("Result: " + result);
                        } else if (parts[0].equalsIgnoreCase("SUB")){
                            double result = operand1 - operand2;
                            out.println("Result: " + result);
                        } else if (parts[0].equalsIgnoreCase("MUL")) {
                            double result = operand1 * operand2;
                            out.println("Result: " + result);
                        }
                         else {
                            out.println("Unknown operation");
                        }
                    } catch (NumberFormatException e) {
                        out.println("Invalid operands");
                    }
                } else {
                    out.println("Invalid request format");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}