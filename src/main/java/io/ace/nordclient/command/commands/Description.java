package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.managers.HackManager;

public class Description extends Command {

    /**
     * @author Ace________/Ace_#1233
     */

    @Override
    public String[] getClientAlias() {
        return new String[]{"description"};
    }

    @Override
    public String getClientSyntax() {
        return "description (Hack)";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage(HackManager.getHackByName(args[0]).getDescription());
    }
}