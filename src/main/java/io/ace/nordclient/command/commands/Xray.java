package io.ace.nordclient.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.command.Command;

public class Xray extends Command{

    /**
     * @author Ace________/Ace_#1233
     */

    @Override
    public String[] getClientAlias() {
        return new String[]{"Xray", "xray"};
    }

    @Override
    public String getClientSyntax() {
        return "Xray (add/remove) (blockname)";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        if (args[0].equals("add") && !io.ace.nordclient.hacks.render.Xray.xrayBlocks.contains(args[1])) {
            io.ace.nordclient.hacks.render.Xray.xrayBlocks.add(args[1]);
            Command.sendClientSideMessage(ChatFormatting.GREEN + args[1] + ChatFormatting.WHITE + " Has Been Added To Xray ");
        }



        if (args[0].equals("del")) {
            if (io.ace.nordclient.hacks.render.Xray.xrayBlocks.contains(args[1])) {
                io.ace.nordclient.hacks.render.Xray.xrayBlocks.remove(args[1]);
                Command.sendClientSideMessage(ChatFormatting.GREEN + args[1] + ChatFormatting.WHITE + " Has Been Removed From Xray ");

            }

        }
    }
}
