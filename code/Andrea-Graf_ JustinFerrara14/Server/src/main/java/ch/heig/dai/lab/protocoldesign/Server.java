package ch.heig.dai.lab.protocoldesign;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 8888;
    String[] supportedCommands = {"init", "add", "mul", "quit"};

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
            String[] command = line.split(" ");
            if(command[0] == "quit") {
                break;
            }
            switch (command[0]) {
                case "init":
                    out.write("info");
                    for(String command : supportedCommands) {
                        out.write(" " + command);
                    }
                    out.write("\n
                    break;
                case "add":
                    out.write("result" + (Integer.parseInt(command[1]) + Integer.parseInt(command[2])) + "\n");
                    break;
                case "mul":
                    out.write("result" + (Integer.parseInt(command[1]) * Integer.parseInt(command[2])) + "\n");
                    break;
                case "quit":
                    break;
                default:
                    out.write("result error\n");
                    break;
            }
            out.flush();
        }
    }

}