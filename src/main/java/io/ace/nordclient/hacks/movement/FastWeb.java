package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class FastWeb extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public FastWeb() {
        super("FastWeb", Category.MOVEMENT);
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.player.isInWeb) {
            mc.player.motionY = 1.1 / -5;
        }
    }
}
