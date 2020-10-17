package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Velocity extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public Velocity() {
        super("Velocity", Category.MOVEMENT);
    }

    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            if(((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
                event.setCanceled(true);
        }
        if(event.getPacket() instanceof SPacketExplosion)
            event.setCanceled(true);

    }
}
