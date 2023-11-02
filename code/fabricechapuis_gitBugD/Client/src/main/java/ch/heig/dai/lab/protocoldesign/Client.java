package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "1.2.3.4";
    final int SERVER_PORT = 1234;
    Socket socket;
    OutputStream out;
    OutputStreamWriter OsWriter;
    BufferedReader in;
    

    public static void main(String[] args) throws UnknownHostException, IOException {
        // Create a new client and run it
        Client client = new Client();
        client.connect();
        client.run();
        client.disconnect();

    }
    private void connect() {
        try {
            this.socket = new Socket(this.SERVER_ADDRESS, this.SERVER_PORT);
            this.out = this.socket.getOutputStream();
            this.OsWriter = new OutputStreamWriter(this.out, StandardCharsets.UTF_8);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
        this.socket.close();
        this.out.close();
        this.in.close();
        this.OsWriter.close();

        } catch (IOException e) {
        e.printStackTrace();
        }
    }
    private void run() {
        try {
            String response = in.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}