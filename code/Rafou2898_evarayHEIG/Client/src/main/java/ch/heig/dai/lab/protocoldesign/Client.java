package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 2828;
    final String WELCOME_MSG = "WELCOME";
    final String ERROR_UNKOWN = "UNKOWN";
    final String ERROR_INVALID = "INVALID";

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {

            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                 var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                 var scanner = new Scanner(System.in)) {

                String serverMessage = in.readLine();
                String userInput;
                String result;
                System.out.println("Server: " + serverMessage);

                if (serverMessage.contains(WELCOME_MSG)) {

                    while (true) {

                        System.out.println("Enter your calculation: ");
                        userInput = scanner.nextLine();

                        if(userInput.equalsIgnoreCase("quit")){
                            break;
                        }

                        out.write(userInput + "\n");
                        out.flush();

                        result = in.readLine();

                        if (result.equals(ERROR_INVALID)) {
                            throw new IOException("Client: One of the operand isn't a number \n");
                        }

                        if (result.equals(ERROR_UNKOWN)) {
                            throw new IOException("Client: The operation you asked for does not exist \n");
                        }

                        System.out.println("Here is the result of the calculation: " + result);
                    }
                }
            } catch (IOException e) {
                System.out.println("Client: exception while using client socket: " + e);
            }
        }

}