package io.ace.nordclient.hacks.render;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.util.EnumHand;

/**
 * @author Ace________/Ace_#1233
 */

public class Swing extends Hack {

    public Swing() {
        super("Swing", Category.RENDER, "Swing With Your OffHand", 443646);
    }

    @Override
    public void onUpdate() {
            mc.player.swingingHand = EnumHand.OFF_HAND;
    }
}
