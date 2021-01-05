package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.HoleUtil;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class HoleFiller extends Hack {

    Setting range;
    Setting yRange;
    Setting sDelay;
    Setting rotate;
    Setting toggleTicks;

    int delay = 0;
    int delayT = 0;
    int startingHand;
    int obiHand;

    public HoleFiller() {
        super("HoleFiller", Category.COMBAT, 16363613);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 5, 1, 8, false, "HoleFillerRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(yRange = new Setting("Y-Range", this, 3, 1, 5, true, "HoleFillerY-Range"));
        CousinWare.INSTANCE.settingsManager.rSetting(sDelay = new Setting("Delay", this, 1, 0, 20, true, "HoleFillerDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, true, "HoleFillerRotate"));

        CousinWare.INSTANCE.settingsManager.rSetting(toggleTicks = new Setting("ToggleTicks", this, 10, 0, 60, true, "HoleFilleToggleTicks"));

    }

    public void onUpdate() {
        delayT++;
        delay++;
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;
        BlockPos playerPos = new BlockPos(x, y, z);
        List<BlockPos> blocks = (BlockInteractionHelper.getSphere(playerPos, (float) range.getValDouble(), (int) yRange.getValDouble(), false, true, 0));
        if (delay >= sDelay.getValInt()) {
            for (BlockPos block : blocks) {
                if (HoleUtil.isHole(block)) {
                    if (!block.equals(playerPos)) {
                        mc.player.inventory.currentItem = obiHand;
                        if (rotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffold(block);
                        else BlockInteractionHelper.placeBlockScaffoldNoRotate(block);
                        mc.player.inventory.currentItem = startingHand;
                    }
                }
            }
        }
        if (delayT > toggleTicks.getValInt()) {
            this.disable();
        }

    }

    public void onEnable() {
        startingHand = mc.player.inventory.currentItem;

        if (InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN) == -1) {
            Command.sendClientSideMessage("No Obi");
            this.disable();
        } else {
            obiHand = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
        }
    }
}
