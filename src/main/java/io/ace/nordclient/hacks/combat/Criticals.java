package io.ace.nordclient.hacks.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
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
        super("Critcals", Category.COMBAT, 4241221);
        ArrayList<String> critModes = new ArrayList<>();
        critModes.add("Normal");
        critModes.add("Strict");
        CousinWare.INSTANCE.settingsManager.rSetting(critMode = new Setting("CritMode", this, "Strict", critModes, "CriticalsCritMode"));

    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (critMode.getValString().equalsIgnoreCase("norm")) {
            if (event.getPacket() instanceof CPacketUseEntity) {
                final CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
                if (packet.getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10000000149011612, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));

                }
            }
        }
        if (critMode.getValString().equalsIgnoreCase("strict")) {
            if (event.getPacket() instanceof CPacketUseEntity) {
                final CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
                if (packet.getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.06260280169278, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0726027996066, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));

                }
            }
        }
    }

    @Override
    public String getHudInfo() {
        return  "\u00A77[\u00A7f" + critMode.getValString() + "\u00A77]";
    }
}
