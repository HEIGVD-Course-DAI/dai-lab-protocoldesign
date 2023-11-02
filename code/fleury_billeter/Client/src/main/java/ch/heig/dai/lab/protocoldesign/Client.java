package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    static final String SERVER_ADDRESS = "localhost"; // "127.0.0.1"; // "1.2.3.4";
    static int SERVER_PORT = 1234;
    static String endServer = "Guest: >";
    static String sendChar = "\n";

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            String line;
            while (!(line = in.readLine()).equals(endServer)) {
                System.out.println(line);
            }
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    System.out.print("> ");
                    out.write(scanner.nextLine() + sendChar);
                    out.flush();
                    while (!(line = in.readLine()).equals(endServer)) {
                        System.out.println(line);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Client: exception : " + e);
        }
    }
}