package io.ace.nordclient.command.commands;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;

import java.awt.*;

public class Set extends Command {
    @Override
    public String[] getClientAlias() {
        return new String[]{"set"};
    }

    @Override
    public String getClientSyntax() {
        return "set <Module> <Setting> <Value>";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        for(Hack h : HackManager.getHacks()) {
            if(h.getName().equalsIgnoreCase(args[0])) {
                CousinWare.INSTANCE.settingsManager.getSettingsByMod(h).stream().filter(s -> s.getDisplayName().equalsIgnoreCase(args[1])).forEach(s->{
                    if(s.isSlider()) {
                        if(Double.parseDouble(args[2]) > s.getMax()) s.setValDouble(s.getMax());
                        if(Double.parseDouble(args[2]) < s.getMin()) s.setValDouble(s.getMin());
                        if(!(Double.parseDouble(args[2]) > s.getMax()) && !(Double.parseDouble(args[2]) < s.getMin())) s.setValDouble(Double.parseDouble(args[2]));
                        Command.sendClientSideMessage(s.getDisplayName() + " set to " + s.getValDouble());
                    }
                    if(s.isCheck()){
                        s.setValBoolean(Boolean.parseBoolean(args[2]));
                        Command.sendClientSideMessage(s.getDisplayName() + " set to " + s.getValBoolean());
                    }
                    if(s.isCombo()){
                        if(!s.getOptions().contains(args[2])) return;
                        s.setValString(args[2]);
                        Command.sendClientSideMessage(s.getDisplayName() + " set to " + s.getValString());
                    }
                    if(s.isColorPicker()){
                        s.setValColor(Color.getColor(args[2]));
                        Command.sendClientSideMessage(s.getDisplayName() + " set to " + s.getValColor());
                    }
                    if(s.isCustomString()){
                        s.setCustomVal(args[2]);
                        Command.sendClientSideMessage(s.getDisplayName() + " set to " + s.getCustomVal());
                    }
                });
            }
        }
    }
}