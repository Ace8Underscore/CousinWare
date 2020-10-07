package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class NoInteract extends Hack {

    public NoInteract() {
        super("NoInteract", Category.COMBAT);
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (mc.player.getHeldItemMainhand().getItem().equals(Items.GOLDEN_APPLE) || mc.player.getHeldItemOffhand().getItem().equals(Items.GOLDEN_APPLE)) {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                event.setCanceled(true);
                //mc.player.setSneaking(true);
                mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));

            }

        }


    }

}
