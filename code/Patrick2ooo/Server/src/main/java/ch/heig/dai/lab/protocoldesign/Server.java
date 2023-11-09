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

    private static String calculation(String input) {
        String[] i = input.split(" ");       
        switch (i[0]) {
            case "INIT":
                return "-ADD -SUB -MUL -DIV";

            case "ADD":
                return (Integer.toString(Integer.parseInt(i[1]) + Integer.parseInt(i[2])));
                
            case "SUB":
                return (Integer.toString(Integer.parseInt(i[1]) - Integer.parseInt(i[2]))) ;
            
            case "MUL":
                return (Integer.toString(Integer.parseInt(i[1]) * Integer.parseInt(i[2]))) ;
            
            case "DIV":
                return (Integer.toString(Integer.parseInt(i[1]) / Integer.parseInt(i[2]))) ;    
        
            default:
                return "Unknown operation";
        }
    } 
    public static void main(String[] args) {
        // Create a new server and run it
        System.out.println("Server running");
        while(true){
            Server server = new Server();
            server.run();
        }
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
        
        while (true) {
            try (Socket socket = serverSocket.accept();
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));                
            var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))){
                String i;               
                while((i = in.readLine()) != null){    
                    if(i.equals("QUIT")){
                        out.write("Program exited \n");
                        out.flush();
                        break;
                    }              
                    
                    System.out.println(i);
                    out.write(calculation(i) + "\n");
                    out.flush();
                    
                }
                } catch (IOException e) {
                System.out.println("Server: socket ex.: " + e);
                }
            } // while(true)
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        } 
    } 


}