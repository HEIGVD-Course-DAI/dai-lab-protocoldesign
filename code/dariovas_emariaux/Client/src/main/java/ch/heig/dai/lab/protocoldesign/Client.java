package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 42069;

    private void run () {
        try (Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(),
                            StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(),
                            StandardCharsets.UTF_8));



            System.out.println("Echo: " + in.readLine());



            out.write("CALCULATION|" + "2+3" + "\n");
            out.flush();
            //out.write("END " + "\n");

            System.out.println(in.readLine());
            out.write("CALCULATION|2*3" + "\n");
            out.flush();
            System.out.println(in.readLine());
            out.write("CALCULATION|10/2" + "\n");
            out.flush();
            System.out.println(in.readLine());
            out.write("CALCULATION|10-2" + "\n");
            out.flush();
            System.out.println(in.readLine());

            out.write("END|" + "\n");

            out.flush();
            clientSocket.close();

        }
        catch(IOException e){
                System.out.println("Client: exc.: " + e);
            }
        }


    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();


        }



}