package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    final int SERVER_PORT = 1234;

    /* ACCEPTED COMMANDS */
    String QUIT = "QUIT";
    String ADD = "ADD";
    String MULT = "MULT";

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        //Create passive socket
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            while(true){
                //  Create active socket & get streams 
                try(Socket socket = serverSocket.accept();  
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8));){  
                        
                        
                        //  Start of message exchange
                        out.write("START OF CONNECTION\n");
                        out.write("Welcome!\nHere are the supported operations:\n   ADD -> addition\n   MULT -> multiplication\n" +
                        "Please write in the following format: OPERATION OPERAND1 OPERAND2\nTo quit: QUIT\n");
                        out.flush();
                        
                        
                        // Read and parse client messages
                        String l;
                        while( (l = in.readLine()) != null){
                            String line = l.trim();
                            if( line.compareToIgnoreCase(QUIT) == 0){
                                // Close connection
                                out.write("END OF CONNECTION\n");$
                                out.flush();
                                break;
                            }

                            //Split line into commands                         
                            String[] cmds = line.split(" ");
                            String op1 = comds[0];
                            String oper = comds[1];
                            String op3 = comds[2];
                            
                            if(cmds.length != 3){
                                out.write("NOT GOOD SIZE OF ARGUMENTS => EXPECTED 3\n");
                                out.flush();
                                continue;
                            } else if{
                                try{
                                    double op1 = Double.parseDouble(comds[0]);
                                    double op2 = Double.parseDouble(comds[2]);

                                } catch (NumberFormatException e){
                                    out.write("ERROR: BAD OPERANDS\n");
                                    out.flush();
                                    continue;
                                }

                                /* TODO parseOperation & try */
                                //try{

                                //} catch(RuntimeException e){
                                //    out.write("ERROR: OPERATION NOT SUPPORTED\n");
                                //    out.flush();
                                //    continue;
                                //}

                                /* Proceed with calculation */
                                if(comds[1].compareToIgnoreCase(ADD) == 0){
                                    out.write(op1 + op2);
                                } else if(comds[1].compareToIgnoreCase(MULT) == 0){
                                    out.write(op1 + op2);
                                } 

                            }
                        }

                    } catch (IOException e){
                        System.out.println("Server: socket ex.: " + e);
                    }
            }
        } catch( IOException e){
            System.out.println("Server: server socket ex.: " + e);
        }
    }
}
  

/* 
 * SERVER::socket (): create a socket
 * SERVER::bind()   : specify which address/port to listen to
 * SERVER::listen() : mark the socket as a passive socket that can accept connections
 * SERVER::accept() : wait for a connection request from a client
 * ---- blocks until connection from client ----
 * socket() :
 * connect(): establish a connetion with a server
 * write()  :
 * SERVER::read()   :read data from a socket
 * ------ server do sth ----
 * SERVER::write()  : write data to the socket
 * read()   :
 * close()  :
 * SERVER::read()   :
 * SERVER::close()  : close the socket
 */




/* TODO enlever */
/* The server should send a welcome message that lists the supported operations.
The client should print the supported operations, wait for user input, send the user's command to the server, and print the result.
Ensure that the application handles unexpected errors and unknown operations gracefully. */