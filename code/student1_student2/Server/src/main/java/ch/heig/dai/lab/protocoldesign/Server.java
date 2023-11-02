package ch.heig.dai.lab.protocoldesign;

import ch.heig.dai.lab.protocoldesign.Calculator.Calculator;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;


public class Server {
    final int SERVER_PORT = 42020;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){

            while (true){
                try(Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),UTF_8));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),UTF_8))){

                    String line;
                    while((line = in.readLine()) != null){

                        Calculator cal = new Calculator(line);


                        if(line.compareTo("CLOSE") == 0){
                            
                        } else if (cal.isValidOperator()) {
                            out.write("ANSWER : " + cal.resultat() +"\n");
                            out.flush();
                        } else {
                            out.write("error :(");
                        }


                    }
                    
                }catch(IOException e){
                    System.out.println("Server: socket ex : " + e);
                }
            }


        }catch (IOException e){
            System.out.println("Server: server socket ex: " + e);
        }


    } 
}