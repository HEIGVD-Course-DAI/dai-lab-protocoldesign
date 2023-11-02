package ch.heig.dai.lab.protocoldesign;
import java.net.*;
import java.io.*;
import java.lang.Math.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 31415;
    enum errors {BAD_ARG, BAD_VAL, BAD_OP};
    String[] OP = {"ADD", "MULT", "SUB", "DIV", "POW", "MEAN", "CLOSE"};

    boolean errorHappened = false;

    private BufferedWriter output;
    

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

       
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            while(true){
                System.out.println("Waiting new connection...");
                try (Socket socket = serverSocket.accept();
                    var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                    var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8)) ) {

                        System.out.println("Connection enabled");
                        String welcomeMessage = "Voici les operations : ";
                        
                        for(String s : OP){
                            welcomeMessage += (s + " ");
                        }
                        output = out;
                        out.write(welcomeMessage + "\n");
                        out.flush();
                        String line;
                        while ((line = in.readLine()) != null){
                            
                            String[] split = line.split(" ");
                            
                            //Check if close message
                            if(split[0].equals("CLOSE")){
                                
                                out.write("Closing connection...\n");
                                out.flush();
                                System.out.println("Closing connection...");
                                socket.close();
                                break;
                            }
                            else{
                                
                                //Get the result of the opperation and send it if no error happened
                                String resultLine = operationSelector(split);
                                if(!errorHappened){
                                    out.write(resultLine + "\n");
                                    out.flush();
                                }

                                errorHappened = false;
                            }
                        }
                        System.out.println("Connection closed");
                        

                    
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    } 

    private String operationSelector(String[] input){

        if(input.length <= 2)
            errorMessage(errors.BAD_ARG);
        
        String output = "";
        Double result = 0.0;
        switch (input[0]){

            case "ADD":
                result = add(stringToDouble(input));
                break;
            case "POW":
                result = pow(stringToDouble(input));
                break;    
            case "MULT":
                result = mult(stringToDouble(input));
                break; 
            case "DIV":
                result = div(stringToDouble(input));
                break; 
            case "SUB":
                result = sub(stringToDouble(input));
                break;    
            case "MEAN":
                result = mean(stringToDouble(input));
                break;  
                     
            default:
                errorMessage(errors.BAD_OP);    

        }
    
        output = "RESULT " + result.toString();
        return output;
        
        
        

        
    }

    private void errorMessage(errors errorCode){
        
        errorHappened = true;
        String errorMsg = "ERROR: ";
        switch(errorCode){
            case BAD_ARG:
                errorMsg += "Bad arguments";
                break;
            case BAD_OP:
                errorMsg += "Unknown operation";
                break;
            case BAD_VAL: 
                errorMsg += "Argument wrong type";
                break;
            
        }

        try {
            System.out.println(errorMsg);
            output.write(errorMsg + "\n");
            output.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }

    private int[] stringToInt(String[] input){
        int[] value = new int[input.length -1];
        for(int i = 0; i < input.length-1; i++){
            try {
                value[i] = Integer.parseInt(input[i+1]);
            } catch (Exception e) {
                // TODO: handle exception
                errorMessage(errors.BAD_VAL);
            }
            
        }


        return value;
    }

    private double[] stringToDouble(String[] input){
        double[] value = new double[input.length -1];
        for(int i = 0; i < input.length-1; i++){
            try {
                value[i] = Double.parseDouble(input[i+1]);
            } catch (Exception e) {
                // TODO: handle exception
                errorMessage(errors.BAD_VAL);
            }
            
        }


        return value;
    }

    private int add(int[] values){
        
        int sum = 0;
        for(int v : values){
            sum += v;
        }
        
        return sum;
    }

    public double add(double[] values) {   
        double sum = 0.0;
        for(double v : values){
            sum += v;
        }
        return sum;
    }

    

    private int mult(int[] values){
        int mult = 1;
        for(int v : values){
            mult *= v;
        }
        return mult;
    }

    private double mult(double[] values){
        double mult = 1.0;
        for(double v : values){
            mult *= v;
        }
        return mult;
    }

    private int sub(int[] values){ //TODO
        
        int diff = values[0];
        for(int v  = 1; v < values.length; v++){
            diff -= values[v];
        }
        return diff;
    }

    private double sub(double[] values){ //TODO
        
        double diff = values[0];
        for(int v  = 1; v < values.length; v++){
            diff -= values[v];
        }
        return diff;
    }

    private int div(int[] values){ //TODO
        
        int quot = values[0];
        for(int v  = 1; v < values.length; v++){
            if(values[v] == 0)
                errorMessage(errors.BAD_VAL);
            quot /= values[v];
        }
        
        return quot;
    }

    private double div(double[] values){ //TODO
        
        double quot = values[0];
        for(int v  = 1; v < values.length; v++){
            if(values[v] == 0 || values[v] == 0.0)
                errorMessage(errors.BAD_VAL);
            quot /= values[v];
        }
        
        return quot;
    }

    private int pow(int[] values){
        
        if(values.length > 2){
            errorMessage(errors.BAD_ARG);
        }
        return (int)(Math.pow(values[0], values[1]));
        
    }

    private double pow(double[] values){
        
        if(values.length > 2){
            errorMessage(errors.BAD_ARG);
        }
        if(values[1] % 1 != 0){
            errorMessage(errors.BAD_VAL);
        }
            
        return (double)(Math.pow(values[0], values[1]));
        
    }

    private int mean(int[] values){
        int sum = add(values);
        return sum/values.length;
    }

    private double mean(double[] values){
        double sum = add(values);
        return sum/values.length;
    }


}