package io.ace.nord.command.commands;

import io.ace.nord.command.Command;

public class Prefix extends Command {

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
