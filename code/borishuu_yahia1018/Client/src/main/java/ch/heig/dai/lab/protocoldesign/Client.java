package ch.heig.dai.lab.protocoldesign;
import javax.xml.stream.events.StartDocument;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "1.2.3.4";
    final int SERVER_PORT = 54321;
    final String[] SUPPORTED_OPERATIONS = {"ADD", "SUB", "MUL", "DIV"};

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void welcomeMessage() {
        System.out.print("Supported operations: " + SUPPORTED_OPERATIONS[0]);
        for (int i = 1; i < SUPPORTED_OPERATIONS.length; ++i)
            System.out.print(", " + SUPPORTED_OPERATIONS[i]);
        System.out.print("\n");
    }

    private void run() {
        welcomeMessage();

        try (Socket socket = new Socket("localhost", SERVER_PORT)) {
            Scanner userInput = new Scanner(System.in);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            while (true) {
                System.out.println("Entrez une operation que vous souhaitez effectuer: ");

                out.write(userInput.nextLine() + '\n');
                out.flush();

                System.out.println("Server response: " + in.readLine());
            }
        } catch (IOException e) {
            System.out.println("Client: exception: " + e);
        }
    }
}