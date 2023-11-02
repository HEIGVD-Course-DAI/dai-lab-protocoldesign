package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

public class Client {
    final String SERVER_ADDRESS = "1.2.3.4";
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket("localhost", 1234);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

            //TODO
            String messages = "HEY \nCALC ADD 12 23\nCALC SUB 10 12\n  CALC MUL 90 38\n CLOWN\nCALC 1";
            String[] lines = messages.split("\r\n|\r|\n");
            int nblines = lines.length;

            for(int i = 0; i < nblines; ++i){
                System.out.println("Sent to server: " + lines[i]);
                out.write(lines[i] + "\n");
                out.flush();
                System.out.println("Echo: " + in.readLine());
            }

        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}