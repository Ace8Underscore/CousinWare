package io.ace.nord.command.commands;

import io.ace.nord.command.Command;
import io.ace.nord.managers.CommandManager;
import io.ace.nord.managers.HackManager;

public class Hacks extends Command {
    @Override
    public String[] getClientAlias() {return new String[] {"allhack", "allhacks", "hacks", "hack"};}

    @Override
    public String getClientSyntax() {return "All of the Client Hacks";}

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage("Hacks: TestCommand, ArrayList");
    }
}
