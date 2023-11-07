package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.*;
import static java.nio.charset.StandardCharsets.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Server {
    public static class Value {
        double value;
        public Value() { this(0); }
        public Value(double value) { this.value = value; }
        public double get() { return value; }
        public void set(double value) { this.value = value; }
        public String toString() { return Double.toString(value); }
    }
    private static final int SERVER_PORT = 1234;
    private static final String sendChar = "\n";
    private static final String endServer = "....";
    private static final String password = "admin";
    private String wod = "That's all folks!";
    private static boolean adminRight = false;
    private volatile boolean closeConn = false;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
        // Server.closeConn();
    }

    private void run() {

        // Implement Server functionalities
        try(ServerSocket ss = new ServerSocket(SERVER_PORT)){
            while(true){
                try (Socket s = ss.accept()){
                    var in = new BufferedReader(new InputStreamReader(s.getInputStream(), UTF_8));
                    var out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), UTF_8));

                    banner(out);
                    out.flush();

                    String line;
                    while((line = in.readLine()) != null){
                        commandSwitch(line, out);
                        if(closeConn){ s.close(); }
                    }
                } catch (IOException e) {
                    System.out.println("Server: socket ex.:" + e);
                }
            }
        } catch (IOException e){
            System.out.println("Server: server socket ex.: " + e);
        }
    }

    public void banner(BufferedWriter out) throws IOException {
        write(out, "---------------------------------------------------------");
        write(out, "--- Welcome on a server implementing the FBP protocol ---");
        write(out, "---------------------------------------------------------");
        write(out, sendChar);
        write(out, "Word of the day: " + wod);
        write(out, sendChar);
        write(out, "Please select a program by its name: ");
        write(out, "Opérateur arithmétique :");
        write(out, "- NEG v1 / INV v1 / SQRT v1 / LOG v1 / ADD v1 v2 / SUB v1 v2 / MUL v1 v2 / DIV v1 v2");
        write(out, "Commande :");
        write(out, "- CloseConn / Quit / Exit : Termine la session");
        write(out, "- Ping : Répond pong");
        write(out, "- banner : Affiche ce message");
        write(out, "- wod : affiche le message du jour");
        write(out, "- pass <arg> : Passage en mode privilégié");
        write(out, "- wod <arg> : Change of the word of the day (admin)");
        write(out,sendChar + endServer);
    }
    public void commandSwitch(String cmd, BufferedWriter out) throws IOException {
        var params = cmd.split(" ");

        if (params.length == 0) {
            error(out, "No command specified");
        } else if (params.length == 1) {
            switch (params[0]) { // commands without arguments
                case "PING" :
                case "ping" :
                case "Ping" :
                    ping(out);
                    break;
                case "TIME"  :
                case "TODAY" :
                case "NOW"   :
                case "time"  :
                case "today" :
                case "now"   :
                case "Time"  :
                case "Today" :
                case "Now"   :
                    now(out);
                    break;
                case "ROLE" :
                case "role" :
                case "Role" :
                    role(out);
                    break;
                case "LIST"     :
                case "COMMANDS" :
                case "list"     :
                case "commands" :
                case "List"     :
                case "Commands" :
                    commands(out);
                    break;
                case "AUTH"     :
                case "PASSWORD" :
                case "PASS"     :
                case "auth"     :
                case "password" :
                case "pass"     :
                case "Auth"     :
                case "Password" :
                case "Pass"     :
                    auth(out);
                    break;
                case "BANNER"   :
                case "banner"   :
                case "Banner"   :
                    banner(out);
                    break;
                case "WOD" :
                case "wod" :
                case "Wod" :
                    wod(out);
                    break;
                case "QUIT" :
                case "EXIT" :
                case "quit" :
                case "exit" :
                case "Quit" :
                case "Exit" :
                    closeConn(out);
                    break;
                default :
                    error(out);
            }
        } else if (params.length == 2) { // unary operators/commands
            switch (params[0]) {
                case "NEG" :
                case "neg" :
                case "Neg" :
                case "-"   :
                    neg(out, params);
                    break;
                case "INV" :
                case "inv" :
                case "Inv" :
                case "/"   :
                    inv(out, params);
                    break;
                case "SQRT" :
                case "sqrt" :
                case "Sqrt" :
                    sqrt(out, params);
                    break;
                case "POW2" :
                case "pow2" :
                case "Pow2" :
                case "^2"   :
                    pow2(out, params);
                    break;
                case "LN"  :
                case "LOG" :
                case "ln"  :
                case "log" :
                case "Ln"  :
                case "Log" :
                    ln(out, params);
                    break;
                case "LOG10" :
                case "log10" :
                case "Log10" :
                    log(out, params);
                    break;
                case "EXP" :
                case "exp" :
                case "Exp" :
                    exp(out, params);
                    break;
                case "AUTH"     :
                case "PASSWORD" :
                case "PASS"     :
                case "auth"     :
                case "password" :
                case "pass"     :
                case "Auth"     :
                case "Password" :
                case "Pass"     :
                    auth(out, params);
                    break;
                default :
                    error(out);
            }
        } else if (params.length == 3) { // binary operators commands
            switch (params[0]) {
                case "ADD" :
                case "add" :
                case "Add" :
                case "+"   :
                    add(out, params);
                    break;
                case "SUB" :
                case "sub" :
                case "Sub" :
                case "-"   :
                    sub(out, params);
                    break;
                case "MULT" :
                case "mult" :
                case "Mult" :
                case "*"    :
                    mult(out, params);
                    break;
                case "DIV" :
                case "div" :
                case "Div" :
                case "/"   :
                    div(out, params);
                    break;
                case "POW" :
                case "pow" :
                case "Pow" :
                case "^"   :
                    pow(out, params);
                    break;
                case "LOG_B" :
                case "log_b" :
                case "Log_b"   :
                    log_b(out, params);
                    break;
                default :
                    error(out);
            }
        } else {
            switch(params[0]) { // wod admin command
                case "WOD":
                case "wod":
                case "Wod":
                    wod(out, params);
                    break;
        }
        }
        out.write(endServer + sendChar);
        out.flush();
    }
    public void error(BufferedWriter out, String msg) throws IOException {
        write(out, "Error: " + msg);
    }
    public void error(BufferedWriter out) throws IOException {
        error(out, "command not recognized" + sendChar);
    }
    public void ping(BufferedWriter out) throws IOException{
        write(out,"pong");
    }
    public void now(BufferedWriter out) throws IOException {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        write(out, format.format(today));
    }
    public void role(BufferedWriter out) throws IOException {
        if(adminRight){
            write(out, "You are in admin mode");
        } else {
            write(out, "You are in user mode");
        }
    }
    public void commands(BufferedWriter out) throws IOException {
        write(out, "List of commands:");
        write(out, "- commands w/o arguments: " + "PING, " + "TIME (TODAY, NOW), " + "ROLE, " + "LIST (COMMANDS), " + "AUTH (PASSWORD, PASS) (requires admin rights), " + "BANNER, " + "WOD, " + "QUIT (EXIT)");
        write(out, "- unary operators: " + "NEG (-), " + "INV (/), " + "SQRT, " + "POW2 (^2), " + "LN (LOG), " + "LOG10, " + "EXP, " + "AUTH (PASSWORD, PASS)");
        write(out, "- binary operators: " + "ADD (+), " + "SUB (-), " + "MULT (*), " + "DIV (/), " + "POW (^), " + "LOG_B");
        write(out, "- commands without limits of arguments: " + "WOD (requires admin rights)");
    }
    public void auth(BufferedWriter out) throws IOException {
        if(adminRight){
            adminRight = false;
            write(out, "Switched back to user mode");
        } else {
            error(out, "command requires admin rights");
        }
    }
    public void wod(BufferedWriter out) throws IOException {
        write(out, "Word of the day : " + wod);
    }
    public void closeConn(BufferedWriter out) throws IOException {
        adminRight = false;
        closeConn = true;
        write(out, "Connection closed");

    }

    public void getParams(String[] params, Value a) {
        a.set(Double.parseDouble(params[1]));
    }
    public void getParams(String[] params, Value a, Value b) {
        a.set(Double.parseDouble(params[1]));
        b.set(Double.parseDouble(params[2]));
    }
    public void neg(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            getParams(params, a);
            write(out, new Value(-a.get()) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void inv(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            getParams(params, a);
            write(out, new Value(1/a.get()) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void sqrt(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            getParams(params, a);
            write(out, new Value(Math.sqrt(a.get())) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void pow2(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            getParams(params, a);
            write(out, new Value(Math.pow(a.get(), 2)) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void ln(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            getParams(params, a);
            write(out, new Value(Math.log(a.get())) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void log(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            getParams(params, a);
            write(out, new Value(Math.log10(a.get())) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void exp(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            getParams(params, a);
            write(out, new Value(Math.exp(a.get())) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void add(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            Value b = new Value();
            getParams(params, a, b);
            write(out, new Value(a.get() + b.get()) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void sub(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            Value b = new Value();
            getParams(params, a, b);
            write(out, new Value(a.get() - b.get()) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void mult(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            Value b = new Value();
            getParams(params, a, b);
            write(out, new Value(a.get() * b.get()) + sendChar);
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void div(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            Value b = new Value();
            getParams(params, a, b);
            if (b.get() == 0) {
                error(out, "second argument cannot be 0");
            } else {
                write(out,new Value(a.get() / b.get()) + sendChar);
            }
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void pow(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            Value b = new Value();
            getParams(params, a, b);
            if (a.get() == 0 && b.get() == 0) {
                error(out, "operation 0^0 is not defined");
            } else {
                write(out,new Value(Math.pow(a.get(), b.get())) + sendChar);
            }
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void log_b(BufferedWriter out, String[] params) throws IOException {
        try {
            Value a = new Value();
            Value b = new Value();
            getParams(params, a, b);
            if (a.get() == 0 && b.get() == 0) {
                error(out, "operation 0^0 is not defined");
            } else {
                write(out,new Value(Math.log(a.get()) / Math.log(b.get())) + sendChar);
            }
        } catch (Exception e) {
            error(out, "arguments not valid");
        }
    }
    public void auth(BufferedWriter out, String[] params) throws IOException {
        if(params[1].equals(password)){
            adminRight = true;
            write(out, "Switched to admin mode");
        } else {
            error(out, "wrong admin password");
        }
    }
    public void wod(BufferedWriter out, String[] params) throws IOException {
        if(adminRight && params.length > 1){
            StringBuilder sb = new StringBuilder(params[1]);
            for(int i = 2; i < params.length; i++){
                sb.append(" ").append(params[i]);
            }
            wod = sb.toString();
            write(out, "Word of the day changed to " + wod);
        } else {
            error(out, "command requires admin rights");
        }
    }
    public void write(BufferedWriter out, String msg) throws IOException {
        if (!msg.endsWith(sendChar)) {
            msg += sendChar;
        }
        out.write(msg);
    }
}