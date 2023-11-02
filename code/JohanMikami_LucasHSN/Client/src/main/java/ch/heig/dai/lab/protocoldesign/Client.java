package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 54321;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try(Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){

            // Print the welcome message from the server
            System.out.println(in.readLine());

            System.out.println("Write your calculation :");
            String str;
            do{
                str = br.readLine();
            }while (str.isEmpty());

            out.write(str + "\n");
            out.flush();

            System.out.println("Result : " + in.readLine());

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}