package ch.heig.dai.lab.protocoldesign;

//import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import ch.heig.dai.lab.protocoldesign.models.Calculate;


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
            // Create a JSON request using Gson
            int[] values = {1, 2};
            Calculate request = new Calculate(values, "add");
//            Gson gson = new Gson();
//            String jsonRequest = gson.toJson(request);
//
//            // Send the JSON request to the server
//            out.println(jsonRequest);
//
//            // Receive and parse the JSON response from the server
//                String jsonResponse = in.readLine();
            //Calculate response = gson.fromJson(jsonResponse, Calculate.class);
//            int result = response.getResult();
            int result = 0;
            String response = "";
            System.out.println("Result: " + result);
            out.flush();
            System.out.println("Client: " + response);
        }  catch (IOException e){
            System.out.println("Client: exc.: " + e);
        }

    }

}