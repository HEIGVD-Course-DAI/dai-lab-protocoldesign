package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class TextualTCPClient {
 public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);           
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){


                for (int k = 0 ; k < 11 ; ++k){
                      String z;
                      z = in.readLine();
                      System.out.println(z);
                }
            


            Scanner scanner = new Scanner(System.in);
            String input;
            System.out.println("Entrez vos calculs (<opérande> <commande> <opérande>) ou 'bye' pour terminer :");

            while (true) {
                input = scanner.nextLine();

                out.write(input +"\n");
                out.flush();

                // Lire et afficher la réponse du serveur
                String response = in.readLine();
                System.out.println("Réponse du serveur : " + response);
            }

        }

        catch (IOException e) {
            System.err.println("Erreur d'E/S : " + e.getMessage());
        }
    }
}