package ch.heig.dai.lab.protocoldesign;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
                     InputStream in = new BufferedInputStream(socket.getInputStream(), Charset.UTF_8);
                     OutputStream out = new
                             BufferedOutputStream(socket.getOutputStream());) {
                    //TODO: lecture du paquet et traitement
                    String op = in.
                } catch (IOException e) {
                    System.out.println("Server: socket ex. : " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex. : " + e);
        }
    }
}