package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Server {
    final int SERVER_PORT = 4242;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        // Create a passive socket (class ServerSocket)
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(
                             new InputStreamReader(
                                     socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(
                             new OutputStreamWriter(
                                     socket.getOutputStream(), UTF_8))) {

                } catch (IOException e) {
                    System.out.println("Socket Exception: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("ServerSocket Exception: " + e);
        }
    }
}