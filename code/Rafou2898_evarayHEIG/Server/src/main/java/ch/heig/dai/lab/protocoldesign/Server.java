package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 2828;
    final String WELCOME_MSG = "WELCOME: You can do the following operations: ADD, MULT";
    final String ERROR_UNKOWN = "UNKOWN";
    final String ERROR_INVALID = "INVALID";

    enum Operations
    {
        ADD,
        MULT
    }

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            while (true) {

                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

                    out.write(WELCOME_MSG + "\n");
                    out.flush();

                    String userInput = in.readLine();
                    // Operation is at index 0, operand 1 at index 1, operand 2 at index 2
                    String[] parameters = userInput.split(" ");

                    try{
                        Double.parseDouble(parameters[1]);
                        Double.parseDouble(parameters[2]);
                    } catch (NumberFormatException e){
                        System.out.println("Server: socket ex.: " + e);
                        out.write(ERROR_INVALID);
                        socket.close();
                    }

                    switch (parameters[0]) {
                        case "ADD":
                            out.write(String.valueOf(Double.parseDouble(parameters[1]) + Double.parseDouble(parameters[2])) + "\n");
                            break;
                        case "MULT":
                            out.write(String.valueOf(Double.parseDouble(parameters[1]) * Double.parseDouble(parameters[2])) + "\n");
                            break;
                        default:
                            out.write(ERROR_UNKOWN + "\n");
                            break;
                    }

                    socket.close();

                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }
}