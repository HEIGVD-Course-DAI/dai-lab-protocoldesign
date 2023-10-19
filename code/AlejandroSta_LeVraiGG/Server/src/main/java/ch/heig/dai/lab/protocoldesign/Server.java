package ch.heig.dai.lab.protocoldesign;

public class Server {
    final int SERVER_PORT = 4242;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        //Copy of given example on side:
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     InputStream in = new BufferedInputStream(socket.getInputStream());
                     OutputStream out = new
                             BufferedOutputStream(socket.getOutputStream());) {
                    int i;
                    while ((i = in.read()) != -1) {
                        out.write(i);
                        out.flush();
                    }
                } catch (IOException e) {
                    System.out.println("Server: socket ex. : " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex. : " + e);
        }
    }
}