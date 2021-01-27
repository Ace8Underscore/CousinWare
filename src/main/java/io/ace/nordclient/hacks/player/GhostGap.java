package io.ace.nordclient.hacks.player;

import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class GhostGap extends Hack {

    public GhostGap() {
        super("GhostGap", Category.PLAYER, 3106299);
    }

    int startingHand;

    @Override
    public void onUpdate() {
        int gapHand = InventoryUtil.findItemInHotbar(Items.GOLDEN_APPLE);
        if (InventoryUtil.findItemInHotbar(Items.GOLDEN_APPLE) != -1) {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(gapHand));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));



        }
    }

    @Override
    public void onEnable() {
        startingHand = mc.player.inventory.currentItem;
    }
}