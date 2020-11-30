package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class TwoBeePacketLogger extends Hack {

    public int useItem = 0;
    public int useItemOnBlock = 0;

    public  TwoBeePacketLogger() {
        super("2bPacketLogger", Category.MISC, 1120862);
    }

    public void onUpdate() {

    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayerTryUseItem) {
            useItem++;
            Command.sendClientSideMessage("Packet: CPacketPlayerTryUseItem :: Sent Amount: " + useItem);

        }
        if (packet instanceof CPacketPlayerTryUseItemOnBlock) {
            useItemOnBlock++;
            Command.sendClientSideMessage("Packet: CPacketPlayerTryUseItemOnBlock :: Sent Amount: " + useItemOnBlock);

        }

    }

    public void onDisable() {
        useItem = 0;
        useItemOnBlock = 0;
    }
}
