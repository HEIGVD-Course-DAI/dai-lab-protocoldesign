package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

public class Server{
    final int SERVER_PORT = 4269;
    private String[] OP = {"ADD","SUB","DIV","MULT"};
    String SRV_READY = "READY", ERROR = "ERROR:calculation impossible";

    public static void main(String[]args){
        //Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run(){
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Serv On");

        }
        catch(IOException e){
            System.out.println("Client:exc.:"+e);
        }


        while(true){
            try{
                System.out.println("1");
                Socket clientSocket = serverSocket.accept();//todo: problème ici
                System.out.println("2");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),UTF_8));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                String line = in.readLine();

                boolean keepRunning = true;

                out.println("Welcome ! Here are the available operations :");
                for(String op : OP){
                    out.println(op);
                }
                out.println("enter QUIT to leave");
                out.println(SRV_READY);

                out.flush();

                while(keepRunning){
                    //if the command to quit is sent
                    if(line.equalsIgnoreCase("QUIT")){
                        keepRunning = false;
                        out.flush();
                        continue;
                    }

                    String[] operation = line.split("");
                    if(operation.length != 3){
                        out.println(ERROR);
                    }
                    else{
                        String operator = operation[0];
                        int val1 = Integer.parseInt(operation[1]);
                        int val2 = Integer.parseInt(operation[2]);
                        int result;

                        switch(operator){
                            case"ADD":
                                result = val1 + val2;
                                out.println(result);
                                break;

                            case"SUB":
                                result = val1 - val2;
                                out.println(result);
                                break;
                            case"DIV":
                                if(val2 == 0){
                                    out.println(ERROR);
                                }
                                else{
                                    result = val1 / val2;
                                    out.println(result);
                                }
                                break;
                            case"MULT":
                                result = val1 * val2;
                                out.println(result);
                                break;
                            default:
                                out.println(ERROR);
                        }
                    }
                    out.flush();
                }
                clientSocket.close();
                in.close();
                out.close();
            }
            catch(IOException e){
                System.out.println("Server:socketex.:"+e);
            }
        }

    }
}

