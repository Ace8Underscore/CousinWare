package io.ace.nordclient.hacks.player;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class PacketCanceller extends Hack {

    Setting SPacketEntityMetadata;
    Setting SPacketMoveVehicle;
    Setting SPacketCollectItem;
    Setting SPacketCloseWindow;
    Setting SPacketSetPassengers;
    Setting SPacketEntityTeleport;
    Setting SPacketPlayerAbilities;
    Setting SPacketChat;


    public PacketCanceller() {
        super("PacketCanceller", Category.PLAYER, 1125279);
        CousinWare.INSTANCE.settingsManager.rSetting(SPacketEntityMetadata = new Setting("SPacketEntityMetadata", this, false, "SPacketEntityMetadata"));
        CousinWare.INSTANCE.settingsManager.rSetting(SPacketMoveVehicle = new Setting("SPacketMoveVehicle", this, false, "SPacketMoveVehicle"));
        CousinWare.INSTANCE.settingsManager.rSetting(SPacketCollectItem = new Setting("SPacketCollectItem", this, false, "SPacketCollectItem"));
        CousinWare.INSTANCE.settingsManager.rSetting(SPacketCloseWindow = new Setting("SPacketCloseWindow", this, false, "SPacketCloseWindow"));
        CousinWare.INSTANCE.settingsManager.rSetting(SPacketSetPassengers = new Setting("SPacketSetPassengers", this, false, "SPacketSetPassengers"));
        CousinWare.INSTANCE.settingsManager.rSetting(SPacketEntityTeleport = new Setting("SPacketEntityTeleport", this, false, "SPacketEntityTeleport"));
        CousinWare.INSTANCE.settingsManager.rSetting(SPacketPlayerAbilities = new Setting("SPacketPlayerAbilities", this, false, "SPacketEntityTeleport"));
        CousinWare.INSTANCE.settingsManager.rSetting(SPacketChat = new Setting("SPacketChat", this, false, "SPacketEntityTeleport"));

    }
    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        Packet packet = event.getPacket();
        if (packet instanceof SPacketEntityMetadata && SPacketEntityMetadata.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof SPacketMoveVehicle && SPacketMoveVehicle.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof SPacketCollectItem && SPacketCollectItem.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof SPacketCloseWindow && SPacketCloseWindow.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof SPacketSetPassengers && SPacketSetPassengers.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof SPacketEntityTeleport && SPacketEntityTeleport.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof SPacketPlayerAbilities && SPacketPlayerAbilities.getValBoolean()) {
            event.setCanceled(true);
        }

        if (packet instanceof SPacketChat && SPacketChat.getValBoolean()) {
            event.setCanceled(true);
        }
    }
}
