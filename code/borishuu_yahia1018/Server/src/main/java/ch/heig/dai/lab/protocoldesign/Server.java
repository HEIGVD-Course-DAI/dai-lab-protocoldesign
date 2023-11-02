package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 54321;

    private static String[] supportedOperators = new String[] {
        "ADD",
        "SUB",
        "MUL",
        "DIV"
    };

    private boolean serverRunning = false;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();


        server.run();
    }

    private void displayWelcomeMessage() {
        System.out.println("Welcome to ptcalc server!");
        System.out.println("The supported operators are the following :");
        for (String operator : supportedOperators) {
            System.out.println(operator);
        }
    }

    private void run() {
        displayWelcomeMessage();
        serverRunning = true;

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (serverRunning) {
                try (Socket socket = serverSocket.accept();
                    var in = new BufferedReader(
                        new InputStreamReader(
                            socket.getInputStream(), UTF_8));
                    var out = new BufferedWriter(
                        new OutputStreamWriter(
                            socket.getOutputStream(), UTF_8))) {
                        String line;
                        while ((line = in.readLine()) != null) {                    
                            out.write(line + "\n");
                            out.flush();
                        }
                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    } 
}