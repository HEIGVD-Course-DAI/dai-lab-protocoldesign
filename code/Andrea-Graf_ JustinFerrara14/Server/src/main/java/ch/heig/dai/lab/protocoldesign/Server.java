package ch.heig.dai.lab.protocoldesign;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 8888;
    String[] supportedCommands = {"init", "add", "mul", "div", "quit"};

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {
                    command(in, out);
                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }

    private void command(var in, var out) {
        String line;
        while ((line = in.readLine()) != null) {
            switch (line) {
                case "init":
                    out.write("hello\n");
                    break;
                case "bye":
                    out.write("bye\n");
                    break;
                default:
                    out.write("error\n");
                    break;
            }
            out.flush();
        }
    }

}