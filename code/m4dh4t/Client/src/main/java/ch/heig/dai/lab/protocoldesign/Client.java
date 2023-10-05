package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 1234;
    private BufferedReader in;
    private BufferedWriter out;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void send(String message) {
        try {
            out.write(message + "\n");
            out.flush();
            receive();
        } catch (IOException e) {
            System.err.println("Output buffer write error: " + e);
        }
    }

    private void receive() {
        try {
            String line;
            System.out.print("Server: ");
            while ((line = in.readLine()) != null) {
                if (line.isEmpty()) {
                    System.out.println();
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Input buffer read error: " + e);
        }
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            // Empty initial buffer filled with welcome message
            receive();

            // Ask for user input
            while (true) {
                Scanner reader = new Scanner(System.in);
                System.out.print("Client: ");
                String line = reader.nextLine();
                send(line);
            }
        } catch (IOException e) {
            System.err.println("Client socket error: " + e);
        }
    }
}