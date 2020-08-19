package io.ace.nord.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nord.command.Command;
import io.ace.nord.hacks.Hack;
import io.ace.nord.managers.CommandManager;
import io.ace.nord.managers.HackManager;

import java.util.Comparator;

public class Hacks extends Command {
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


