package ch.heig.dai.lab.protocoldesign;

//import com.google.gson.Gson;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import ch.heig.dai.lab.protocoldesign.*;
import com.google.gson.Gson;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 6666;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); var in = new BufferedReader( new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        var out = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))){
            // Read the Json operation from server
            String jsonOperation = in.readLine();
            //print in console the json operations received
            System.out.println("Server: " + jsonOperation);

            // Ask the server to calculate 1 + 2


            // Create a JSON request using Gson

            Request request = new Request(Type.ADD, 1., 2.);
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(request);

            // Send the JSON request to the server
            out.write(jsonRequest);
            out.newLine();
            // Flush the output stream
            out.flush();

            // Read the JSON response from the server
            String jsonResponse = in.readLine();
            Result response = gson.fromJson(jsonResponse, Result.class);
            // Print the result
            System.out.println("Result: " + response.getResult());
        }  catch (IOException e){
            System.out.println("Client: exc.: " + e);
        }

    }

}