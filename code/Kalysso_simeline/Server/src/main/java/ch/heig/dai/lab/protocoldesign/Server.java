package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.*;

public class Server {

    final int SERVER_PORT = 12345;

    /***
     * 12345 est le port sur lequel le serveur écoute les connexions entrantes,
     * tandis que 64009 est le port local attribué par l'OS à la connexion entre le client et le serveur.
     */


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

                    out.write("CONNECTED <add, mul, sub, div, mod>\n");
                    out.flush();

                    String line;

                    while ((line = in.readLine()) != null) {


                        if (line.startsWith("OPERATION")) {

                            String[] parts = line.split(" "); // Split the message and perform the operation
                            if (parts.length == 4) {

                                    String operation = parts[1];
                                    double operand1 = Double.parseDouble(parts[2]);
                                    double operand2 = Double.parseDouble(parts[3]);

                                    double result = calculate(operation, operand1, operand2);

                                    if (result == Integer.MAX_VALUE) {
                                        out.write("COMPUTATION FAILED" + '\n');
                                        out.flush();
                                    }
                                    else if (result == Integer.MAX_VALUE - 1) {
                                        out.write("UNKNOWN OPERATION" + '\n');
                                        out.flush();
                                    }
                                    else {
                                        out.write(Double.toString(result) + '\n');
                                        out.flush();
                                    }
                            }
                            else {
                                out.write("UNKNOWN OPERATION" + '\n');
                                out.flush();
                            }
                        }
                        else if (line.equals("EXIT")) {
                            out.write("EXIT" + '\n');
                            System.out.println("EXIT");
                            out.flush();
                            break;
                        }
                        else {
                            System.out.println("smth else");
                            out.write("UNKNOWN OPERATION" + '\n');
                            out.flush();
                        }
                    }
                }
                catch(IOException e){
                    System.out.println("Server: " + e);
                    e.printStackTrace();
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
                    return Integer.MAX_VALUE;
                    //System.out.println("COMPUTATION FAILED");
                }
            case "mod":
                if (operand2 != 0) {
                    return operand1 % operand2;
                } else {
                    return Integer.MAX_VALUE;
                    //System.out.println("COMPUTATION FAILED");
                }
            default:
                return Integer.MAX_VALUE - 1;
                //System.out.println("UNKNOWN OPERATION");
        }
    }
}