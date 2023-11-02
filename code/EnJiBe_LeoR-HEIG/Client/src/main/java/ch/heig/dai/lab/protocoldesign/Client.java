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
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            while (true)
            {
                System.out.print("Enter your request: ");
                String request = scanner.nextLine();

                out.println(request);

                String response = in.readLine();
                System.out.println("Server response: " + response);

                if (request.equals("QUIT"))
                {
                    break;
                }
            }

            out.flush();
            in.close();
            out.close();
            socket.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}