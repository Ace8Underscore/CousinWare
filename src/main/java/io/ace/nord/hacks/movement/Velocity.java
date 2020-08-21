package io.ace.nord.hacks.movement;

import io.ace.nord.event.PacketEvent;
import io.ace.nord.hacks.Hack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Velocity extends Hack {
    public Velocity() {
        super("Velocity", Category.MOVEMENT);
    }
    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            if(((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
                event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketExplosion) {
            event.setCanceled(true);
        }
    }
}
