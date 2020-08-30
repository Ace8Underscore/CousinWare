package io.ace.nordclient.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;

public class Hacks extends Command {

    /**
     * @author Ace________/Ace_#1233
     */

    @Override
    public String[] getClientAlias() {
        return new String[]{"allhack", "allhacks", "hacks", "hack"};
    }

    @Override
    public String getClientSyntax() {
        return "Hacks";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage("Hacks: ");
        HackManager.getHacks()
                .stream()
                .filter(Hack::isEnabled)
                .forEach(h -> {
                    Command.sendClientSideMessage(ChatFormatting.GREEN + h.getName());
                });
        HackManager.getHacks()
                .stream()
                .filter(Hack::isDisabled)
                .forEach(h -> {
                    Command.sendClientSideMessage(ChatFormatting.RED + h.getName());
                });

    }
}
       // Command.sendClientSideMessage("Hacks: TestCommand, ArrayList, AutoTotem, ");


