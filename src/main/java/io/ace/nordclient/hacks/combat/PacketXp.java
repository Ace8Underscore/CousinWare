package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.mixin.accessor.ICPacketPlayer;
import io.ace.nordclient.utilz.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class PacketXp extends Hack {

    public PacketXp() {
        super("PacketXp", Category.COMBAT, 3386060);
    }
    int xpHand;
    int startingHand;
    boolean spoofPitch;

    @Override
    public void onUpdate() {
        xpHand = InventoryUtil.findItemInHotbar(Items.EXPERIENCE_BOTTLE);
        if (Keyboard.isKeyDown(this.getBind())) {
            if (InventoryUtil.findItemInHotbar(Items.EXPERIENCE_BOTTLE) != -1) {
                spoofPitch = true;
                mc.player.connection.sendPacket(new CPacketHeldItemChange(xpHand));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                mc.player.connection.sendPacket(new CPacketHeldItemChange(startingHand));


            }

        } else {
            this.disable();
        }
    }
    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            ((ICPacketPlayer) event.getPacket()).setPitch((float) 90);
        }
    }

    @Override
    public void onEnable() {
        spoofPitch = false;
        mc.player.rotationPitch += 0.0004;
        startingHand = mc.player.inventory.currentItem;
        //mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.cameraYaw, 90, mc.player.onGround));

    }

    @Override
    public void onDisable() {
        mc.player.inventory.currentItem = startingHand;
        mc.player.connection.sendPacket(new CPacketHeldItemChange(startingHand));

    }
}
