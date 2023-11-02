package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Client {
    static final int SERVER_PORT = 0xCA1C;
    static final private String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    static final private String MSG_PREFIX = "GCCP ";
    static final private String MSG_HELLO = MSG_PREFIX + "HELLO";
    static final private String MSG_BYE = "BYE";
    static final private int MSG_MAX_LENGTH = 8192;
    static final private String ALLOWED_OPERANDS_PATTERN = "[0-9]+";
    private String serverAddress;
    private ArrayList<String> allowedOperators;

    /**
     * Application entry point.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.run(args);
    }

    private void setAllowedOperators(ArrayList<String> allowedOperators) {
        this.allowedOperators = allowedOperators;
    }

    private void help() {
        System.out.println("Usage: java Client <server IPv4 address>");
        System.exit(1);
    }

    private void parseArguments(String[] args) {
        if (args.length != 1 || args[0].matches("help") || !args[0].matches(IPV4_PATTERN)) {
            help();
            System.exit(1);
        }

        serverAddress = args[0];
    }

    private void run(String[] args) {
        parseArguments(args);
        System.out.println("Connecting to server " + serverAddress + ":" + SERVER_PORT);

        try (Socket socket = new Socket(serverAddress, SERVER_PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8));
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            String line = "";
            // Establish the connection with the server
            connect(in, out);

            // Connection to the server established and ready to run
            // Loop between asking for input and displaying answer
            while (!line.startsWith(MSG_PREFIX + MSG_BYE)) {
                write(userInput, out); // Get user input
                line = read(in); // Get server response
            }

            System.out.println("Server: connection closed");
        } catch (IOException e) {
            System.out.println("Server: socket exception: " + e);
        }
    }

    private void connect(BufferedReader in, BufferedWriter out) throws IOException {
        out.write(MSG_HELLO);
        out.newLine();
        out.flush();
        // Get server response
        String response = in.readLine();
        if (!response.startsWith(MSG_HELLO)) {
            System.out.println("Server: connection refused");
            System.out.println("Server: " + response);
            System.exit(1);
        } else {
            // Parse anything after the hello message
            if (response.length() > MSG_HELLO.length() + 1) {
                response = response.substring(MSG_HELLO.length() + 1);
            }
            System.out.println("Server: connection established");
            System.out.println("Server: allowed operators are " + response);
            // Parse the allowed operators
            ArrayList<String> allowedOperators = new ArrayList<>();
            Collections.addAll(allowedOperators, response.split(" "));
            setAllowedOperators(allowedOperators);
        }
    }

    private void write(BufferedReader in, BufferedWriter out) throws IOException {
        // Get user input
        String message = null;
        while (message == null) {
            System.out.print("Client: ");
            message = in.readLine();
            // Check that the message is not too long
            if (message.length() > MSG_MAX_LENGTH) {
                message = null;
                System.out.println(" message too long. Please try again.");
                break;
            }
            // Check that the message is whitelisted
            if (message.endsWith("exit")) {
                message = MSG_BYE;
                break;
            }
            // Check that messages only contains numbers and allowed tokens
            for (final var token : message.split(" ")) {
                if (!allowedOperators.contains(token) && !token.matches(ALLOWED_OPERANDS_PATTERN)) {
                    message = null;
                    System.out.println(" incorrect request. Please try again.");
                    break;
                }
            }
            // Check that the last token is an allowed operator
            if (!allowedOperators.contains(message.split(" ")[message.split(" ").length - 1])) {
                message = null;
                System.out.println(" incorrect request. Please try again.");
            }
        }

        // Write user input to output
        out.write(MSG_PREFIX + message + "\n");
        out.flush();
    }

    private String read(BufferedReader in) throws IOException {
        String response;
        while (!(response = in.readLine()).startsWith(MSG_PREFIX)) {
            // Blockingly wait for a well-formatted message
        }
        System.out.println("Server: " + response.substring(MSG_PREFIX.length()));
        return response;
    }
}