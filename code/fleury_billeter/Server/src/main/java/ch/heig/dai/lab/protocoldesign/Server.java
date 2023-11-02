package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.regex.*;

public class Server {
    final int SERVER_PORT = 1234;
    public int adminRight = 0;

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
                    // READER
                    var in = new BufferedReader(
                            new InputStreamReader(
                                    s.getInputStream(), UTF_8));

                    // WRITTER
                    var out = new BufferedWriter(
                            new OutputStreamWriter(
                                    s.getOutputStream(), UTF_8));

                    out.write("---------------------------------------------------------\n");
                    out.write("--- Welcome on MySuperServer                          ---\n");
                    out.write("---------------------------------------------------------\n\n");

                    out.write("Please select a program by its name or its number : \n");
                    out.write("1. Calculator\n");
                    out.write("2. Ping\n");
                    out.write("3. Textual RPG\n\n");

                    if(adminRight == 1){
                        out.write("Admin: >");
                    }else {
                        out.write("Guest: >");
                    }

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
        if(cmd.equals("ping")) {
            ping(out);
        }else if(cmd.contains("ADD") || cmd.contains("add")){
            add(out, cmd);
        }else if(cmd.contains("SUB") || cmd.contains("sub")) {
            sub(out, cmd);
        }else if(cmd.contains("password")){
            auth(cmd);
        }else {
            out.write("Commande Non reconnue \n");
        }
        if(adminRight == 1){
            out.write("Admin: >");
        }else {
            out.write("Guest: >");
        }
        out.flush();
    }

    //
    // Liste des différentes commandes
    //
    public void ping(BufferedWriter out) throws  IOException{
        out.write("pong\n");
    }

    public void add(BufferedWriter out, String cmd) throws IOException{
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(cmd);
        int res = 0;

        m.find();
        var arg1 = Integer.valueOf(m.group());
        m.find();
        var arg2 = Integer.valueOf(m.group());

        res = arg1 + arg2;

        System.out.println("Resultat de l'addition = " + res + "\n");
        out.write(res + "\n");
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
            adminRight = 1;
        }
    }
}