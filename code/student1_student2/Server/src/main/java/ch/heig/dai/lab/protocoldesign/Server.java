package ch.heig.dai.lab.protocoldesign;

import ch.heig.dai.lab.protocoldesign.Calculator.Calculator;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;
import java.util.Timer;
import java.util.TimerTask;


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

                    out.write("Welcome in the cloud calculator !\n");
                    out.write("Supported operation : ADD SUB MUL DIV\n");
                    out.flush();



                    String clientMessage;
                    while((clientMessage = in.readLine()) != null){



                        String[] tokens = clientMessage.split(" ");

                        if(tokens.length > 3 || tokens.length == 2){
                            out.write("ERROR SYNTAX\n");
                            out.flush();
                        }else{
                            if(tokens[0].compareTo("CLOSE") == 0){
                                out.write("Connection closing !\n");
                                out.flush();
                                break;
                            }else{
                                Calculator cal = new Calculator(clientMessage);

                                int resultat = cal.resultat();

                                if(cal.isValidOperator()){
                                    out.write("ANSWER : " + resultat  +"\n");
                                    out.flush();
                                }
                                else{
                                    out.write("ERROR OPERAND\n");
                                    out.flush();
                                }
                            }
                        }
                    }
                    break;
                }catch(IOException e){
                    System.out.println("Server: socket ex : " + e);
                }
            }


        }catch (IOException e){
            System.out.println("Server: server socket ex: " + e);
        }


    } 
}