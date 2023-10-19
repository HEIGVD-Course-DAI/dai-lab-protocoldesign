package ch.heig.dai.lab.protocoldesign;

import java.util.HashSet;

public class CommandHandler {

    private static final String COMMAND_OK_RESPONSE = "OK";
    private static final String COMMAND_ERROR_RESPONSE = "NOK";
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

    public String handleCommand(String fullCommand) {

        if (fullCommand == null || fullCommand.isEmpty()) {
            return COMMAND_ERROR_RESPONSE;
        }

        String[] tokens = fullCommand.split(TOKEN_SEPARATOR);

        // check if first token is a valid command
        if (!commandsStringMap.contains(tokens[0])) {
            return COMMAND_ERROR_RESPONSE;
        }

        // convert first token
        CommandEnum command = CommandEnum.valueOf(tokens[0]);

        // parse other tokens
        double[] parsedTokens = new double[tokens.length - 1];

        for (int i = 1; i < tokens.length; ++i) {
            try {
                parsedTokens[i - 1] = Double.parseDouble(tokens[i]);
            } catch (NumberFormatException e) {
                return COMMAND_ERROR_RESPONSE;
            }
        }
        double result;
        switch (command) {
            case help:
                return COMMAND_OK_RESPONSE + " " + listOfCommands();
            case quit:
                return COMMAND_OK_RESPONSE;
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
                    return COMMAND_ERROR_RESPONSE;
                result = MathHelper.div(parsedTokens[0], parsedTokens[1]);
                break;
            case inv:
                if (parsedTokens.length != 1)
                    return COMMAND_ERROR_RESPONSE;
                result = MathHelper.inv(parsedTokens[0]);
                break;
            case pow:
                if (parsedTokens.length != 2)
                    return COMMAND_ERROR_RESPONSE;
                result = MathHelper.pow(parsedTokens[0], parsedTokens[1]);
                break;
            default:
                // We will never get here
                return COMMAND_ERROR_RESPONSE;
        }
        return String.format("%s %f", COMMAND_OK_RESPONSE, result);
    }

}
