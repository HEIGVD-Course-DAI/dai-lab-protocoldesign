package ch.heig.dai.lab.protocoldesign;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        Server server = new Server();
        server.run(5, 6666);
    }

    private void run(int numThreads, int port) {
        System.out.println("Thread pool with " + numThreads + " threads");
        Worker worker = new Worker();
        try (var serverSocket = new ServerSocket(port);
             ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    var handler = new RunnableClientHandler(socket, worker);
                    executor.execute(handler);
                } catch (IOException e) {
                    System.err.println("Error client socket: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error server socket: " + e.getMessage());
        }
    }
}
