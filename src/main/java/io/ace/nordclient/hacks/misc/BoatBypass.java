package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.item.ItemBoat;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;

public class BoatBypass extends Hack {

    public BoatBypass() {
        super("2bBoatPlace", Category.MISC, 7338316);
    }

    @Override
    public void onUpdate() {
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBoat && Mouse.isButtonDown(1)) {
            mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(mc.objectMouseOver.getBlockPos(), EnumFacing.SOUTH, EnumHand.MAIN_HAND, 1, 1, 1));
        }
    }
}
