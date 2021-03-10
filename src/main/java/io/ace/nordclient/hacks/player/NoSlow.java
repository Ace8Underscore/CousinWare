package io.ace.nordclient.hacks.player;

import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.MathUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class NoSlow extends Hack {

    public NoSlow() {
        super("NoSlow", Category.PLAYER, 10060642);
    }

    @Override
    public void onUpdate() {
        if (mc.player.isHandActive()) {
            final double[] dir = MathUtil.directionSpeed(.26);
            mc.player.motionX = dir[0];
            mc.player.motionZ = dir[1];
        }

    }
    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            if (((CPacketPlayer) event.getPacket()).onGround) {
                //event.setCanceled(true);
                //mc.player.connection.sendPacket(new CPacketPlayer(((CPacketPlayer) event.getPacket()).onGround));
            }
        }
    }
}
