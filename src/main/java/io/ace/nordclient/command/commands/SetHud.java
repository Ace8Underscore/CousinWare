package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.hud.Hud;
import io.ace.nordclient.managers.HudManager;

public class SetHud extends Command {
    @Override
    public String[] getClientAlias() {
        return new String[]{"setHud"};
    }

    @Override
    public String getClientSyntax() {
        return "setHud <Hud> <x/y> <Value>";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        for(Hud h : HudManager.getHuds()) {
            if(h.getName().equalsIgnoreCase(args[0])) {
                if (!args[2].equals("")) {
                    if (args[1].equalsIgnoreCase("x")) h.setX(Integer.parseInt(args[2]));
                    if (args[1].equalsIgnoreCase("y")) h.setY(Integer.parseInt(args[2]));

                }
            }
        }
    }
}