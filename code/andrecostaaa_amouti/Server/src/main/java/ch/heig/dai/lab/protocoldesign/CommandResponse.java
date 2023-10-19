package ch.heig.dai.lab.protocoldesign;

public class CommandResponse {

    private boolean isQuit;
    private String response;
    private boolean isError;


    private CommandResponse(String res)
    {
        response = res;
        isQuit = false;
        isError = false;
    }
    private CommandResponse(String res, boolean error, boolean quit)
    {
        response = res;
        isQuit = quit;
        isError = error;
    }

    public static CommandResponse errorCommandResponse(String response){
        return new CommandResponse(response, true, false);
    }

    public static CommandResponse quitResponse(String response){
        return new CommandResponse(response, false, true);
    }
    public static CommandResponse commandResponse(String response)
    {
        return new CommandResponse(response);
    }
    public String getResponse()
    {
        return response;
    }
    public boolean isQuit(){
        return isQuit;
    }
    public boolean isError(){
        return isError;
    }

}
