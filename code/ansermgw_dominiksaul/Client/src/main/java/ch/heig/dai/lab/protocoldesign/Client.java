package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client {
    final static String MESSAGE_SEP = "|";
    final static String MESSAGE_END = "\n";
    final String SERVER_ADDRESS = "127.0.0.1";
    final int SERVER_PORT = 4242;
    final BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        System.out.println("Establishing connection with " + SERVER_ADDRESS + ":" + SERVER_PORT);
        try (var socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            handleWelcomeMessage(getMessageFromServer(in));

            while (true) {
                out.write(buildCalculationMessage(getUserInput()));
                out.flush();

                handleCalculationResult(getMessageFromServer(in));
            }

        } catch (UnknownHostException e) {
            System.out.println("No server listening");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void handleCalculationResult(Message message) {
        if(isErrorMessageHandled(message)) {
            return;
        } else if (message.type() != MessageType.RESULT) {
            throw new RuntimeException("Did not receive expected calculation result");
        }

        System.out.println(message.content());
    }

    private void handleWelcomeMessage(Message message) {
        if (message.type() != MessageType.WELCOME) {
            throw new RuntimeException("Did not receive expected welcome message");
        }

        var supportedOperators = extractSupportedOperators(message.content);

        if (supportedOperators.length == 0) {
            throw new RuntimeException("Useless server, can't do math");
        }

        System.out.println("The server support the following operators: " + String.join(" ", supportedOperators));
    }

    private boolean isErrorMessageHandled(Message message) {
        if (message.type() == MessageType.ERROR) {
            System.out.println("ERROR: " + message.content);
            return true;
        }

        return false;
    }

    private String buildCalculationMessage(String equation) {
        return MessageType.CALCULATION + MESSAGE_SEP + equation + MESSAGE_END;
    }

    private String getUserInput() throws IOException {
        System.out.print("=> ");
        return consoleIn.readLine();
    }

    private Message getMessageFromServer(BufferedReader in) throws IOException {
        var msg = in.readLine();
        if(msg == null) {
            throw new RuntimeException("Server closed connection");
        }
        return parseMessage(msg);
    }

    private String[] extractSupportedOperators(String content) {
        return content.split("\\s");
    }

    private Message parseMessage(String message) {
        if (!message.contains(MESSAGE_SEP)) {
            System.out.println("Invalid response received from server");
        }

        var msgPart = message.split("\\|");
        return new Message(MessageType.valueOf(msgPart[0]), msgPart[1]);
    }

    private enum MessageType {
        WELCOME,
        CALCULATION,
        RESULT,
        ERROR
    }

    record Message(MessageType type, String content) {
    }
}