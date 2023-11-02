package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.net.*;

import static java.lang.System.in;


public class Client {
    final String SERVER_ADDRESS = "127.0.0.1";
    final int SERVER_PORT = 23997;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private void run()  {
        System.out.println("Welcome to the CalculazorDX, our new HIGH tech cloud computing device \n" +
                "Please tell us which operation you want to do (add, sub, div, mult) : ");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(in))) {
            String operation;

            do {
                // Reading data using readLine
                String op = reader.readLine();

                operation = switch (op) {
                    case "add"  -> "ADD ";
                    case "sub"  -> "SUB ";
                    case "mult" -> "MULT ";
                    case "div"  -> "DIV ";
                    default     -> null;
                };


                if(operation == null){
                    System.out.println("Wrong operation! Please specify one of the four operation (add, sub, div, mult) : ");
                }

            } while (operation == null);

            System.out.println("Now tell me the first number you want to compute : ");

            String num1;
            while(true) {

                num1 = reader.readLine();

                if(isNumeric(num1)){
                    break;
                }
                System.out.println("Please enter a number ! : ");
            }

            System.out.println("Now tell me the second number you want to compute : ");

            String num2;

            while(true) {

                num2 = reader.readLine();

                if(isNumeric(num2)){
                    break;
                }
                System.out.println("Please enter a number ! : ");
            }

            try (Socket socket = new Socket(SERVER_ADDRESS,SERVER_PORT);
                var in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
                var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8))){

                out.write(operation + num1 + ' ' + num2);
                System.out.println("Command sent : " + operation + num1 + ' ' + num2);

                System.out.println("Result is : " + in.readLine());

                } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);

            }
            } catch (IOException ex) {
                System.out.println("Client: exc.: " + ex);
            }
        }







        }

