package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "0.0.0.0";
    final int SERVER_PORT = 42020;

  

    public static void main(String[] args) throws IOException {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() throws IOException {
        CalcWrapper calc = new CalcWrapper();
        calc.openConnection(SERVER_ADDRESS,SERVER_PORT);
        calc.add(5,1);
    }
}