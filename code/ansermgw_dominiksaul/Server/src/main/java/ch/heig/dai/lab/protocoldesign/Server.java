package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Character.isDigit;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Server {
    final int SERVER_PORT = 4242;

    enum OPERATORS {
        PLUS('+'),
        MINUS('-'),
        MULTIPLY('*'),
        DIVISION('/'),
        MODULO('%'),
        POWER('^'),
        BRACKET_OPEN('('),
        BRACKET_CLOSE(')'),
        POINT('.'),
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
                                //case "CALC":
                                try {
                                    // String result = ;
                                    out.write("RESULT|" + calculateString(msgParts[1]) + "\n");
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
        // convert string so it can be calculated
        str = str.replaceAll("\\s+", "");
        str = "(" + str + ")";

        // calculate the string
        return calculate(str, new Position());
    }

    private int calculate(String str, Position pos) {
        if (str.charAt(pos.get()) == OPERATORS.BRACKET_OPEN.label) {
            pos.increase();
            int v1 = calculate(str, pos);

            // return if it's a single value inside brackets, f.ex ((3))
            if (str.charAt(pos.get()) == OPERATORS.BRACKET_CLOSE.label) {
                return v1;
            }

            char op = str.charAt(pos.increase());
            int v2 = calculate(str, pos);

            if (str.charAt(pos.increase()) != OPERATORS.BRACKET_CLOSE.label) {
                throw new RuntimeException("invalid calculation format 1");
            }

            if (op == OPERATORS.PLUS.label) {
                return v1 + v2;
            } else if (op == OPERATORS.MINUS.label) {
                return v1 - v2;
            } else if (op == OPERATORS.MULTIPLY.label) {
                return v1 * v2;
            } else if (op == OPERATORS.DIVISION.label) {
                return v1 / v2;
            } else if (op == OPERATORS.MODULO.label) {
                return v1 % v2;
            } else if (op == OPERATORS.POWER.label) {
                return (int) Math.pow(v1, v2);
            } else {
                throw new RuntimeException("invalid calculation format 2");
            }
        } else if (isDigit(str.charAt(pos.get())) || str.charAt(pos.get()) == OPERATORS.MINUS.label) {
            var value = new StringBuilder();
            if (str.charAt(pos.get()) == OPERATORS.MINUS.label) {
                value.append(str.charAt(pos.increase()));
            }
            while (isDigit(str.charAt(pos.get()))) {
                value.append(str.charAt(pos.increase()));
            }
            return Integer.parseInt(value.toString());
        } else {
            throw new RuntimeException("invalid calculation format 3");
        }
    }

    private class Position {
        int pos = 0;

        int get() {
            return pos;
        }

        int increase() {
            return pos++;
        }
    }
}