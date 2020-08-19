package io.ace.nord.command.commands;

import io.ace.nord.command.Command;

public class Help extends Command {
    @Override
    public String[] getClientAlias() {
        return new String[]{"help", "command"};
    }

    @Override
    public String getClientSyntax() {
        return "Help";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage("This Client Was Made By Ace_#1233 Enjoy :D. To see Commands Please use the command " + prefix + "commands");
    }
}