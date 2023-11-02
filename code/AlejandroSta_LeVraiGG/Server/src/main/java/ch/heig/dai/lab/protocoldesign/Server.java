package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 4242;
    final String[] OPERATIONS = {"ADD", "MUL"/*, "SUB", "DIV"*/};

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
                    String line;
                    while ((line = in.readLine()) != null){
                        String[] args = line.split(" ");
                        double value1, value2, result = 0;
                        if(args.length != 3){
                            out.write("INVALID 2" + '\n');
                            out.flush();
                            socket.close();
                        }
                        if(!Arrays.asList(OPERATIONS).contains(args[0])){
                            out.write("INVALID 1" + '\n');
                            out.flush();
                            socket.close();
                        }
                        if(!args[1].startsWith("+-") || !args[2].startsWith("+-")){
                            out.write("INVALID 3" + '\n');
                            out.flush();
                            socket.close();
                        }
                        try{
                            value1 = Double.parseDouble(args[1]);
                            value2 = Double.parseDouble(args[2]);
                            result = switch (args[0]) {
                                case "ADD" -> value1 + value2;
                                case "MUL" -> value1 * value2;
                                default -> result;
                            };
                            if(result >= 0) out.write("+");
                            out.write(Double.toString(result));

                        }catch (NumberFormatException e){
                            out.write("INVALID 3" + '\n');
                            out.flush();
                            socket.close();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Server: socket ex. : " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex. : " + e);
        }
    }
}