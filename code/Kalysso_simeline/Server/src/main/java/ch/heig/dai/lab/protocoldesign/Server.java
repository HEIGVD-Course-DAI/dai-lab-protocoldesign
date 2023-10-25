package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.*;

public class Server {

    final int SERVER_PORT = 12345;


    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Server is running...");

            while (true) {

                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), UTF_8));
                     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), UTF_8))) {

                    System.out.println("New client connected: " + clientSocket);

                    out.write("CONNECTED add, mul, sub, div, mod\n");
                    out.flush();

                    String line;

                    while ((line = in.readLine()) != null) {


                        if (line.startsWith("OPERATION")) {

                            String[] parts = line.split(" "); // Split the message and perform the operation
                            if (parts.length == 4) {
                                try {

                                    String operation = parts[1];
                                    double operand1 = Double.parseDouble(parts[2]);
                                    double operand2 = Double.parseDouble(parts[3]);

                                    double result = calculate(operation, operand1, operand2);

                                    out.write(Double.toString(result) + '\n');
                                    out.flush();

                                } catch (NumberFormatException e) {
                                    out.write("ERROR UNKNOWN OPERATION\n");
                                    out.flush();
                                }
                            }
                        }
                        else if (line.equals("EXIT")) {
                            break;
                        }
                    }
                }
                catch(IOException e){
                    System.out.println("Server: " + e);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Server: " + e);
            e.printStackTrace();
        }
    }

    private double calculate(String operation, double operand1, double operand2) {

        switch (operation) {
            case "add":
                return operand1 + operand2;
            case "sub":
                return operand1 - operand2;
            case "mul":
                return operand1 * operand2;
            case "div":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new ArithmeticException("COMPUTATION FAILED");
                }
            case "mod":
                if (operand2 != 0) {
                    return operand1 % operand2;
                } else {
                    throw new ArithmeticException("COMPUTATION FAILED");
                }
            default:
                throw new IllegalArgumentException("UNKNOWN OPERATION");
        }
    }
}