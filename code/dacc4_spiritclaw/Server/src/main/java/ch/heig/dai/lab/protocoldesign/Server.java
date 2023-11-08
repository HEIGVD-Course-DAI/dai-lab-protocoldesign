package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import ch.heig.dai.lab.protocoldesign_common.*;

public class Server {

    static int previousResult = 0;
    static boolean firstOperation = true;

    final int SERVER_PORT = 4242;
    private final char LINE_DELIMITER = ';';
    private final String FIELD_DELIMITER = "\s";

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private OperationResult process(String line) {
        String[] fields = line.split(FIELD_DELIMITER);
        // Check that the line was valid
        if (fields.length != 3) {
            return new OperationResult(OperationError.INVNB);
        }

        Operator op;
        // Verify that the operator is valid
        try {
            op = Operator.fromName(fields[0]);
        } catch (IllegalArgumentException e) {
            return new OperationResult(OperationError.INVOP);
        }

        // If not first operation, check if one of the operand is ?
        if (!firstOperation) {
            if (fields[1].equals("?")) {
                fields[1] = Integer.toString(previousResult);
            }
            if (fields[2].equals("?")) {
                fields[2] = Integer.toString(previousResult);
            }
            firstOperation = false;
        }

        // Verify that the operands are valid
        int op1, op2;
        try {
            op1 = Integer.parseInt(fields[1]);
            op2 = Integer.parseInt(fields[2]);
        } catch (NumberFormatException e) {
            return new OperationResult(OperationError.INVARG);
        }

        // Perform the operation
        int result = 0;
        switch (op) {
            case ADD:
                return new OperationResult(Integer.toString(op1 + op2));
            case SUB:
                return new OperationResult(Integer.toString(op1 - op2));
            case MUL:
                return new OperationResult(Integer.toString(op1 * op2));
            case DIV:
                if (op2 == 0) {
                    return new OperationResult(OperationError.DIV0);
                }
                return new OperationResult(Integer.toString(op1 / op2));
            case MOD:
                if (op2 == 0) {
                    return new OperationResult(OperationError.DIV0);
                }
                return new OperationResult(Integer.toString(op1 % op2));
            case POW:
                return new OperationResult(Integer.toString((int)Math.pow(op1, op2)));
            default:
                return new OperationResult(OperationError.INVOP);

        }
    }

    private void run() {
        // Listen on port on all interfaces
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            // Wait (block) until a client establishes a connection
            while (true) {
                try (Socket clientSocket = serverSocket.accept()){
                    var in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8)
                    );

                    // Only interested in the first line
                    StringBuilder sb = new StringBuilder();
                    int c;
                    while ((c = in.read()) != LINE_DELIMITER) {
                        sb.append((char)c);
                    }
                    String line = sb.toString();
                    OperationResult result = process(line);

                    var out = new BufferedWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8)
                    );

                    out.write(result.getResult());
                    out.flush();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}