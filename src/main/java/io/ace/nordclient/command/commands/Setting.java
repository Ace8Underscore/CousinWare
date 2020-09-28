package io.ace.nordclient.command.commands;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;

public class Setting extends Command {
    @Override
    public String[] getClientAlias() {
        return new String[]{"setting"};
    }

    @Override
    public String getClientSyntax() {
        return "setting <Module>";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        for(Hack h : HackManager.getHacks()) {
            if(h.getName().equalsIgnoreCase(args[0])) {
                CousinWare.INSTANCE.settingsManager.getSettingsByMod(h).forEach(s->{
                    if(s.isSlider()) {
                        Command.sendClientSideMessage("Slider Setting " + s.getDisplayName() + ": Value " + s.getValDouble());
                    }
                    if(s.isCheck()){
                        Command.sendClientSideMessage("Boolean Setting " + s.getDisplayName() + ": Value " + s.getValBoolean());
                    }
                    if(s.isCombo()){
                        Command.sendClientSideMessage("Mode Setting " + s.getDisplayName() + ": Value " + s.getValString());
                    }
                   /* if(s.isColorPicker()){
                        s.setValColor(Color.getColor(args[2]));
                        Command.sendClientSideMessage(s.getDisplayName() + " set to " + s.getValColor());
                    } */
                    /*if(s.isCustomString()){
                        s.setCustomVal(args[2]);
                        Command.sendClientSideMessage(s.getDisplayName() + " set to " + s.getCustomVal());
                    } */
                });
            }
        }
    }
}