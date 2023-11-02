package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "1.2.3.4";
    final int SERVER_PORT = 1234;
    Socket socket;
    OutputStream os;
    OutputStreamWriter OsWriter;
    BufferedReader in;
    BufferedWriter out;
    

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.connect();
        client.run();
        client.disconnect();

    }
    private void connect() {
        try {
            this.socket = new Socket("localhost", this.SERVER_PORT);
            this.os = this.socket.getOutputStream();
            this.OsWriter = new OutputStreamWriter(this.os, StandardCharsets.UTF_8);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(this.os));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
        this.socket.close();
        this.os.close();
        this.in.close();
        this.OsWriter.close();

        } catch (IOException e) {
        e.printStackTrace();
        }
    }
    private void run() {
        try {
            String request = "Hello my friend";
            this.out.write(request);
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}