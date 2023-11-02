package ch.heig.dai.lab.protocoldesign;

import java.net.*;
import java.io.*;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 8888;
    String[] supportedCommands = {"init", "add", "mult", "quit"};

    public static void main(String[] args) {
        System.out.println("Server running on port ");
        while(true){
            // Create a new client and run it
            Server server = new Server();
            server.run();
        }
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {


                    String line;
                    while ((line = in.readLine()) != null) {
                        String[] command = line.split(" ");
                        if(Objects.equals(command[0], "quit")) {
                            break;
                        }
                        switch (command[0]) {
                            case "init":
                                out.write("info");
                                for(String commands : supportedCommands) {
                                    out.write(" " + commands);
                                }
                                out.write("\n");
                                break;
                            case "add":
                                out.write("result " + (Integer.parseInt(command[1]) + Integer.parseInt(command[2])) + "\n");
                                break;
                            case "mult":
                                out.write("result " + (Integer.parseInt(command[1]) * Integer.parseInt(command[2])) + "\n");
                                break;
                            case "quit":
                                break;
                            default:
                                out.write("result error\n");
                                break;
                        }
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