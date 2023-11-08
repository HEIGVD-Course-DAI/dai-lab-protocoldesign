package ch.heig.dai.lab.protocoldesign;

import jdk.dynalink.Operation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.OptionalDouble;

import static java.nio.charset.StandardCharsets.*;

public class Server {

    /***
     * 12345 est le port sur lequel le serveur écoute les connexions entrantes,
     * tandis que l'autre port renseigné est le port local attribué par l'OS à la connexion entre le client et le serveur.
     */
    final int SERVER_PORT = 12345;

    // Messages send to the client
    String UNKNOWN_OPERATION = "UNKNOWN OPERATION\n";
    String COMPUTATION_FAILED = "COMPUTATION FAILED\n";
    String SERVER_EXIT = "EXIT\n";

    // Messages parts from the client
    String CLIENT_OPERATION = "OPERATION";
    String CLIENT_EXIT = "EXIT";

    // Enum used for the operations
    public enum Operations {
        ADD("add"), MUL("mul"), SUB("sub"), DIV("div"), MOD("mod");

        private final String text;

        Operations(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

        public static String getAllOperations() {
            StringBuilder str = new StringBuilder();
            for (Operations op : Operations.values()) {
                str.append(op.text);
            }
            return str.toString();
        }

        public static Operations getMatchingValue(String value) {
            for (Operations op : Operations.values()) {
                if (Objects.equals(value, op.text)) {
                    return op;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Server is running...");

            while (true) {

                try (Socket clientSocket = serverSocket.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), UTF_8)); BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), UTF_8))) {

                    System.out.println("New client connected: " + clientSocket);

                    out.write("CONNECTED <" + Operations.getAllOperations() + ">\n");
                    out.flush();

                    String line;

                    while ((line = in.readLine()) != null) {
                        if (line.startsWith(CLIENT_OPERATION)) {
                            processLine(line, out);
                        } else if (line.equals(CLIENT_EXIT)) {
                            out.write(SERVER_EXIT);
                            out.flush();
                            break;
                        } else {
                            out.write(UNKNOWN_OPERATION);
                            out.flush();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Server: " + e);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Server: " + e);
            e.printStackTrace();
        }
    }

    private void processLine(String line, BufferedWriter out) throws IOException {
        String[] parts = line.split(" "); // Split the message and perform the operation
        if (parts.length == 4 || Operations.getMatchingValue(parts[1]) == null) {
            String operation = parts[1];
            double operand1 = 0;
            double operand2 = 0;

            // Check if the operand are numeric values
            try {
                operand1 = Double.parseDouble(parts[2]);
                operand2 = Double.parseDouble(parts[3]);

                // Computation
                OptionalDouble result = calculate(operation, operand1, operand2);
                if (result.isPresent()) {
                    out.write(result.toString() + '\n');
                    out.flush();
                } else {
                    out.write(COMPUTATION_FAILED);
                    out.flush();
                }

            } catch (NumberFormatException e) {
                // One of the operand string does not contain a parsable double.
                out.write(UNKNOWN_OPERATION);
                out.flush();
            }
        } else {
            out.write(UNKNOWN_OPERATION);
            out.flush();
        }
    }

    private OptionalDouble calculate(String operation, double operand1, double operand2) {
        double result;
        Operations op = Operations.getMatchingValue(operation);
        if (op != null) {
            switch (op) {
                case ADD:
                    result = operand1 + operand2;
                    return OptionalDouble.of(result);
                case SUB:
                    result = operand1 - operand2;
                    return OptionalDouble.of(result);
                case MUL:
                    result = operand1 * operand2;
                    return OptionalDouble.of(result);
                case DIV:
                    if (operand2 != 0) {
                        return OptionalDouble.of(operand1 / operand2);
                    }
                    break;
                case MOD:
                    if (operand2 != 0) {
                        return OptionalDouble.of(operand1 % operand2);
                    }
                    break;
            }
        }
        return OptionalDouble.empty();
    }
}