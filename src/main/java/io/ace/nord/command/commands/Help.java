package io.ace.nord.command.commands;

import io.ace.nord.command.Command;

public class Help extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"help", "command"};
    }

    @Override
    public String getSyntax() {
        return "help";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage("This Client doesnt have a gui yet so its currently all command base");
    }
}