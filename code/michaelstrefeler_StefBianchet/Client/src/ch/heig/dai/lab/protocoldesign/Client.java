package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "127.0.0.1";
    final int SERVER_PORT = 52273;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter a calculation : ");
            String calc = userInput.nextLine() + "\n";
            out.write(calc);
            out.flush();
            System.out.println("Result: " + in.readLine());

        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}