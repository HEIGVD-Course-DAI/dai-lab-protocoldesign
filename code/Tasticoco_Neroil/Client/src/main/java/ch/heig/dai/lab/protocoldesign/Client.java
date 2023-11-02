package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "1.2.3.4";
    final int SERVER_PORT = 23997;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run()  {
        System.out.println("Welcome to the CalculazorDX, our new HIGH tech cloud computing device \n" +
                "Please tell us which operation you want to do (add, sub, div, mult) : ");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));) {
            Operation operation;

            do {
                // Reading data using readLine
                String op = reader.readLine();

                operation = switch (op) {
                    case "add"  -> Operation.ADD;
                    case "sub"  -> Operation.SUB;
                    case "mult" -> Operation.MULT;
                    case "div"  -> Operation.DIV;
                    default     -> Operation.NULL;
                };


                if(operation == Operation.NULL){
                    System.out.println("Wrong operation! Please specify one of the four operation (add, sub, div, mult) : ");
                }

            } while (operation == Operation.NULL);



        }

        catch (IOException e) {
            System.out.println("Client: exception: " + e);
        }
    }
}
