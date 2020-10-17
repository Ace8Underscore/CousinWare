package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Jesus extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public Jesus() {
        super("Jesus", Category.MOVEMENT, "Walk on water duh");
    }

    @Override
    public void onUpdate() {

        if (mc.player.isOverWater() && !mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = 0;
        }
    }
}
