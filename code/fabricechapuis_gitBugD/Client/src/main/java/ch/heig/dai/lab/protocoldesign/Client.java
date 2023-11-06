package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

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

    private boolean sendMessage(String message) {
        try {
            this.out.write(message);
            this.out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private List<String> initiateConversation() {
        try {
            // Getting the list of available operations
            String response = this.in.readLine();
            List<String> operations = Arrays.asList(response.split(" "));
            System.out.print("Available operations: ");
            for (String operation : operations) {
                System.out.print(operation + " ");
            }
            System.out.println();
            return operations;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void run() {
            List<String> operations = initiateConversation();

            Scanner input = new Scanner(System.in);
            String operation = "";
            try {
                while (!Objects.equals(operation, "STOP")) {
                    System.out.println("Enter an operation: ");
                    operation = input.nextLine();
                    if (!operations.contains(operation.split(" ")[0]) && !operation.equals("STOP")) {
                        System.out.println("Invalid operation: " + operation);
                        continue;
                    }
                    this.sendMessage(operation);
                    String response = this.in.readLine();
                    System.out.println("Response: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                input.close();
            }
    }
}