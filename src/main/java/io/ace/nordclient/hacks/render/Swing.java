package io.ace.nordclient.hacks.render;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Swing extends Hack {
    public Swing() {
        super("Swing", Category.RENDER, "Swing With Your OffHand");
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.world == null)
            return;

            mc.player.swingingHand = EnumHand.OFF_HAND;

    }
}
