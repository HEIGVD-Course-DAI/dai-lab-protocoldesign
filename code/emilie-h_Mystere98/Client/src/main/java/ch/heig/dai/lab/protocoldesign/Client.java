package ch.heig.dai.lab.protocoldesign;

//import com.google.gson.Gson;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import com.google.gson.Gson;

import static java.lang.System.exit;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 6666;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);                 var br = new BufferedReader(new InputStreamReader(System.in));
             var in = new BufferedReader( new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        var out = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))){
            System.out.println("Connected to server");
            System.out.println("Write exit to quit");
            // Read the Json operation from server
            String line = in.readLine();

            Gson gson = new Gson();
            Operation operation = gson.fromJson(line, Operation.class);
            System.out.println("Operation: " + Arrays.toString(operation.getOperations()));

            // example of valid request
            Request r = new Request(Type.ADD, 1., 2.);
            //Gson gson = new Gson();
            System.out.println("Request example:\n" + gson.toJson(r));
            System.out.println("-------------------");

            String readLine = "";
            while(!readLine.equals("exit")){

                //read from console
                readLine = br.readLine();
                System.out.println("readLine: " + readLine);
                System.out.println("Sending request");
                // Send the JSON request to the server
                out.write(readLine);
                out.newLine();
                out.flush();

                // Read the JSON response from the server
                String jsonResponse = in.readLine();
                Result response = gson.fromJson(jsonResponse, Result.class);
                // Print the result
                System.out.println("Result: " + response.getResult());
            }

        }  catch (IOException e){
            System.out.println("Client: exc.: " + e);
            exit(1);
        }

    }

}