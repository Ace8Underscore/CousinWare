package io.ace.nordclient.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.managers.HackManager;

public class Toggle extends Command {

    boolean found;
    @Override
    public String[] getClientAlias() {
        return new String[]{"toggle"};
    }

    @Override
    public String getClientSyntax() {
        return "Toggle (Hack)";
    }

    @Override
    public void onClientCommand(String command, String[] args) {
        found = false;
        HackManager.getHacks().forEach(m -> {
            if (m.getName().equalsIgnoreCase(args[0])) {
                if (m.isEnabled()) {
                    m.disable();
                    Command.sendClientSideMessage(args[0] + " Was" + ChatFormatting.DARK_RED +" Disabled!");
                    found = true;
                } else if (!m.isEnabled()) {
                    m.enable();
                    found = true;
                    Command.sendClientSideMessage(args[0] + " Was" + ChatFormatting.GREEN + " Enabled!");
                }
            }
        });
        if(!found && args.length == 1) Command.sendClientSideMessage(ChatFormatting.DARK_RED + "Hack not found!");

    }
}



    /*    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        String toggleHack = args.toString();
        HackManager.getHackByName(toggleHack).toggle();

    }
} */
