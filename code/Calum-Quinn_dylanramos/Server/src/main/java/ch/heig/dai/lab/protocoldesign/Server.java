package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    final int SERVER_PORT = 32976;
    final String OPERATIONS = """
            ADD <firstNumber> <secondNumber>
            SUB <firstNumber> <secondNumber>
            MUL <firstNumber> <secondNumber>
            DIV <firstNumber> <secondNumber>
            END
            """;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    // Check if the number string represents a number
    public static boolean isNumeric(String number) {
        return number.matches("[+-]?\\d+");
    }

    // Carries out the operation
    public static String operation(String op, String num1, String num2) {
        int number1 = Integer.parseInt(num1);
        int number2 = Integer.parseInt(num2);
        switch (op) {
            case "ADD":
                return Integer.toString(number1 + number2) + '\n';
            case "SUB":
                return Integer.toString(number1 - number2) + '\n';
            case "MUL":
                return Integer.toString(number1 * number2) + '\n';
            case "DIV":
                if (number2 != 0) {
                    return Integer.toString(number1 / number2) + '\n';
                }
            default:
                return "UNSUPPORTED OPP\n";
        }
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            try (Socket socket = serverSocket.accept();
                 var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                 var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                // Send possible operations to client
                out.write(OPERATIONS);
                out.flush();

                // Read client's request
                String request;
                while ((request = in.readLine()) != null) {
                    String[] arguments = request.split("\\s+");
                    if (arguments.length != 3) {
                        out.write("UNSUPPORTED OPP\n");
                        out.flush();
                    }
                    else {
                        String opp = arguments[0];
                        String num1 = arguments[1];
                        String num2 = arguments[2];

                        // Check if numbers are valid
                        if (!(isNumeric(num1) && isNumeric(num2))) {
                            out.write("UNSUPPORTED NUM\n");
                            out.flush();
                        } else {
                            // Valid numbers
                            out.write(operation(opp, num1, num2));
                            out.flush();
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Server: socket ex.: " + e);
            }
        } catch (IOException e) {
            System.out.println(("Server: server socket ex.: " + e));
        }
    }
}