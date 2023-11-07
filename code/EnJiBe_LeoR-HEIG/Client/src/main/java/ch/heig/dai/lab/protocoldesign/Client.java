package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
    final String SERVER_ADDRESS = "0.0.0.0";
    final int SERVER_PORT = 42069;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in);)
        {
            boolean connectionOpen = false;

            String response;

            while (true)
            {
                if(!connectionOpen)
                {
                    out.println("HELLO");
                    if (!(response = in.readLine()).equals("ERROR: Invalid command"))
                    {
                        connectionOpen = true;
                    }
                    System.out.println(response);
                    continue;
                }
                System.out.println("Enter your request: ");
                String request = scanner.nextLine();

                out.println(request);

                response = in.readLine();
                System.out.println("Server response: " + response);

                if (request.equals("QUIT"))
                {
                    break;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("catch");
            e.printStackTrace();
        }
    }
}