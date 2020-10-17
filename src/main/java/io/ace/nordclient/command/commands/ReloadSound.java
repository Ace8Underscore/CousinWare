package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;

public class ReloadSound extends Command {

    /**
     * @author Ace________/Ace_#1233
     */

    @Override
    public String[] getClientAlias() {
        return new String[]{"reloadsound", "reloadsounds", "soundreload"};
    }

    @Override
    public String getClientSyntax() {
        return "reloadsounds";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        try {
            mc.getSoundHandler().sndManager.reloadSoundSystem();
            Command.sendClientSideMessage("Reloaded Sounds");
        }catch (Exception e) {
            Command.sendClientSideMessage("Failed To Reload Sounds");

        }
    }

}
