package io.ace.nordclient.hacks.render;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.util.EnumHand;

public class Swing extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public Swing() {
        super("Swing", Category.RENDER, "Swing With Your OffHand");
    }

    @Override
    public void onUpdate() {
            mc.player.swingingHand = EnumHand.OFF_HAND;
    }
}
