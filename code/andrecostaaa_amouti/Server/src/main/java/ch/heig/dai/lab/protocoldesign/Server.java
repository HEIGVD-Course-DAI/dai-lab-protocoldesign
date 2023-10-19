package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Server {
    final int SERVER_PORT = 6900;
    final private CommandHandler commandHandler;
    private ArrayList<Thread> clientThreads;
    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private Server() {
        commandHandler = new CommandHandler();
        clientThreads = new ArrayList<Thread>();

    }

    private void run() {
        System.out.println("Server is now running. Listening on port " + SERVER_PORT);
        try (var serverSocket = new ServerSocket(SERVER_PORT)) {
            handleClientConnections(serverSocket);
        } catch (Exception e) {
            System.out.println("Exception while creating Server Socket > " + e);
        }
    }

    private void handleClientConnections(ServerSocket serverSocket) {
        while (true) {
            try  {
                Socket socket = serverSocket.accept();
                var clientHandler = new ClientHandler(socket, commandHandler);
                var clientThread = new Thread(clientHandler);
                clientThreads.add(clientThread);
                clientThread.start();

            } catch (IOException e) {
                System.out.println("Exception while waiting for client connection > " + e);
            }
        }
    }




}