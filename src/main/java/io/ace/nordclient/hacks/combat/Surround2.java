package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class Surround2 extends Hack {

    Setting noGhostBlocks;
    Setting placeDelay;

    ArrayList<BlockPos> placePos;
    int delay = 0;
    int block = 1;

    public Surround2() {
        super("Surround2b", Category.COMBAT,14379045);
        CousinWare.INSTANCE.settingsManager.rSetting(noGhostBlocks = new Setting("NoGhostBlocks", this, true, "Surround2bNoGhostBlocks"));
        CousinWare.INSTANCE.settingsManager.rSetting(placeDelay = new Setting("PlaceDelay", this, 2, 0, 20, true, "Surround2bPlaceDelay"));

    }



    private Vec3d[] placeLocation = new Vec3d[] {new Vec3d(1, 0, 0), new Vec3d(0, 0, 1), new Vec3d(-1, 0 , 0), new Vec3d(0, 0, -1), new Vec3d(1, -1, 0), new Vec3d(0, -1, 1), new Vec3d(-1, -1, 0), new Vec3d(0, -1, -1) };

    @Override
    public void onUpdate() {
        delay++;
        if (!mc.player.onGround) this.disable();
        int obiSlot = -1;
        int startingSlot = mc.player.inventory.currentItem;
        if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) this.disable();
        else obiSlot = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
        if (delay >= placeDelay.getValInt()) {
            block++;
            if (block >= 8) block = 0;
            BlockPos placePos = new BlockPos(mc.player.getPositionVector().add(placeLocation[block]).x, mc.player.getPositionVector().add(placeLocation[block]).y, mc.player.getPositionVector().add(placeLocation[block]).z);
                if (mc.world.mayPlace(Blocks.OBSIDIAN, placePos, false, EnumFacing.UP, mc.player)) {
                        if (obiSlot != -1) mc.player.inventory.currentItem = obiSlot;
                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        BlockInteractionHelper.placeBlockScaffold(placePos);
                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        mc.player.inventory.currentItem = startingSlot;
                        if (noGhostBlocks.getValBoolean()) {
                            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, placePos, EnumFacing.SOUTH));
                            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, placePos, EnumFacing.SOUTH));
                        }
                    delay = 0;
                    }
                }

            }



    @Override
    public void onEnable() {
        if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) this.disable();
        delay = 0;
        block = 0;

    }
}
