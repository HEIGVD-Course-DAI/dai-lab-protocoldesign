package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    static final String SERVER_ADDRESS = "127.0.0.1"; // "1.2.3.4";
    static int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            for (int i = 0; i < 10; ++i) {
                out.write("Hello world " + i + "\n");
                out.flush();
                // System.out.println("Echo: " + in.readLine());
            }
        } catch (IOException e){
            System.out.println("Client: exception : " + e);
        }
    }
}