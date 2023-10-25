package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.*;

public class Client {
    final String SERVER_ADDRESS = "localhost"; // Adresse IP du serveur - 127.0.0.1
    final int SERVER_PORT = 12345; // Port du serveur

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            String line;

            while (true) {

                if ((line = in.readLine()) != null) {
                    System.out.println(line);

                    // Exemple de demande de fermeture de connexion
                    if (line.equals("EXIT")) {
                        out.write("EXIT\n");
                        out.flush();
                        break;
                    }
                }
                System.out.print("Enter a command: ");
                String userInputLine = userInput.readLine();

                // if the user wrote smth
                if (userInputLine != null) {
                    out.write(userInputLine + "\n");
                    out.flush();
                }

            }
        } catch (IOException e) {
            System.out.println("Client: " + e);
        }
    }
}
