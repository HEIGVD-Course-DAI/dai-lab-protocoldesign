package ch.heig.dai.lab.protocoldesign;
import com.sun.source.tree.WhileLoopTree;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int SERVER_PORT = 1234;
    final String regexPattern = "^(add|sub|mult|div);(\\d+(\\.\\d{1,2})?);(\\d+(\\.\\d{1,2})?)$";
    public static void logMessage(String s){
        System.out.println(s+"\n");
    }
    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    public static void welcomeClient(BufferedOutputStream out){
        String msg = "Connected to the beast calculator ! \n";
        try{
            out.write(msg.getBytes());
            out.flush();
        }catch (Exception e){
            logMessage("Server: welcomeClient ex.: " + e);
        }
    }

    private void run() {

        try (ServerSocket serverSocket =  new ServerSocket(SERVER_PORT)){

            logMessage("Server: Launched, waiting on connexion\n");
            // loop wait until a client connect
            boolean connected = false;
            while (true) {
                try(Socket socket = serverSocket.accept()){
                    BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

                    if(!connected){
                        welcomeClient(out);
                        logMessage("Connected client : " + socket.getInetAddress());
                        connected = true;
                    }

                    // loop wait until the client send a message
                    while(true){
                        byte[] buffer = new byte[1024];
                        int read = in.read(buffer);
                        if(read == -1){
                            break;
                        }
                        String msg = new String(buffer, 0, read);
                        logMessage("Client : " + msg);
                        if(msg.equals("exit")){
                            break;
                        }
                        if(msg.matches(regexPattern)){
                            boolean flagDevideByZero = false;
                            String[] split = msg.split(";");
                            String operation = split[0];
                            double num1 = Double.parseDouble(split[1]);
                            double num2 = Double.parseDouble(split[2]);
                            double result = 0;
                            switch (operation){
                                case "add":
                                    result = num1 + num2;
                                    break;
                                case "sub":
                                    result = num1 - num2;
                                    break;
                                case "mult":
                                    result = num1 * num2;
                                    break;
                                case "div":
                                    if(num2 == 0){
                                        flagDevideByZero = true;
                                        break;
                                    }
                                    result = num1 / num2;
                                    break;
                            }
                            if(flagDevideByZero){
                                out.write("Error, you can't devide by zero !\n".getBytes());
                                out.flush();
                            }else{
                                out.write(("Result : " + result + "\n").getBytes());
                                out.flush();
                            }
                        }else{
                            out.write("Wrong format !\n".getBytes());
                            out.flush();
                        }
                    }
                }catch (IOException e){
                    logMessage("Server : socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            logMessage("Server: server socket ex.: " + e);
        }


    }
}