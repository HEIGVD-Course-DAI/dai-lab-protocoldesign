package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

import static java.nio.charset.StandardCharsets.*;

class Server {
 public static void main(String[] args) {
       
    try (ServerSocket serverSocket = new ServerSocket(12345)) {
            
            System.out.println("Serveur en attente de connexions...");

            while (true) {

                try(Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), UTF_8));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), UTF_8))) {

                clientSocket.setSoTimeout(300000);

                System.out.println("Client connecté.");  
                out.write("\n\n|----Bienvenue chez le supercalculateur des services secrets suisses !----|\n\n");
                out.write("Voici les commandes supportées: \r\n* Multiplication (*)\r\n* Division (/)\r\n* Addition (+)\r\n* Soustraction(-)\r\n* Modulo (%)\r\n");
                out.flush();


                String input;
                while ((input = in.readLine()) != null) {

                    if (input.equals("bye")) {
                        break;
                    }
                    // Analyser la commande et effectuer le calcul
                    if (input.length() < 3){
                        out.write("ERROR:invalid expression\n");
                        out.flush();
                        continue;
                    }

                    int indexOperator = -1;
                    String[] charSet = new String[5];
                    charSet[0] = "*";
                    charSet[1] = "-";
                    charSet[2] = "/";
                    charSet[3] = "+";
                    charSet[4] = "%";
                    //Aller chercher l'index de terminaison de la première opérande
                    for (int k = 0 ; k < 5 && indexOperator == -1 ; ++k){
                        indexOperator = input.indexOf(charSet[k]);
                    }
                    //Parser chaque sous-string pour extraire les opérandes et opérateur
                    double operand1 = Double.parseDouble(input.substring(0, indexOperator));
                    String operator = input.substring(indexOperator, indexOperator+ 1);
                    double operand2 = Double.parseDouble(input.substring(indexOperator + 1));
                    double result;



                    switch (operator) {
                        case "+":
                            result = operand1 + operand2;
                            break;
                        case "%":
                            result = operand1 % operand2;
                            break;
                        case "-":
                            result = operand1 - operand2;
                            break;
                        case "*":
                            result = operand1 * operand2;
                            break;
                        case "/":
                            if (operand2 != 0) {
                                result = operand1 / operand2;
                            } else {
                                out.write("ERROR:div0\n");
                                out.flush();
                                continue;
                            }
                            break;
                        default:
                            result = Double.NaN; // Indiquer une opération invalide
                            out.write("ERROR:invalid expression\n");
                            out.flush();
                    }

                    out.write("Résultat : " + result + "\n");
                    out.flush();


                }

                out.write("Session terminée.");
                out.flush();
                System.out.println("Session terminée.");
                
                }
            }



        }
        catch (SocketTimeoutException e){
            System.out.println("Timeout écoulé\n");
        }
        catch (IOException e) {
            System.err.println("Erreur d'E/S : " + e.getMessage());
        }
    }


}
