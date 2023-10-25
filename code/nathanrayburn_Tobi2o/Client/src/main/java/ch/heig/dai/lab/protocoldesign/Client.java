package ch.heig.dai.lab.protocoldesign;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "1.2.3.4"; // Assurez-vous d'utiliser l'adresse IP correcte du serveur.
    final String SERVER_LOCALHOST = "localhost";
    final int SERVER_PORT = 1234; // Ce doit être le même port que celui écouté par votre serveur.
    public static void logMessage(String s){
        System.out.println(s+"\n");
    }
    public static void main(String[] args) {
        // Créer un nouveau client et le lancer
        Client client = new Client();
        client.run();
    }

    private void run() {
        // Initialiser la socket pour communiquer avec le serveur


        try (Socket socket = new Socket(SERVER_LOCALHOST, SERVER_PORT))
        {

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);  // Scanner pour lire l'entrée de l'utilisateur
            String userInput;

            // Lire le message de bienvenue du serveur
            String messageFromServer = in.readLine();


            logMessage("Server: " + messageFromServer);

            while (true) {
                System.out.println("Enter operation and numbers in format: [operation];[num1];[num2] (e.g., add 5 3) or 'exit' to quit");
                userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput.trim())) {
                    out.write("exit\n");  // Informer le serveur de la fin de la connexion.
                    out.flush();
                    break;
                }

                // Envoyer la demande de calcul au serveur
                out.write(userInput);
                out.flush(); // Il est important de vider le tampon, sinon le message pourrait ne pas être envoyé immédiatement.

                // Obtenir la réponse du serveur
                String response = in.readLine();
                if (response != null) {
                    System.out.println("Server ( " +SERVER_LOCALHOST + ":" + SERVER_PORT +" ) response -> " + response);
                } else {
                    System.out.println("Server connection was lost.");
                    break; // Sortir de la boucle si la connexion est perdue
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + SERVER_PORT + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}