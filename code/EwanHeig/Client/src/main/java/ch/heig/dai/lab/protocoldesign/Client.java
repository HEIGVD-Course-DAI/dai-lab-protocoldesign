package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "10.192.94.57";//todo: comment savoir bonne addresse ?
    final int SERVER_PORT = 4269;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        boolean quit = false,srvReady = false;
        String usrIn = "";
        String SRV_READY = "READY";
        try {
            Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            //si le serveur est connecté et a fini d'afficher le message de bienvenu
            if( in.readLine().equals(SRV_READY)){
                srvReady = true;
            }

            if (srvReady){
                while(!quit){
                    usrIn = stdin.readLine();
                    if(usrIn == "QUIT"){
                        quit = true;
                    }else{
                        out.write(usrIn + "\n");
                        out.flush();
                    }
                }
            }




        }catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }
    }
}