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
            out.write(message + "\n\n");
            out.flush();
        } catch (IOException e) {
            System.err.println("Output buffer write error: " + e);
        }
    }

    private double getOperand(Scanner s) {
        if (!s.hasNextDouble()) {
            throw new IllegalArgumentException("No operand found");
        }
        return s.nextDouble();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {
                    this.out = out;

                    // Welcome message
                    StringBuilder sb = new StringBuilder();
                    sb.append("Welcome to the calculator server! Usage: <operation> <lhs> <rhs>.\n");
                    sb.append("Available operations:");
                    for (Operation op : Operation.values()) {
                        sb.append("\n - ").append(op).append(" (").append(op.getSymbol()).append(")");
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
                                lhs = getOperand(s);
                                rhs = getOperand(s);
                            } catch (IllegalArgumentException e) {
                                send(e.getMessage() + ". Usage: " + command + " <lhs> <rhs>");
                                continue;
                            }

                            // Compute the result
                            double result = op.applyAsDouble(lhs, rhs);
                            // Send the result
                            send(lhs + " " + op.getSymbol() + " " + rhs + " = " + result);
                        } catch (IllegalArgumentException e) {
                            send("Unknown command: " + command);
                        }
                    }

                } catch (IOException e) {
                    System.err.println("Client socket error: " + e);
                }
            }
        } catch (IOException e) {
            System.err.println("Server socket error: " + e);
        }
    } 
}