package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;

public class Prefix extends Command {

    /**
     * @author Ace________/Ace_#1233
     */

    @Override
    public String[] getClientAlias() { return new String[] {"prefix"};}

    @Override
    public String getClientSyntax() { return "Prefix (character)"; }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        String clientPrefixBeforeChange = Command.getClientPrefix();
        if (args[0] != "") {
            Command.setClientPrefix(args[0]);
            if (!clientPrefixBeforeChange.equals(Command.getClientPrefix())) {
                Command.sendClientSideMessage("Command prefix set to " + Command.getClientPrefix());
            } else {
                Command.sendClientSideMessage("This is Already Your Prefix!");
            }
        }
    }
}
