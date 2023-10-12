package ch.heig.dai.lab.protocoldesign;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 6900;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
    }
}