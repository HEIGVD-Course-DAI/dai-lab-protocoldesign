package ch.heig.dai.lab.protocoldesign;
import java.net.*;
import java.io.*;
import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 3141;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

       
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            while(true){
                try (Socket socket = serverSocket.accept();
                    var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                    var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8)) ) {

                        System.out.println("Connection OK");
                        out.write("Voici les opérations : ");
                        out.flush();
                        String line;
                        while ((line = in.readLine()) != null){
                            System.out.println(line);
                            out.write(line + "\n");
                            out.flush();
                        }


                    
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }

    } 
}