package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 1234;
    static boolean dontStop = true;

    public static void main(String[] args) {
       // Create a new client and run it
        while(dontStop){
            Client client = new Client();
            client.run();
        }
    }

    private void run() {
        System.out.println("Client running");
        try (
            Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),StandardCharsets.UTF_8));            
            var out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),StandardCharsets.UTF_8))){ 
            out.write("INIT" + "\n");
            out.flush();
            String[] init = in.readLine().split(" ");
            System.out.println("Hello, here's the possible operation: ");
            for(String i : init){
                System.out.println(i);
            }            
            while(dontStop){
                System.out.println("Enter your calculation : ");
                BufferedReader  userInput = new BufferedReader(new InputStreamReader(System.in));
                String userInputString = userInput.readLine();
                out.write(userInputString + "\n");
                out.flush(); 
                
                
                String result = in.readLine();
                
                System.out.println("Server response: " + result);
                if(result.equals("Program exited")){
                    dontStop = false;
                    clientSocket.close();
                    break;                  
                }
                
            }
        }
        catch (IOException e) {
            System.out.println("Client: exception : " + e);
        }
    }
}