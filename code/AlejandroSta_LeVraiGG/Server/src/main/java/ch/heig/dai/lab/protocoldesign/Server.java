package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 4242;
    final String[] OPERATIONS = {"ADD", "MULL", "SUB", "DIV"};

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        //Copy of given example on side:
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(
                                         new InputStreamReader(socket.getInputStream(), UTF_8));
                     BufferedWriter out = new BufferedWriter(
                                          new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {
                    //TODO : lecture du paquet et traitement
                    String op;
                } catch (IOException e) {
                    System.out.println("Server: socket ex. : " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex. : " + e);
        }
    }
}