package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 1234;
    private BufferedWriter out;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void send(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            System.out.println("Server: send ex.: " + e);
        }
    }

    private double getDouble(Scanner s) {
        if (!s.hasNextDouble()) {
            throw new IllegalArgumentException("No double found");
        }
        return s.nextDouble();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8));) {
                    this.out = out;

                    // Welcome message
                    send("Server: Welcome to the calculator server! Usage: <operation> <lhs> <rhs>.");
                    // List available operations
                    StringBuilder sb = new StringBuilder("Available operations:\n");
                    for (Operation op : Operation.values()) {
                        sb.append(" - ").append(op).append(" (").append(op.getSymbol()).append(")\n");
                    }
                    send(sb.toString());

                    String line;
                    while ((line = in.readLine()) != null) {
                        Scanner s = new Scanner(line);
                        String command = s.next();

                        try {
                            // Try to parse the command
                            Operation op = Operation.valueOf(command);

                            // Try to parse the operands
                            double lhs;
                            double rhs;
                            try {
                                lhs = getDouble(s);
                                rhs = getDouble(s);
                            } catch (IllegalArgumentException e) {
                                send("Server: " + e.getMessage() + ". Usage: " + command + " <lhs> <rhs>");
                                continue;
                            }

                            // Compute the result
                            double result = op.applyAsDouble(lhs, rhs);
                            // Send the result
                            send("Server: " + lhs + " " + op.getSymbol() + " " + rhs + " = " + result);
                        } catch (IllegalArgumentException e) {
                            send("Server: Unknown command: " + command);
                        }
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