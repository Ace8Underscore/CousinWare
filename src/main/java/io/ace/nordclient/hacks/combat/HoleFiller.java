package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.HoleUtil;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class HoleFiller extends Hack {

    Setting range;
    Setting yRange;
    Setting sDelay;
    Setting placeMode;
    Setting toggleTicks;
    Setting noGhostBlocks;
    Setting skipFail;

    int delay = 0;
    int delayT = 0;
    int startingHand;
    int obiHand;

    public HoleFiller() {
        super("HoleFiller", Category.COMBAT, 16363613);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 5, 1, 8, false, "HoleFillerRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(yRange = new Setting("Y-Range", this, 3, 1, 5, true, "HoleFillerY-Range"));
        CousinWare.INSTANCE.settingsManager.rSetting(sDelay = new Setting("Delay", this, 1, 0, 20, true, "HoleFillerDelay"));
        ArrayList<String> placeModes = new ArrayList<>();
        placeModes.add("NoRotate");
        placeModes.add("Rotate");
        placeModes.add("Strict");
        placeModes.add("raytrace");
        CousinWare.INSTANCE.settingsManager.rSetting(placeMode = new Setting("PlaceModes", this, "Strict", placeModes, "HoleFillerPlaceModes"));
        CousinWare.INSTANCE.settingsManager.rSetting(toggleTicks = new Setting("ToggleTicks", this, 10, 0, 60, true, "HoleFilleToggleTicks"));
        CousinWare.INSTANCE.settingsManager.rSetting(noGhostBlocks = new Setting("NoGhostBlocks", this, true, "HoleFillerNoGhostBlocks"));
        CousinWare.INSTANCE.settingsManager.rSetting(skipFail = new Setting("SkipFail", this, false, "HoleFillerSkipFail"));

    }

    ArrayList<String> placeBlocks = new ArrayList<>();

    @Override
    public void onUpdate() {
        delayT++;
        delay++;
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;
        BlockPos playerPos = new BlockPos(x, y, z);
        List<BlockPos> blocks = (BlockInteractionHelper.getSphere(playerPos, (float) range.getValDouble(), (int) yRange.getValDouble(), false, true, 0));
            for (BlockPos block : blocks) {
                if (skipFail.getValBoolean()) {
                    if (!placeBlocks.contains(block))
                        continue;
                }

                 if (HoleUtil.isHole(block)) {
                    if (!block.equals(playerPos)) {
                        if (delay >= sDelay.getValInt()) {
                            mc.player.inventory.currentItem = obiHand;
                        if (placeMode.getValString().equalsIgnoreCase("raytrace")) {
                            BlockInteractionHelper.placeBlockScaffoldStrictRaytrace(block);

                        }
                        if (placeMode.getValString().equalsIgnoreCase("strict")) BlockInteractionHelper.placeBlockScaffoldStrict(block);
                        if (placeMode.getValString().equalsIgnoreCase("rotate")) BlockInteractionHelper.placeBlockScaffold(block);
                        if (placeMode.getValString().equalsIgnoreCase("norotate")) BlockInteractionHelper.placeBlockScaffoldNoRotate(block);

                        mc.player.inventory.currentItem = startingHand;
                        if (noGhostBlocks.getValBoolean()) {
                            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, block, EnumFacing.SOUTH));
                            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, block, EnumFacing.SOUTH));
                            delay = 0;
                        }
                        if (skipFail.getValBoolean()) placeBlocks.add(String.valueOf(block));
                    }
                }
            }

        }
        if (delayT > toggleTicks.getValInt()) {
            this.disable();
        }

    }

    @Override
    public void onEnable() {
        placeBlocks.clear();
        startingHand = mc.player.inventory.currentItem;
        delayT = 0;

        if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) {
            Command.sendClientSideMessage("No Obi");
            this.disable();
        } else {
            obiHand = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
        }
    }
}


/*
@Author Ace________
New Placement Ideas
You must find the blocks around where you want to place right now most placements do this which is ok but not good for strict ncp servers ie 2b2t
look at middle of blockpos
place block on block below desired blockpos with ENUM.FACINGUP
this would work perfect if you could see the block below the place location but you prob cant so you must do this
get all the valid placing locations for the block

ie
place at blockpos.west();
then place the oposite of west so east
this is setting the block you want to do enumfacing on then do the opposite because thats the side facing where you want to place
soo...
blockpos.west :: EnumFacing.EAST
blockpos.east :: EnumFacing.WEST
blockpos.north :: EnumFacing.SOUTH
blockpos.south :: EnumFacing.north;






 */
