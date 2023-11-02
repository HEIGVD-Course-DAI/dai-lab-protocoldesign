package ch.heig.dai.lab.protocoldesign;
import java.net.*;
import java.io.*;


import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 31415;
    enum errors {BAD_ARG, BAD_VAL, BAD_OP};
    String[] OP = {"ADD", "MULT", "SUB", "DIV", "POW", "MEAN", "CLOSE"};

    boolean errorHappened = false;

    //To have the output buffer accessible by errorMessage method
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

                        System.out.println("Connected to " + socket.getRemoteSocketAddress().toString());

                        //----Send welcome message---- 
                        String welcomeMessage = "Voici les operations : ";
                        
                        for(String s : OP){
                            welcomeMessage += (s + " ");
                        }
                        output = out;
                        out.write(welcomeMessage + "\n");
                        out.flush();

                        //------Process the command from the client------
                        String line;
                        while ((line = in.readLine()) != null){
                            
                            String[] split = line.split(" ");
                            
                            //Check if close message was send
                            if(split[0].toUpperCase().equals("CLOSE")){
                                
                                out.write("CLOSING\n");
                                out.flush();
                                System.out.println("Closing connection...");
                                break;
                            }
                            else{
                                
                                //Get the result of the opperation and send it if no error happened
                                String resultLine = process(split);
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

    private String process(String[] input){

        //If not enough argument, send an error message
        if(input.length <= 2)
            errorMessage(errors.BAD_ARG);
        
        String outputTxt = "";
        Double result = 0.0;

        //Select the opperation and get the result
        switch (input[0].toUpperCase()){

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
        //Remove the .0 if the result is an integer
        String resultTxt;
        if(result % 1 == 0){
            resultTxt = result.toString().substring(0, result.toString().length()-2);
        }
        else{
            resultTxt = result.toString();
        }

        outputTxt = "RESULT " + resultTxt;
        return outputTxt;
        
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
            //Send the error message
            System.out.println(errorMsg);
            output.write(errorMsg + "\n");
            output.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    /*
     * Convert a string array to a double array
     */
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

    /*
     * Return the sum of all values in values array parameter
     */
    public double add(double[] values) {   
        double sum = 0.0;
        for(double v : values){
            sum += v;
        }
        return sum;
    }
    
    /*
     * Return the multiplication of all values from the array
     */
    private double mult(double[] values){
        double mult = 1.0;
        for(double v : values){
            mult *= v;
        }
        return mult;
    }
    
    /*
     * Return the difference of all values from the array
     */
    private double sub(double[] values){
        
        double diff = values[0];
        for(int v  = 1; v < values.length; v++){
            diff -= values[v];
        }
        return diff;
    }
    
    /*
     * Return the division of all the values from the array
     */
    private double div(double[] values){ //TODO
        
        double quot = values[0];
        for(int v  = 1; v < values.length; v++){
            if(values[v] == 0 || values[v] == 0.0)
                errorMessage(errors.BAD_VAL);
            quot /= values[v];
        }
        
        return quot;
    }
   
    /*
     * Return the pow of the 1st value by the 2nd
     */
    private double pow(double[] values){
        
        if(values.length > 2){
            errorMessage(errors.BAD_ARG);
        }
        if(values[1] % 1 != 0){
            errorMessage(errors.BAD_VAL);
        }
            
        return (double)(Math.pow(values[0], values[1]));
        
    }
    
    /*
     * Return the mean of all values from the array
     */
    private double mean(double[] values){
        double sum = add(values);
        return sum/values.length;
    }


}