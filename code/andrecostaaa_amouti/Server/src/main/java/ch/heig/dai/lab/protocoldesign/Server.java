package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Server {
    final int SERVER_PORT = 6900;
    final private CommandHandler commandHandler;
    private static final String MESSAGE_END = "\n";
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
            try (var socket = serverSocket.accept()) {
                clientThreads.add(new Thread(handleClient, socket));
                return;
            } catch (IOException e) {
                System.out.println("Exception while waiting for client connection > " + e);
            }
        }
    }

    private void handleClient(Socket clientSocket) {

        if (clientSocket == null)
            return;
        while (true) {
            try (var reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream(), StandardCharsets.UTF_8));

                    var writer = new BufferedWriter(
                            new OutputStreamWriter(
                                    clientSocket.getOutputStream(), StandardCharsets.UTF_8))) {

                sendMessage(writer, commandHandler.listOfCommands());
                String line;
                while ((line = reader.readLine()) != null) {
                    String response = commandHandler.handleCommand(line);
                    sendMessage(writer, response);
                }
                return;
            } catch (IOException e) {
                System.out.println("IOException while handling client buffers > " + e);
            }
        }
    }

    private void sendMessage(BufferedWriter writer, String message) throws IOException {
        writer.write(message + MESSAGE_END);
        writer.flush();
    }

}