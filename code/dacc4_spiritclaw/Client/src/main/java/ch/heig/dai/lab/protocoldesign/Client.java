package ch.heig.dai.lab.protocoldesign;

import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Client {
    private final String SERVER_ADDRESS = "1.2.3.4";
    private final int SERVER_PORT = 4242;
    private final Charset charset = StandardCharsets.UTF_8;
    private final char lineSeparator = ';';

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
    }

    private ArrayList<Operation> convertFromString(String str){
        ArrayList<Operation> operations = new ArrayList<>();

        // TODO : Logic

        return operations;
    }

    private OperationResult computeOperations(ArrayList<Operation> operations){
        try (var socket = new Socket(SERVER_ADDRESS, SERVER_PORT)){

        } catch (Exception e) {
            return null;
        }
        return null;
    }
}