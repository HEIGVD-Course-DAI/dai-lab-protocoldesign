package ch.heig.dai.lab.protocoldesign;
import com.sun.source.tree.WhileLoopTree;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int SERVER_PORT = 1234;
    final String regexPattern = "^(add|sub|mult|div);((\\d+(\\.)?(\\d){1,2});(\\d+(\\.)?(\\d){1,2}))$";
    public static void logMessage(String s){
        System.out.println(s+"\n");
    }
    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        try (ServerSocket serverSocket =  new ServerSocket(SERVER_PORT)){
            // loop wait until a client connect
            while (true) {
                try(Socket socket = serverSocket.accept()){
                    logMessage("Connected client : " + socket.getInetAddress());
                    BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

                }catch (IOException e){
                    logMessage("Server : socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            logMessage("Server: server socket ex.: " + e);
        }

        // loop listen socket
    }
}