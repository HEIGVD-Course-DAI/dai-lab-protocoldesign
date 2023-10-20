package ch.heig.dai.lab.protocoldesign;
import java.net.*;
import java.io.*;
import java.lang.Math.*;
import java.util.HashMap;
import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 3141;
    enum errors {BAD_ARG, BAD_VAL, BAD_OP};
    String[] OP = {"ADD", "MULT", "SUB", "DIV", "POW", "MEAN", "CLOSE"};

    boolean errorHappened = false;

    private BufferedWriter output;
    //final HashMap<OP, String> operations = new HashMap<OP, String>();

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {

       
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)){
            while(true){
                try (Socket socket = serverSocket.accept();
                    var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                    var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8)) ) {

                        String welcomeMessage = "Voici les operations : ";
                        output = out;
                        for(String s : OP){
                            welcomeMessage += (s + " ");
                        }
                        
                        out.write(welcomeMessage + "\n");
                        out.flush();
                        String line;
                        while ((line = in.readLine()) != null){
                            

                            String resultLine = operationSelector(line);
                            if(!errorHappened){
                                out.write(resultLine + "\n");
                                out.flush();
                                
                                
                            }
                            errorHappened = false;
                            

                            
                            
                        }


                    
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }

    } 

    private String operationSelector(String input){

        String[] split = input.split(" ");
        String output = "";
        if(split.length <= 2)
            errorMessage(errors.BAD_ARG);
        
           
        Integer result = 0;
        switch (split[0]){

            case "ADD":
                result = add(stringToInt(split));
                break;
            case "POW":
                result = pow(stringToInt(split));
                break;    
            case "MULT":
                result = mult(stringToInt(split));
                break; 
            case "DIV":
                result = div(stringToInt(split));
                break; 
            case "SUB":
                result = sub(stringToInt(split));
                break;    
            case "MEAN":
                result = mean(stringToInt(split));
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

    private int add(int[] values){
        
        int sum = 0;
        for(int v : values){
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

    private int sub(int[] values){ //TODO
        
        int diff = values[0];
        for(int v  = 1; v < values.length; v++){
            diff -= values[v];
        }
        
        return diff;
    }

    private int div(int[] values){ //TODO
        
        int quot = values[0];
        for(int v  = 1; v < values.length; v++){
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

    private int mean(int[] values){
        int sum = add(values);
        return sum/values.length;
    }


}