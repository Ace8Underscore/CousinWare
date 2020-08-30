package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.managers.CommandManager;

public class AllCommands extends Command {

    /**
     * @author Ace________/Ace_#1233
     */

    @Override
    public String[] getClientAlias() {return new String[] {"allcommand", "allcommands", "commands", "command"};}

    @Override
    public String getClientSyntax() {return "Commands";}

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage("Commands: ");
        CommandManager.getClientCommands()
                .stream()
                .forEach(cc -> {
                    Command.sendClientSideMessage(cc.getClientSyntax());
                });
    }
}
