package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 4242;

    public final static String MSG_EXPRESSION_HANDLER = "Client: exception : ";

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream());) {
            Worker wrk = new Worker(out, in);

            for (int i = 0; i < 10; i++) {
                wrk.add(i, i);
                System.out.print(i + " + " + i + " = ");

                String data = wrk.read();
                try {
                    System.out.println(Integer.parseInt(data));
                } catch (NumberFormatException e) {
                    System.out.println(MSG_EXPRESSION_HANDLER + data);
                }
            }
        } catch (IOException ex) {
            System.out.println(MSG_EXPRESSION_HANDLER + ex);
        }
    }
}