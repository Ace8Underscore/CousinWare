package io.ace.nordclient.hacks.player;

import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.CPacketPlayer;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Freecam extends Hack {

    double startX, startY, startZ;
    EntityOtherPlayerMP entity;

    public Freecam() {
        super("Freecam", Category.PLAYER, 10319687);
    }

    public void onUpdate() {
        mc.player.noClip = true;
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer.Position) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayer.Rotation) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            event.setCanceled(true);
        }
    }

    public void onEnable() {
        entity = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        entity.copyLocationAndAnglesFrom(mc.player);
        entity.rotationYaw = mc.player.rotationYaw;
        entity.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(696984837, entity);
        mc.player.capabilities.allowFlying = true;
        startX = mc.player.posX;
        startY = mc.player.posZ;
        startZ = mc.player.posZ;




    }
    public void onDisable() {
        mc.player.noClip = false;
        mc.player.capabilities.allowFlying = false;
        mc.player.capabilities.isFlying = false;
        //mc.player.setPosition(startX, startY, startZ);
        mc.world.removeEntity(entity);
    }

}
