package io.ace.nord.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.command.Command;
import io.ace.nord.managers.CommandManager;
import io.ace.nord.managers.HackManager;

public class AllCommands extends Command {
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
