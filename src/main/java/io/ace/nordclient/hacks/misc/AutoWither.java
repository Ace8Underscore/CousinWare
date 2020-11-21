package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AutoWither extends Hack {

    Vec3d startPos;
    int soulSandItem;
    int witherSkullItem;
    int blockPlaced = 0;
    int skullPlaced = 0;
    int delay = 0;
    boolean north;
    boolean south;
    boolean east;
    boolean west;

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
        if (blockPlaced >= 4 && skullPlaced < 4) {
            placeSkull();
        }
        if (skullPlaced >= 3) {
            this.disable();
        }
    }

    public void placeSoulSand() {
        Vec3d[] soulSandPosNorthSouth = new Vec3d[] {
            new Vec3d(0, 0, 0),
            new Vec3d(0, 1, 0),
            new Vec3d(1, 1, 0),
            new Vec3d(-1, 1, 0),
        };
        Vec3d[] soulSandPosEastWest = new Vec3d[] {
                new Vec3d(0, 0, 0),
                new Vec3d(0, 1, 0),
                new Vec3d(0, 1, 1),
                new Vec3d(0, 1, -1),
        };
        if (soulSandItem == -1) {
            Command.sendClientSideMessage("No SoulSand Toggling");
            this.toggle();
        } else {
            mc.player.inventory.currentItem = soulSandItem;
        }
        for (int i = 0; i < 4; i++) {
            BlockPos start = null;
            if (north || south) {
                start = new BlockPos(startPos.add(soulSandPosNorthSouth[i]));
            }
            if (east || west) {
                start = new BlockPos(startPos.add(soulSandPosEastWest[i]));
            }
            if (start == null)
                return;
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
        Vec3d[] witherSkullPosNorthSouth = new Vec3d[] {
                new Vec3d(-1, 2, 0),
                new Vec3d(0, 2, 0),
                new Vec3d(1, 2, 0),
        };
        Vec3d[] witherSkullPosEastWest = new Vec3d[] {
                new Vec3d(0, 2, -1),
                new Vec3d(0, 2, 0),
                new Vec3d(0, 2, 1),
        };
        if (witherSkullItem == -1) {
            Command.sendClientSideMessage("No WitherSkulls Toggling");
            this.toggle();
        } else {
            mc.player.inventory.currentItem = witherSkullItem;
        }
        for (int i = 0; i < 3; i++) {
            BlockPos start2 = null;
            if (north || south) {
                start2 = new BlockPos(startPos.add(witherSkullPosNorthSouth[i]));
            }
            if (east || west) {
                start2 = new BlockPos(startPos.add(witherSkullPosEastWest[i]));
            }
            if (start2 == null)
                return;
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
        int dir = MathHelper.floor((double) (Minecraft.getMinecraft().player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (dir == 2) {

            north = true;
        }

        if (dir == 1) {

            west = true;
        }
        if (dir == 3) {

            east = true;
        }
        if (dir == 0) {

            south = true;
        }
    }
    public void onDisable() {
        north = false;
        south = false;
        east = false;
        west = false;
    }
}
