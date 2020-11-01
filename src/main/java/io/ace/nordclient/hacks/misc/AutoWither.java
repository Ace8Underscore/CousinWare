package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AutoWither extends Hack {

    Vec3d startPos;
    int soulSandItem;
    int witherSkullItem;
    int blockPlaced = 0;
    int skullPlaced = 0;
    int delay = 0;

    Setting placeDelay;

    public AutoWither() {
        super("AutoWither", Category.MISC, 1);
        CousinWare.INSTANCE.settingsManager.rSetting(placeDelay = new Setting("PlaceDelay", this, 2, 0, 20, true, "AutoWitherPlaceDelay"));
    }

    public void onUpdate() {
        delay++;
        if (blockPlaced < 4) {
            placeSoulSand();
        }
        if (blockPlaced >= 4 && skullPlaced < 3) {
            placeSkull();
        }
        if (skullPlaced >= 2) {
            this.disable();
        }
    }

    public void placeSoulSand() {
        Vec3d[] soulSandPos = new Vec3d[] {
            new Vec3d(0, 0, 0),
            new Vec3d(0, 1, 0),
            new Vec3d(1, 1, 0),
            new Vec3d(-1, 1, 0),
        };
        if (soulSandItem == -1) {
            Command.sendClientSideMessage("No SoulSand Toggling");
            this.toggle();
        } else {
            mc.player.inventory.currentItem = soulSandItem;
        }
        for (int i = 0; i < 4; i++) {
            BlockPos start = new BlockPos(startPos.add(soulSandPos[i]));
            if (mc.world.getBlockState(start).getBlock().canPlaceBlockAt(mc.world, start)) {
                if (delay > placeDelay.getValInt()) {
                    BlockInteractionHelper.placeBlockScaffold(start);
                    blockPlaced++;
                    delay = 0;
                }
            }

        }
    }

    public void placeSkull() {
        Vec3d[] witherSkullPos = new Vec3d[] {
                new Vec3d(0, 2, 0),
                new Vec3d(1, 2, 0),
                new Vec3d(-1, 2, 0)
        };
        if (witherSkullItem == -1) {
            Command.sendClientSideMessage("No WitherSkulls Toggling");
            this.toggle();
        } else {
            mc.player.inventory.currentItem = witherSkullItem;
        }
        for (int i = 0; i < 4; i++) {
            BlockPos start2 = new BlockPos(startPos.add(witherSkullPos[i]));
            if (mc.world.getBlockState(start2).getBlock().canPlaceBlockAt(mc.world, start2)) {
                if (delay > placeDelay.getValInt()) {
                    BlockInteractionHelper.placeBlockScaffold(start2);
                    skullPlaced++;
                    delay = 0;
                }

            }
        }

    }

    public void onEnable() {
        startPos = mc.objectMouseOver.hitVec;
        soulSandItem = InventoryUtil.findBlockInHotbar(Blocks.SOUL_SAND);
        witherSkullItem = InventoryUtil.findItemInHotbar(Items.SKULL);
        blockPlaced = 0;
        skullPlaced = 0;
        delay = 0;
    }
}
