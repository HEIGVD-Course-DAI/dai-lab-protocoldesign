package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Server {
    final int SERVER_PORT = 4242;

    enum OPERATORS {
        PLUS('+'),
        MINUS('-'),
        MULTIPLY('*'),
        DIVISION('/'),
        MODULO('%'),
        BRACKETO('('),
        BRACKETC(')'),
        SPACE(' ');

        public final char label;

        private OPERATORS(char label) {
            this.label = label;
        }
    }

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        // Create a passive socket (class ServerSocket)
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

                    // Send WELCOME message (with the possible operators) to the clients on new connection
                    out.write("WELCOME|" + getOperators() + "\n");
                    out.flush();

                    while (true) {
                        // read incoming lines from client until connection is closed
                        String msg = in.readLine();
                        // if connection is closed the msg is null: break the while and wait for new client
                        if (msg == null) break;
                        // separate the msg from client into the message type and the message arguments
                        String[] msgParts = msg.split("\\|");

                        // depending on the message type, do ...
                        switch (msgParts[0]) {
                            case "CALCULATION":
                                try {
                                    int result = calculateString(msgParts[1]);
                                    out.write("RESULT|" + result + "\n");
                                } catch (RuntimeException e) {
                                    out.write("ERROR|" + e.getMessage() + "\n");
                                }
                                break;
                            default:
                                // TODO define error code if invalid message recieved
                                out.write("ERROR|Invalid Message Type\n");
                                break;
                        }
                        out.flush();
                    }
                } catch (IOException e) {
                    System.out.println("Socket Exception: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("ServerSocket Exception: " + e);
        }
    }

    private String getOperators() {
        var operatorsString = new StringBuilder();
        for (var operator : OPERATORS.values()) {
            operatorsString.append(operator.label).append(' ');
        }
        return operatorsString.toString();
    }

    private int calculateString(String str) {
        // calculate(str, pos);

        if (str.equals("err")) throw new RuntimeException("invalid format");
        return 42;

        // remove all whitespaces in string
        //String calc = str.replaceAll("\\s+","");
        /*var numberStack = new Stack<Integer>();
        var operatorStack = new Stack<Character>();
        int number;
        for (int i = 0; i < calc.length(); ++i) {
            if (calc.charAt(i) == '(' || calc.charAt(i) == ' ') {
                continue;
            } else if (calc.charAt(i) == ')') {

            } else if ()
        }*/
    }
}