package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.managers.HackManager;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
    @Override
    public String[] getClientAlias() {
        return new String[]{"bind", "b"};
    }

    @Override
    public String getClientSyntax() {
        return "bind (Hack) (Key)";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        int key = Keyboard.getKeyIndex(args[1].toUpperCase());
        HackManager.getHacks().forEach(h ->{
            if(args[0].equalsIgnoreCase(h.getName())){
                h.setBind(key);
                Command.sendClientSideMessage(args[0] + " binded to " + args[1].toUpperCase());
            }
        });
    }
}