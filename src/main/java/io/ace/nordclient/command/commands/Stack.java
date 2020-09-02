package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;

public class Stack extends Command{

    /**
     * @author Ace________/Ace_#1233
     */

    @Override
    public String[] getClientAlias() { return new String[] {"Stack"};}

    @Override
    public String getClientSyntax() { return "Stack (amount)"; }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        mc.player.inventory.getCurrentItem().getItem().setMaxStackSize(Integer.parseInt(args[0]));
        Command.sendClientSideMessage("Set " + mc.player.getHeldItemMainhand().getDisplayName() + " Max Stack Count To " + args[0]);




    }
}
