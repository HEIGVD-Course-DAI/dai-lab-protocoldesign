package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

class TextualTCPServer {
 public static void main(String[] args) {
       
    try (ServerSocket serverSocket = new ServerSocket(12345)) {
            
            System.out.println("Serveur en attente de connexions...");

    //TODO: implement: * timeout
    //                 * correct parsing
    //                 * implement power fct in switch case
            while (true) {

                try(Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), UTF_8));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), UTF_8))) {



                System.out.println("Client connecté.");  
                out.write("\n\n|----Bienvenue chez le supercalculateur des services secrets suisses !----|\n\n");
                out.write("Voici les commandes supportées: \r\n* Multiplication (*)\r\n* Division (/)\r\n* Addition (+)\r\n* Substraction(-)\r\n* Modulus (%)\r\n* Powe(pow<base>-<exposent>)\r\n");
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
                    double operand1 = Double.parseDouble(input.substring(0, 1));
                    double operand2 = Double.parseDouble(input.substring(2,3));
                    String operator = input.substring(1, 2);
                    double result;



                    switch (operator) {
                        case "+":
                            result = operand1 + operand2;
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
                                result = Double.NaN; // Indiquer une division par zéro
                                out.write("ERROR:div0\n");
                                out.flush();

                            }
                            continue;
                        default:
                            result = Double.NaN; // Indiquer une opération invalide
                            out.write("ERROR:invalid expression\n");
                            out.flush();
                    }



                    out.write("Résultat : " + result + "\n");
                    out.flush();
                }

                System.out.println("Session terminée.");
                
                }
            }
        }

        catch (IOException e) {
            System.err.println("Erreur d'E/S : " + e.getMessage());
        }
    }

    }
