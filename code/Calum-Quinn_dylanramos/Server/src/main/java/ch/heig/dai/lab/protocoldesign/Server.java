package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Server {
    final int SERVER_PORT = 32976;
    final String OPERATIONS =   "ADD <firstNumber> <secondNumber> \n" +
                                "SUB <firstNumber> <secondNumber> \n" +
                                "MUL <firstNumber> <secondNumber> \n" +
                                "DIV <firstNumber> <secondNumber>";
    static final String[] OPS = {"ADD","SUB","MUL","DIV"};

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    // Check if the number string represents a number
    public static boolean isNumeric(String number) {
        return number.matches("-?\\d+");
    }

    // Carries out the operation
    public static String operation(String op, String num1, String num2) {
        int number1 = Integer.parseInt(num1);
        int number2 = Integer.parseInt(num2);
        switch (op) {
            case "ADD" : return Integer.toString(number1 + number2);
            case "SUB" : return Integer.toString(number1 - number2);
            case "MUL" : return Integer.toString(number1 * number2);
            case "DIV" :
                if (number2 != 0) {
                    return Integer.toString(number1 / number2);
                }
            default: return "UNSUPPORTED OPP";
        }
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            try (Socket socket = serverSocket.accept();
                 var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                 var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                out.write(OPERATIONS);
                out.flush();

                String[] request = in.readLine().split(" ");
                String opp = request[0];
                String num1 = request[1];
                String num2 = request[2];
                boolean validOp = false;

                for (String op : OPS) {
                    if (Objects.equals(opp, op)) {
                        validOp = true;
                        break;
                    }
                }

                // Check if valid operation
                if (!validOp) {
                    out.write("UNSUPPORTED OPP");
                    out.flush();
                }
                else {
                    if(!(isNumeric(num1) && isNumeric(num2))) {
                        out.write("UNSUPPORTED NUM");
                        out.flush();
                    }
                    else {
                        // Valid operation and numbers
                        out.write(operation(opp,num1,num2));
                        out.flush();
                    }
                }
            }
            catch(IOException e) {
                System.out.println("Server: socket ex.: " + e);
            }

        }
        catch (IOException e) {
            System.out.println(("Server: server socket ex.: " + e));
        }
    } 
}