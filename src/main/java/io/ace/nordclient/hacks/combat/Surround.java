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

public class Surround extends Hack {

    Setting noGhostBlocks;
    Setting placeDelay;

    int delay = 0;
    int startingSlot;
    int obiSlot;

    public Surround() {
        super("Surround", Category.COMBAT, 14379045);
        CousinWare.INSTANCE.settingsManager.rSetting(noGhostBlocks = new Setting("NoGhostBlocks", this, true, "SurroundNoGhostBlocks"));
        CousinWare.INSTANCE.settingsManager.rSetting(placeDelay = new Setting("PlaceDelay", this, 2, 0, 20, true, "SurroundPlaceDelay"));

    }


    private Vec3d[] placeLocation = new Vec3d[]{new Vec3d(1, 0, 0), new Vec3d(0, 0, 1), new Vec3d(-1, 0, 0), new Vec3d(0, 0, -1), new Vec3d(1, -1, 0), new Vec3d(0, -1, 1), new Vec3d(-1, -1, 0), new Vec3d(0, -1, -1)};

    public void onUpdate() {
        delay++;
        //if (!mc.player.onGround) this.disable();
        for (Vec3d vec3d : placeLocation) {
            if (delay >= placeDelay.getValInt()) {
                BlockPos pos = new BlockPos(mc.player.getPositionVector().add(vec3d));
                if (mc.world.mayPlace(Blocks.OBSIDIAN, pos, false, EnumFacing.UP, mc.player)) {
                    mc.player.inventory.currentItem = obiSlot;
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    BlockInteractionHelper.placeBlockScaffold(pos);
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    mc.player.inventory.currentItem = startingSlot;
                    if (noGhostBlocks.getValBoolean()) {
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.SOUTH));
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, pos, EnumFacing.SOUTH));
                    }

                }
                delay = 0;
            }
        }

    }

    public void onEnable() {
         obiSlot = -1;
         startingSlot = mc.player.inventory.currentItem;
        if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) this.disable();
        else obiSlot = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
    }
}