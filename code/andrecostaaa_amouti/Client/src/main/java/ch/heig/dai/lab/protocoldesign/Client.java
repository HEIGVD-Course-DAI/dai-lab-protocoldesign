package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 6900;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try(Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))){

            String line;
            line = in.readLine().replace('|', '\n');
            System.out.println(line);

            do{
                System.out.println("\nEnter a command: ");
                line = userInput.readLine();
                out.write(line + '\n');
                out.flush();
                String response = in.readLine().replace('|', '\n');
                System.out.println(response);
            }
            while(!line.equals("quit"));

        }catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}