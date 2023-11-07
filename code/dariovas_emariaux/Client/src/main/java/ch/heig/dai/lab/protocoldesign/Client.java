package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 42069;

    private void run () {
        try (Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(),
                            StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(),
                            StandardCharsets.UTF_8));


            String input;
            Scanner console = new Scanner(System.in);

            do {
                System.out.println(in.readLine());
                input = console.nextLine();
                out.write(input + "\n");
                out.flush();
            }while(!input.contains("END"));



        }
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