package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class KeyPearl extends Hack {

    public KeyPearl() {
        super("KeyPearl", Category.COMBAT, 13854341);
    }

    int startingHand;
    int pearlHand;

    public void onEnable() {
        startingHand = mc.player.inventory.currentItem;
        if (InventoryUtil.findItemInHotbar(Items.ENDER_PEARL) == -1) {
            this.disable();
        } else {
            pearlHand = InventoryUtil.findItemInHotbar(Items.ENDER_PEARL);
        }
        mc.player.connection.sendPacket(new CPacketHeldItemChange(pearlHand));
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        this.disable();
    }
}
