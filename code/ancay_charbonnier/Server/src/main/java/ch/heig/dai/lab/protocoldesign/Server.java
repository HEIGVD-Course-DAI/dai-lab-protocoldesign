package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true){
                try (Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));)
                {
                    String line;
                    while ((line = in.readLine()) != null) {
                        //TODO Compute
                        System.out.println(line);
                        out.write("OK : " + line + "\n");
                        out.flush();
                    }
                    
                    
                } catch (Exception e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }

        } catch (Exception e) {
            System.out.println("Server: server socket ex.: " + e);
        }

    } 
}