package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 4269;
    static boolean quit = false;


    public static void main(String[] args) {
        // Create a new client and run it
        while (!quit){
            Client client = new Client();
            client.run();
        }
    }

    private void run() {
        System.out.println("Client ready");
        try {
            Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

            out.write("INIT" + "\n");
            out.flush();

            String[] init = in.readLine().split(" ");
            System.out.println("Welcome ! Here are the available operations :");
            for(String i : init){
                System.out.println(i);
            }

            while(!quit){
                System.out.println("Enter your calculation : ");
                BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                String userInputString = stdin.readLine();
                out.write(userInputString + "\n");
                out.flush();

                String result = in.readLine();

                System.out.println("Server response: " + result);
                if(result.equals(" ")){
                    quit = true;
                    clientSocket.close();
                    break;
                }
            }

        }catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }
    }
}