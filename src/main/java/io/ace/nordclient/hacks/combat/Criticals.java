package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class Criticals extends Hack {

    Setting critMode;

    public Criticals() {
        super("Critcals", Category.COMBAT, 1);
        ArrayList<String> critModes = new ArrayList<>();
        critModes.add("norm");
        critModes.add("2");
        critModes.add("3");
        critModes.add("4");
        critModes.add("5");
        CousinWare.INSTANCE.settingsManager.rSetting(critMode = new Setting("CritMode", this, "1", critModes, "CriticalsCritMode"));

    }

    public void onUpdate() {

    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (critMode.getValString().equalsIgnoreCase("norm")) {
            if (event.getPacket() instanceof CPacketUseEntity) {
                final CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
                if (packet.getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10000000149011612, mc.player.posZ, false));
                    mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));

                }
            }
        }
        if (critMode.getValString().equalsIgnoreCase("2")) {
            if (event.getPacket() instanceof CPacketUseEntity) {
                final CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
                if (packet.getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10000000149011612, mc.player.posZ, true));
                    mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10000000149011612, mc.player.posZ, false));
                    mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));

                }
            }
        }
    }

}
