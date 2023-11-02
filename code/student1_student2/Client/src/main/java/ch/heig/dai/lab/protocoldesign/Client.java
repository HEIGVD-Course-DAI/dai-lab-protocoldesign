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
        System.out.println("Lesgo");
        CalcWrapper calc = new CalcWrapper();
        calc.openConnection(SERVER_ADDRESS,SERVER_PORT);
        calc.add(2,1);
        calc.substract(2,1);
        calc.divide(1,2);
        calc.multiply(3,1);
    }
}