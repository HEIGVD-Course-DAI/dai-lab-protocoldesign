package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 4242;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        //Copy of given example on side:
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             InputStream in = new BufferedInputStream(socket.getInputStream());
             OutputStream out = new BufferedOutputStream(socket.getOutputStream());) {
            for (int i = 0; i < 10; i++) {
                out.write(i);
                out.flush();
                System.out.println("Echo: " + in.read());
            }
        } catch (IOException ex) {
            System.out.println("Client: exception : " + ex);
        }
    }
}