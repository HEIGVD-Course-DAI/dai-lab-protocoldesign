package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;

public class Server{
    final int SERVER_PORT = 4269;

    private static String calculation(String input){
        String[] i = input.split(" ");
        switch(i[0]){
            case "INIT":
                return "Operation: ADD SUB MULT DIV, format: OP a b";
            case"ADD":
                return Integer.toString(Integer.parseInt(i[1])+Integer.parseInt(i[2]));
            case"SUB":
                return Integer.toString(Integer.parseInt(i[1])-Integer.parseInt(i[2]));
            case"DIV":
                if(Integer.parseInt(i[2]) == 0){
                    return "ERROR: cannot divid by 0";
                }
                else{
                    return Integer.toString(Integer.parseInt(i[1])/Integer.parseInt(i[2]));
                }
            case"MULT":
                return Integer.toString(Integer.parseInt(i[1])*Integer.parseInt(i[2]));
            default:
                return "Operation must be one of these: -ADD -SUB -MULT -DIV in this format: OP a b";
        }
    }
    public static void main(String[]args){
        //Create a new server and run it
        System.out.println("Server ready");
        while(true){
            Server server = new Server();
            server.run();
        }
    }

    private void run(){
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){

            while(true){
                try(Socket clientsocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream(), UTF_8));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientsocket.getOutputStream(), UTF_8))){
                    String i;
                    while((i = in.readLine()) != null){
                        if(i.equals("QUIT")){
                            out.write("Program exited\n");
                            out.flush();
                            break;
                        }
                        System.out.println(i);
                        out.write(calculation(i)+ "\n");
                        out.flush();
                    }

                }
                catch(IOException e){
                    System.out.println("Server: socket ex.:" + e);
                }
            }
        }catch(IOException e){
            System.out.println("Server: server socket ex.:" + e);
        }


    }
}

