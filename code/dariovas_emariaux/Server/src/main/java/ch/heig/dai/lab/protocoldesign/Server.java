package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 42069;

    private enum Operation  {
        ADD("+"),
        SUB("-"),
        DIV("/"),
        MULT("*"),
        POW("^"),
        BRACKET_OPEN("("),
        BRACKET_CLOSE(")");
        public final String label;
        private Operation(String label) {
            this.label = label;
        }
    }

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server up and running on port " + SERVER_PORT);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(
                             new InputStreamReader( socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter( new OutputStreamWriter(
                             socket.getOutputStream(), UTF_8))){

                    // Send WELCOME message (with the possible operators) to the clients on new connection
                    out.write("WELCOME | " + getOperations() + "\n");
                    out.flush();

                    while (true){
                        String msg = in.readLine();

                        if(msg != null)
                            break;
                    }

                }
                catch (IOException e){
                    System.out.println("Server: server socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }

    private String getOperations(){
        StringBuilder operation = new StringBuilder();

        for (Operation op : Operation.values()){
            operation.append(op.label).append(" ");
        }

        return operation.toString();
    }
}