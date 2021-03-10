package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;

public class Jesus extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public Jesus() {
        super("Jesus", Category.MOVEMENT, "Walk on water duh", 12149820);
    }

    int delay = 0;

    @Override
    public void onUpdate() {
        if (mc.player.getHeldItemMainhand().getItemUseAction().equals(EnumAction.EAT) && mc.player.getHeldItemMainhand().getItem() instanceof ItemFood && mc.gameSettings.keyBindUseItem.isKeyDown()) {
            delay++;
            if (delay > 1) {
                mc.player.motionX = -0;
                delay = 0;
            }
        }
    }
}

/*
speed lowhop bypass
delay++;
            if (delay > 10) {
                mc.player.setVelocity(.000001, 0, .000001);
                delay = 0;
            }
 */
