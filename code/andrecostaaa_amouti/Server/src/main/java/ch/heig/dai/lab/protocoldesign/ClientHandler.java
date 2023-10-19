package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {

    private static final String MESSAGE_END = "\n";
    private final Socket mySocket ;

    private final CommandHandler commandHandler;

    ClientHandler(Socket socket, CommandHandler cmdHandler){
        mySocket = socket;
        commandHandler = cmdHandler;

    }

    public void run()
    {
        if(mySocket == null)
            return;

        while (true) {
            try (mySocket;
                 var reader = new BufferedReader(
                    new InputStreamReader(
                            mySocket.getInputStream(), StandardCharsets.UTF_8));

                 var writer = new BufferedWriter(
                         new OutputStreamWriter(
                                 mySocket.getOutputStream(), StandardCharsets.UTF_8))) {

                sendMessage(writer, commandHandler.listOfCommands());
                String line;
                while ((line = reader.readLine()) != null) {
                    var commandResponse = commandHandler.handleCommand(line);
                    sendMessage(writer, commandResponse.getResponse());
                    if(commandResponse.isQuit())
                    {
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException while handling client buffers > " + e);
                return;

            }
        }
    }
    private void sendMessage(BufferedWriter writer, String message) throws IOException {
        writer.write(message + MESSAGE_END);
        writer.flush();
    }
}
