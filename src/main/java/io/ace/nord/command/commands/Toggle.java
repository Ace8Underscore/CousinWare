package io.ace.nord.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.command.Command;
import io.ace.nord.hacks.Hack;
import io.ace.nord.managers.HackManager;

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
                    Command.sendClientSideMessage(args[0] + " Was" + ChatFormatting.RED +"Disabled!");
                    found = true;
                } else if (!m.isEnabled()) {
                    m.enable();
                    found = true;
                    Command.sendClientSideMessage(args[0] + " Was" + ChatFormatting.GREEN + "Enabled!");
                }
            }
        });
        if(!found && args.length == 1) Command.sendClientSideMessage(ChatFormatting.DARK_RED + "Module not found!");

    }
}



    /*    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        String toggleHack = args.toString();
        HackManager.getHackByName(toggleHack).toggle();

    }
} */
