package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.regex.*;

public class Server {
    static final int SERVER_PORT = 1234;
    static final String sendChar = "\n";
    static final String endServer = "....";
    static boolean adminRight = false;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

        // Implement Server functionalities
        try(ServerSocket ss = new ServerSocket(SERVER_PORT)){
            while(true){
                try (Socket s = ss.accept()){
                    var in = new BufferedReader(new InputStreamReader(s.getInputStream(), UTF_8));
                    var out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), UTF_8));

                    out.write("---------------------------------------------------------" + sendChar);
                    out.write("--- Welcome on MySuperServer                          ---" + sendChar);
                    out.write("---------------------------------------------------------" + sendChar + sendChar);

                    out.write("Please select a program by its name or its number : " + sendChar);
                    out.write("1. Calculator" + sendChar);
                    out.write("2. Ping" + sendChar);
                    out.write("3. Textual RPG" + sendChar + sendChar);

                    out.write(endServer + sendChar);

                    out.flush();

                    String line;
                    while((line = in.readLine()) != null){
                        commandSwitch(line, out);
                    }
                }catch (IOException e) {
                    System.out.println("Server: socket ex.:" + e);
                }
            }
        }catch (IOException e){
            System.out.println("Server: server socket ex.: " + e);
        }
    }

    // Selection de method en fonction de la commande appelée
    public void commandSwitch(String cmd, BufferedWriter out) throws IOException {
        var params = cmd.split(" ");

        if (params.length == 0) {
            error(out, "No command specified" + sendChar);
        }

        if (params.length == 1) {
            if (params[0].equals("ping")) {
                ping(out);
            } else if (params[0].equals("quit") || params[0].equals("exit")) {
                closeConn();
            } else {
                error(out);
            }
        }

        if (params.length == 2) {
            error(out, "NEG, SQRT, POW non yet implemented");
        }

        if (params.length == 3) {
            if (params[0].equals("ADD") || params[0].equals("add")) {
                // vérifier que parseInt ne lève pas d'exception !!!
                add(out, Integer.parseInt(params[1]), Integer.parseInt(params[2]));
            } else if (params[0].equals("SUB") || params[0].equals("sub")) {
                sub(out, cmd);
            }
        }

        out.write(endServer + sendChar);
        out.flush();
        /*
        if(params[0].equals("ping")) {
            ping(out);
        }else if(params[0].equals("ADD") || params[0].equals("add")){
            add(out, Integer.parseInt(params[1]), Integer.parseInt(params[2]));
        }else if(params[0].equals("SUB") || params[0].equals("sub")) {
            sub(out, cmd);
        }else if(params[0].equals("password")) {
            auth(cmd);
        }else if(params[0].equals("quit") || params[0].equals("exit")){
            closeConn();
        }else {
            out.write("Commande Non reconnue \n");
        }
        if(adminRight == 1){
            out.write("Admin: >\n");
        }else {
            out.write("Guest: >\n");
        }
        out.flush();
        */
    }

    //
    // Liste des différentes commandes
    //

    public void error(BufferedWriter out, String msg) throws IOException {
        if (!msg.endsWith(sendChar)) {
            msg += sendChar;
        }
        out.write(msg);
    }
    public void error(BufferedWriter out) throws IOException {
        error(out, "Non recognized command" + sendChar);
    }
    public void ping(BufferedWriter out) throws  IOException{
        out.write("pong" + sendChar);
    }

    public void add(BufferedWriter out, int a, int b) throws IOException{
        System.out.println("Resultat de l'addition = " + (a+b) + "\n");
        out.write((a+b) + "\n");
    }

    public void sub(BufferedWriter out, String cmd) throws IOException{
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(cmd);
        Integer res = 0;
        m.find();
        var arg1 = Integer.valueOf(m.group());
        m.find();
        var arg2 = Integer.valueOf(m.group());

        res = arg1 - arg2;

        System.out.println("Resultat de soustraction = " + res + "\n");
        out.write(res + "\n");
    }

    public void auth(String cmd){
        var params = cmd.split(" ");

        if(params[1].equals("salut")){
            adminRight = true;
        }
    }

    public void closeConn(){
        adminRight = false;
    }
}