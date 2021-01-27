package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.*;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

/**
 * @author Ace________/Ace_#1233
 */

public class NoInteract extends Hack {

    public NoInteract() {
        super("NoInteract", Category.MISC, 16280575);
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (mc.world == null || mc.player == null)
            return;
        if (mc.player.getHeldItemMainhand().getItem().equals(Items.GOLDEN_APPLE) || mc.player.getHeldItemOffhand().getItem().equals(Items.GOLDEN_APPLE)) {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                for (TileEntity entity : mc.world.loadedTileEntityList) {
                    if (entity instanceof TileEntityEnderChest || entity instanceof TileEntityBeacon || entity instanceof TileEntityFurnace || entity instanceof TileEntityHopper || entity instanceof TileEntityChest)
                        if (mc.objectMouseOver.getBlockPos().equals(entity.getPos())) {
                            if (Mouse.isButtonDown(1)) {
                                event.setCanceled(true);
                                mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));


                            }
                        }
                    //
                }
                if (mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock().equals(Blocks.ANVIL)) {
                    if (Mouse.isButtonDown(1)) {
                        event.setCanceled(true);
                        mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));


                    }
                }

            }
        }


    }
}
