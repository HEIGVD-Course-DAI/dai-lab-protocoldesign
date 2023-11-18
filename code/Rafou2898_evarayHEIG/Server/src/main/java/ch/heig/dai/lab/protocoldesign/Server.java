package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 2828;
    final String WELCOME_MSG = "WELCOME: You can do the following operations:";
    final String ERROR_UNKOWN = "UNKOWN";
    final String ERROR_INVALID = "INVALID";

    String[] operations = {"ADD", "SUB", "MULT"};

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Server: I am listening baby");

            StringBuilder welcomeMessage = new StringBuilder();
            welcomeMessage.append(WELCOME_MSG);
            for(String operation : operations){
                welcomeMessage.append(" ").append(operation);
            }

            while (true) {

                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {
                    System.out.println("Server: client connected");

                    out.write(welcomeMessage.toString() + "\n");
                    out.flush();
                    String userInput;

                    while((userInput = in.readLine()) != null) {
                        // String userInput = in.readLine();

                        // Operation is at index 0, operand 1 at index 1, operand 2 at index 2
                        String[] parameters = userInput.split(" ");

                        try {
                            Double.parseDouble(parameters[1]);
                            Double.parseDouble(parameters[2]);
                        } catch (NumberFormatException e) {
                            out.write(ERROR_INVALID);
                            out.flush();
                            socket.close();
                            continue;
                        }

                        switch (parameters[0].toUpperCase()) {
                            case "ADD":
                                System.out.println("Calculating... ");
                                out.write(String.valueOf(Double.parseDouble(parameters[1]) + Double.parseDouble(parameters[2])) + "\n");
                                break;
                            case "MULT":
                                out.write(String.valueOf(Double.parseDouble(parameters[1]) * Double.parseDouble(parameters[2])) + "\n");
                                break;
                            case "SUB":
                                out.write(String.valueOf(Double.parseDouble(parameters[1]) - Double.parseDouble(parameters[2])) + "\n");
                                break;
                            default:
                                out.write(ERROR_UNKOWN + "\n");
                                break;
                        }
                        System.out.println("Done!");
                        out.flush();
                    }

                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);

                }
                System.out.println("Server: client disconnected");
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);

        }
    }
}