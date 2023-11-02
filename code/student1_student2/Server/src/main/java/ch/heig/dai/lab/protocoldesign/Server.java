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
                    String[] instrWords = null;

                    while ((line = in.readLine()) != null) {
                        //TODO
                        instrWords = splitStringIntoWords(line);
                        String header = instrWords[0];
                        switch(header){
                            case "HEY":
                                answer = "hey ( ◣‿◢')";
                                break;
                            case "CALC":
                                //answer = computeCalc(String[] instrWords);
                                break;
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

    private String[] splitStringIntoWords(String input){
        return input.split(" ");
    }

   private String computeCalc(String[] instruction) {
       String operation = instruction[1];
       int lfs = Integer.parseInt(instruction[2]);
       int rhs = Integer.parseInt(instruction[3]);

       return null; //operation(lfs, rhs);
   }
}