package io.ace.nordclient.hacks.player;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class AntiVoid extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public AntiVoid() {
        super("AntiVoid", Category.PLAYER);
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        Double yLevel = mc.player.posY;
        if (yLevel <=.5) {
            mc.player.jump();
            Command.sendClientSideMessage("Attempting to get " + mc.player.getName() + " Out of the Void!");
        }
    }
}
