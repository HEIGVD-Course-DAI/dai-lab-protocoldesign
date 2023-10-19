package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    final int SERVER_PORT = 32976;
    final String OPERATIONS =   "ADD <firstNumber> <secondNumber> \n" +
                                "SUB <firstNumber> <secondNumber> \n" +
                                "MUL <firstNumber> <secondNumber> \n" +
                                "DIV <firstNumber> <secondNumber>";
    final String[] OPS = {"ADD","SUB","MUL","DIV"};

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            try (Socket socket = serverSocket.accept();
                 var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                 var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                out.write(OPERATIONS);
                out.flush();

                String[] request = in.readLine().split(" ");
                String opp = request[0];
                String num1 = request[1];
                String num2 = request[2];
                boolean validOp = false;

                for (String op : OPS) {
                    if(opp == op) {
                        validOp = true;
                    }
                }

                // Check if valid operation
                if (!validOp) {
                    out.write("UNSUPPORTED OPP");
                    out.flush();
                }
                else {
                    try {
                        Double.parseDouble(num1); // Try to parse the string as a double
                        Double.parseDouble(num2);
                    } catch (NumberFormatException e) {
                        out.write("UNSUPPORTED NUM"); // Parsing failed, so it's not numerical
                        out.flush();
                    }

                }
            }
            catch(IOException e) {
                System.out.println("Server: socket ex.: " + e);
            }

        }
        catch (IOException e) {
            System.out.println(("Server: server socket ex.: " + e));
        }
    } 
}