package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Server {
    final int SERVER_PORT = 52273;

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

                    String line;
                    if ((line = in.readLine()) != null) {
                        out.write(calculate(line) + "\n");
                        out.write("Closing TCP connection");
                        out.flush();
                        socket.close();
                    }
                    break;
                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }

    public String calculate(String line){
        String[] words = line.split(" ");
        if(words.length == 3){
            int lhs = 0;
            int rhs = 0;
            try{
                lhs = Integer.parseInt(words[1]);
                rhs = Integer.parseInt(words[2]);
            }catch (NumberFormatException e){
                return "ERROR " + line + " cannot be calculated!";
            }

            int answer = 0;
            return switch (words[0]) {
                case "ADD" -> {
                    answer = lhs + rhs;
                    yield Integer.toString(answer);
                }
                case "SUB" -> {
                    answer = lhs - rhs;
                    yield Integer.toString(answer);
                }
                case "MLT" -> {
                    answer = lhs * rhs;
                    yield Integer.toString(answer);
                }
                case "DIV" -> {
                    try{
                        answer = lhs / rhs;
                    }catch (ArithmeticException e){
                        yield "ERROR " + line + " cannot be calculated!";
                    }
                    yield Integer.toString(answer);
                }
                case "MOD" -> {
                    answer = lhs % rhs;
                    yield Integer.toString(answer);
                }
                default -> "ERROR " + line + " cannot be calculated!";
            };
        }
        return "ERROR " + line + " cannot be calculated!";
    }
}