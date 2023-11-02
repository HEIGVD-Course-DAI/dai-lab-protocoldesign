package ch.heig.dai.lab.protocoldesign;

import java.net.*;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.*;

public class Client {
    final String SERVER_ADDRESS = "sever";
    final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        while(true){
            // Create a new client and run it
            Client client = new Client();
            client.run();
        }
    }

    private void run() {
        System.out.println("Client running");

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

            String[] command = in.readLine().split(" ");
            System.out.println("Server commands: ");
            for(int i = 1; i < command.length; i++) {
                System.out.println(command[i]);
            }
            System.out.println("\n");

            System.out.println("Connexion ok");
            boolean quit = false;
            while (true) {
                System.out.println("Entrez votre calcul : ");
                Scanner scanner = new Scanner(System.in);
                String ligne = scanner.nextLine();
                if(Objects.equals(ligne, command[command.length - 1])) {
                    quit = true;
                }

                out.write(ligne + "\n");
                out.flush();
                if(quit) {
                    break;
                }
                System.out.println(in.readLine());
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e);
        }
    }
}