package io.ace.nordclient.hacks.player;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

public class GhostGap extends Hack {

    public GhostGap() {
        super("GhostGap", Category.PLAYER, 1);
    }

    public void onUpdate() {
        ItemStack gap = new ItemStack(Items.GOLDEN_APPLE);
        if (mc.player.getHeldItemMainhand().equals(gap)) {
            mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            //mc.getConnection().sendPacket(new CPacketPlayerDigging(Action., mc.player.getPosition(), EnumFacing.SOUTH));
           // mc.getConnection().sendPacket(new SPacketEntityStatus(mc.player, (byte) 9));
            //super.onUpdate();
        }
    }
}

/*
protected void onItemUseFinish() {
        if (!this.activeItemStack.isEmpty() && this.isHandActive()) {
            this.connection.sendPacket(new SPacketEntityStatus(this, (byte)9));
            super.onItemUseFinish();
        }

    }
 */