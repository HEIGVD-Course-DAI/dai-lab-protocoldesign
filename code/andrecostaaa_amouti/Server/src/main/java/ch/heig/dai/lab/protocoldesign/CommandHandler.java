package ch.heig.dai.lab.protocoldesign;

import java.util.HashSet;

public class CommandHandler {

    private static final String OK_RESPONSE = "OK";
    private static final String ERROR_INVALID_COMMAND = "NOK - INVALID COMMAND";
    private static final String ERROR_INVALID_ARGUMENTS = "NOK - INVALID ARGUMENTS";

    private static final String TOKEN_SEPARATOR = " ";
    private static final String END_LINE_IN_COMMAND = "|";

    private enum CommandEnum {
        add, mul, sub, div, pow, inv, help, quit
    }

    private final HashSet<String> commandsStringMap;

    private static HashSet<String> getCommandsString() {
        var commands = new HashSet<String>();

        for (CommandEnum c : CommandEnum.values()) {
            commands.add(c.name());
        }
        return commands;
    }

    public CommandHandler() {
        commandsStringMap = getCommandsString();
    }

    public String listOfCommands() {
        StringBuilder result = new StringBuilder();
        for (CommandEnum c : CommandEnum.values()) {
            result.append(c.name()).append(END_LINE_IN_COMMAND);
        }

        return result.toString();
    }

    public CommandResponse handleCommand(String fullCommand) {

        if (fullCommand == null || fullCommand.isEmpty()) {
            return invalidCommand();
        }

        String[] tokens = fullCommand.split(TOKEN_SEPARATOR);

        // check if first token is a valid command
        if (!commandsStringMap.contains(tokens[0])) {
            return invalidCommand();
        }

        // convert first token
        CommandEnum command = CommandEnum.valueOf(tokens[0]);

        // parse other tokens
        double[] parsedTokens = new double[tokens.length - 1];

        for (int i = 1; i < tokens.length; ++i) {
            try {
                parsedTokens[i - 1] = Double.parseDouble(tokens[i]);
            } catch (NumberFormatException e) {
                return invalidArguments();
            }
        }
        double result;
        switch (command) {
            case help:
                return helpResponse();
            case quit:
                return quitResponse();
            case add:
                result = MathHelper.add(parsedTokens);
                break;
            case mul:
                result = MathHelper.mul(parsedTokens);
                break;
            case sub:
                result = MathHelper.sub(parsedTokens);
                break;
            case div:
                if (parsedTokens.length != 2)
                    return invalidArguments();
                result = MathHelper.div(parsedTokens[0], parsedTokens[1]);
                break;
            case inv:
                if (parsedTokens.length != 1)
                    return invalidArguments();
                result = MathHelper.inv(parsedTokens[0]);
                break;
            case pow:
                if (parsedTokens.length != 2)
                    return invalidArguments();
                result = MathHelper.pow(parsedTokens[0], parsedTokens[1]);
                break;
            default:
                // We will never get here
                return invalidCommand();
        }
        return response(result);
    }

    private CommandResponse invalidArguments(){
        return CommandResponse.errorCommandResponse(ERROR_INVALID_ARGUMENTS);
    }

    private CommandResponse invalidCommand(){
        return CommandResponse.errorCommandResponse(ERROR_INVALID_COMMAND);
    }

    private CommandResponse quitResponse(){
        return CommandResponse.quitResponse(OK_RESPONSE);
    }
    private CommandResponse helpResponse(){
     return CommandResponse.commandResponse(OK_RESPONSE + " " + listOfCommands());
    }
    private CommandResponse response(double result){
        return CommandResponse.commandResponse(String.format("%s %f", OK_RESPONSE, result));
    }
}
