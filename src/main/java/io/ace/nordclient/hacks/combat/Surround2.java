package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Surround2 extends Hack {

    Setting noGhostBlocks;

    public Surround2() {
        super("Surround2", Category.COMBAT,14379045);
        CousinWare.INSTANCE.settingsManager.rSetting(noGhostBlocks = new Setting("NoGhostBlocks", this, true, "Surround2NoGhostBlocks"));
    }



    private Vec3d[] placeLocation = new Vec3d[] {new Vec3d(1, 0, 0), new Vec3d(0, 0, 1), new Vec3d(-1, 0 , 0), new Vec3d(0, 0, -1), new Vec3d(1, -1, 0), new Vec3d(0, -1, 1), new Vec3d(-1, -1, 0), new Vec3d(0, -1, -1) };

    public void onUpdate() {
        int obiSlot = -1;
        int startingSlot = mc.player.inventory.currentItem;
        if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) this.disable();
        else obiSlot = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
        for (int i = 0; i < placeLocation.length; i++) {
            BlockPos placePos = new BlockPos(mc.player.getPositionVector().add(placeLocation[i]).x, mc.player.getPositionVector().add(placeLocation[i]).y, mc.player.getPositionVector().add(placeLocation[i]).z);
            if (mc.world.mayPlace(Blocks.OBSIDIAN, placePos, false, EnumFacing.UP, mc.player)) {
                if (obiSlot != -1) mc.player.inventory.currentItem = obiSlot;
                BlockInteractionHelper.placeBlockScaffold(placePos);
                mc.player.inventory.currentItem = startingSlot;
                if (noGhostBlocks.getValBoolean()) {
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, placePos, EnumFacing.SOUTH));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, placePos, EnumFacing.SOUTH));
                }

            }
        }
    }
}
