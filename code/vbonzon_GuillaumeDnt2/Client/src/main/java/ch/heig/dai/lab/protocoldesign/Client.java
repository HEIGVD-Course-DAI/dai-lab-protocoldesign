package ch.heig.dai.lab.protocoldesign;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 31415;
    final String[] OPERATIONS = {"ADD", "SUB"};
    final Charset ENCODING = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        //Display the title
        System.out.print("----------------------------------------\n" +
                         "|            Super Calculator          |\n" +
                         "----------------------------------------\n");


        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        //Ask for the server adresse
        System.out.print("Entre server adresse (by default localhost): ");
        Scanner scn = new Scanner(System.in);
        String result = scn.nextLine();

        if(!result.isEmpty()){
            SERVER_ADDRESS = result;
        }

        //Connect to the server
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), ENCODING));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), ENCODING));

            //Display the available operations
            System.out.println(in.readLine());
            
            //Start the looping for command to send
            while(!socket.isClosed()){
                //Get the command from the user and sends it
                System.out.print("Enter your command: ");
                // Scanner scn = new Scanner(System.in);
                out.write(scn.nextLine() + "\n");
                out.flush();

                //Wait for the server result
                result = in.readLine();

                //End the connection if the command is CLOSE
                if(result.equals("CLOSING")){
                    System.out.println("Disconnecting ...");
                    //socket.close();
                    break;
                }

                //Display the result
                System.out.println(result);
            }
        } catch (IOException e){
            System.out.println("An exception happened!");
        }
    }
}