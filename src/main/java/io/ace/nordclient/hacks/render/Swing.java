package io.ace.nordclient.hacks.render;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.util.EnumHand;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Swing extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public Swing() {
        super("Swing", Category.RENDER, "Swing With Your OffHand");
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
            mc.player.swingingHand = EnumHand.OFF_HAND;
//
    }
}
