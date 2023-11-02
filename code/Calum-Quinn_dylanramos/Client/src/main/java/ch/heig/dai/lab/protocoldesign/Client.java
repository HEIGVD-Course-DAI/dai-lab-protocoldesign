package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "0.0.0.0";
    final int SERVER_PORT = 32976;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            // Read the welcome message
            String line;
            while ((line = in.readLine()) != null && !line.equals("END")) {
                System.out.println(line);
            }

            // Read user input
            Scanner scanner = new Scanner(System.in);
            String msg;
            while (!Objects.equals(msg = scanner.nextLine(), "EXIT")) {
                // Send the message to the server
                out.write(msg + "\n");
                out.flush();
                // Read the response from the server
                line = in.readLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}