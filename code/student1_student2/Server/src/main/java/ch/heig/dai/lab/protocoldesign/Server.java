package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {

                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

                    String line;
                    String answer = null;

                    while ((line = in.readLine()) != null) {
                        //TODO
                        String header = getFirstWord(line);
                        switch(header){
                            case "HEY":
                                answer = "hey ( ◣‿◢')";
                        }
                        out.write(answer + "\n");
                        out.flush();
                    }

                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }

    private String getFirstWord(String firstWord) {
        int index = firstWord.indexOf(' ');
        if (index > -1) { // Check if there is more than one word.
            return firstWord.substring(0, index).trim();
        } else {
            return firstWord;
        }
    }
}