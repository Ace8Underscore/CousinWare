package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;

public class Ping extends Command{

    /**
     * @author Ace________/Ace_#1233
     */

    @Override
    public String[] getClientAlias() { return new String[] {"ping"};}

    @Override
    public String getClientSyntax() { return "ping"; }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        if (!mc.isSingleplayer()) {
            long ping = mc.getCurrentServerData().pingToServer;
            Command.sendClientSideMessage(String.valueOf(ping));
        } else {
            Command.sendClientSideMessage("You're In Singleplayer");
        }



    }
}
