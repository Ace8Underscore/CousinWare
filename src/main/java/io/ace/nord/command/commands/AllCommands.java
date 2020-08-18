package io.ace.nord.command.commands;

import io.ace.nord.command.Command;
import io.ace.nord.managers.CommandManager;

public class AllCommands extends Command {
    @Override
    public String[] getClientAlias() {return new String[] {"allcommand", "allcommands", "commands", "command"};}

    @Override
    public String getClientSyntax() {return "All of the Client Commands";}

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage("Commands: Help, Prefix, Commands, Toggle, Hacks, ");
    }
}
