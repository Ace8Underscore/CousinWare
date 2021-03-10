package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.potion.Potion;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.Objects;

public class PlayerEffects extends Hack {

    public PlayerEffects() {
        super("PlayerEffects", Category.MISC, 16286842);
    }
    @Listener
    public void onUpdate(PacketEvent.Receive event) {
            for (Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityPlayer) {
                    if (!entity.getName().equals(mc.player.getName())) {
                        //if (entity.getEntityId() == ((SPacketEntityEffect) event.getPacket()).getEntityId()) {
                        if (event.getPacket() instanceof SPacketEntityEffect) {
                            //if (((SPacketEntityEffect) event.getPacket()).)

                            Command.sendClientSideMessage(entity.getName() + " Just Got The Effect " + Objects.requireNonNull(Potion.getPotionById(((SPacketEntityEffect) event.getPacket()).getEffectId())).getName() + " For " + ((SPacketEntityEffect) event.getPacket()).getDuration() / 20);
                        //}
                    }

                }
            }
        }

    }
}
