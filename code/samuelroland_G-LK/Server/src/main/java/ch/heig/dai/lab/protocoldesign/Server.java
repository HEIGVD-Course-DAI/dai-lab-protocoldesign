package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;

public class Server {
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {


        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);) {

            while (true) {
                //create a server to be able to hold client connections

                System.out.println("Server sockt is up");

                try (Socket clientSocket = serverSocket.accept();) {
                    //wait for a client to be connected with the server

                    System.out.println("Server is waiting for clients...");

                } catch (IOException e) {
                    System.out.println("Server: server socket ex.: " + e);
                }
            }

        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }


        //establish the connection with the client
        //Socket socket = new Socket(clientSocket, SERVER_PORT);
        //System.out.println("Connection established with ");


    }


}