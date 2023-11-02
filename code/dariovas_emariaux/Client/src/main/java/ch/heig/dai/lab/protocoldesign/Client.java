package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 42069;

    private void run () {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(),
                            StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(),
                            StandardCharsets.UTF_8));
            while(true) {
                // There are two errors here!

                System.out.println("Echo: " + in.readLine());
                out.write("CALCULATION ADD (2 3)");
                out.flush();
            } }
        catch(IOException e){
                System.out.println("Client: exc.: " + e);
            }
        }


    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();


        }



}