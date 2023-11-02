package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 2828;
    final String WELCOME_MSG = "WELCOME: You can do the following operations: ADD, MULT";
    final String ERROR_UNKOWN = "UNKOWN";
    final String ERROR_INVALID = "INVALID";

    enum Operations {
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
                    System.out.println("Server: client connected");

                    out.write(WELCOME_MSG + "\n");
                    out.flush();

                    String userInput = in.readLine();
                    System.out.println("userInput: " + userInput);
                    // Operation is at index 0, operand 1 at index 1, operand 2 at index 2
                    String[] parameters = userInput.split(" ");

                    try {
                        Double.parseDouble(parameters[1]);
                        Double.parseDouble(parameters[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("Server1: socket ex.: " + e);
                        out.write(ERROR_INVALID);
                        socket.close();
                        System.exit(1);
                    }

                    switch (parameters[0]) {
                        case "ADD":
                            System.out.println("Calculating: " + String.valueOf(Double.parseDouble(parameters[1]) + Double.parseDouble(parameters[2])));
                            out.write(String.valueOf(Double.parseDouble(parameters[1]) + Double.parseDouble(parameters[2])) + "\n");

                            break;
                        case "MULT":
                            out.write(String.valueOf(Double.parseDouble(parameters[1]) * Double.parseDouble(parameters[2])) + "\n");

                            break;
                        default:
                            out.write(ERROR_UNKOWN + "\n");
                            break;
                    }
                    out.flush();

                    socket.close();

                } catch (IOException e) {
                    System.out.println("Server2: socket ex.: " + e);
                    System.exit(1);

                }
            }
        } catch (IOException e) {
            System.out.println("Server3: server socket ex.: " + e);
            System.exit(1);

        }
    }
}