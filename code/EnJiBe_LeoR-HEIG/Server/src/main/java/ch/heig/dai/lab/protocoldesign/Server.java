package ch.heig.dai.lab.protocoldesign;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    final int SERVER_PORT = 42069;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try
        {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is running...");

            while (true)
            {
                Socket clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;

                while ((inputLine = in.readLine()) != null)
                {
                    String response = processRequest(inputLine);
                    System.out.println(inputLine);
                    System.out.println(response);
                    out.println(response);
                }

                in.close();
                out.close();
                clientSocket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String processRequest(String request)
    {
        StringTokenizer tokenizer = new StringTokenizer(request, " ");
        String command = tokenizer.nextToken();

        switch (command) {
            case "HELLO" -> {
                return "AVAILABLE OPERATIONS ADD (ex. ADD 10 20) MUL (ex. MUL 30 4 66)";
            }
            case "QUIT" -> {
                return "CLOSED";
            }
            case "ADD" -> {
                int result = 0;

                while (tokenizer.hasMoreTokens()) {
                    try {
                        result += Integer.parseInt(tokenizer.nextToken());
                    } catch (NumberFormatException e) {
                        return "ERROR: Invalid operands";
                    }
                }

                return String.valueOf(result);
            }
            case "MUL" -> {
                int result = 1;

                while (tokenizer.hasMoreTokens()) {
                    try {
                        result *= Integer.parseInt(tokenizer.nextToken());
                    } catch (NumberFormatException e) {
                        return "ERROR: Invalid operands";
                    }
                }

                return String.valueOf(result);
            }
            default -> {
                return "ERROR: Invalid command";
            }
        }
    }
}