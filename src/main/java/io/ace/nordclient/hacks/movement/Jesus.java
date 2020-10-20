package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.hacks.Hack;

public class Jesus extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public Jesus() {
        super("Jesus", Category.MOVEMENT, "Walk on water duh", 26);
    }

    @Override
    public void onUpdate() {

        if (mc.player.isOverWater() && !mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = 0;
        }
    }
}
