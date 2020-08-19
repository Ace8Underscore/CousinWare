package io.ace.nord.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.command.Command;
import io.ace.nord.managers.HackManager;

public class Drawn extends Command{
    private boolean found;

    @Override
    public String[] getClientAlias() {return new String[] {"draw", "drawn"};}

    @Override
    public String getClientSyntax() {return "drawn (Hack)";}

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {


        found = false;
        HackManager.getHacks().forEach(m -> {
            if (m.getName().equalsIgnoreCase(args[0])) {
                if (m.isDrawn()) {
                    m.drawn = false;
                    found = true;
                    Command.sendClientSideMessage(args[0] + " Was" + ChatFormatting.GREEN + " Drawn!");
                } else if (!m.isDrawn()) {
                    m.drawn = true;
                    found = true;
                    Command.sendClientSideMessage(args[0] + " Was" + ChatFormatting.RED + " UnDrawn!");
                }
            }
        });
        if(!found && args.length == 1) Command.sendClientSideMessage(ChatFormatting.DARK_RED + "Hack not found!");

    }
}



