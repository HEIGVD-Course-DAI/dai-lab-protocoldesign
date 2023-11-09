package ch.heig.dai.lab.protocoldesign;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "127.0.0.1";
    final int SERVER_PORT = 1234;

    /* CONNECTION COMMANDS */
    String QUIT = "QUIT";

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try(Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);            
        BufferedReader in = new BufferedReader(
        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))){
            
            
            String input; 
            while((input = in.readLine()) != null) {

                if(input.equalsIgnoreCase("END" )){
                    break;
                }
                System.out.println(input);

            }
            while(true){
                try{
                    String message = stdin.readLine();
                    out.write(message + "\n");
                    out.flush();
                    System.out.println(in.readLine());
                    
                    if (message.compareToIgnoreCase(QUIT) == 0) {
                        out.close();
                        in.close();
                        socket.close();
                        stdin.close();
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("Client: Error while communicating with the server: " + e);
                    break;
                }
            }         
        } catch(IOException e){
            System.out.println("Client: Client socket ex.: " + e);
        }
    }
}