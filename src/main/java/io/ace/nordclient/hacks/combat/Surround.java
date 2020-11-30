package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * @author Ace________/Ace_#1233
 */

public class Surround extends Hack {
    Float yaw;

    boolean side1basic;
    boolean side2basic;
    boolean side3basic;
    boolean side4basic;
    boolean side1basicS;
    boolean side2basicS;
    boolean side3basicS;
    boolean side4basicS;
    int delay = 0;
    int delay2 = 0;

    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;

    Setting renderPlace;
    Setting r;
    Setting g;
    Setting b;
    Setting a;


    public Surround() {
        super("Surround", Category.COMBAT, 2497873);

        CousinWare.INSTANCE.settingsManager.rSetting(renderPlace = new Setting("RenderPlace", this, true, "SurroundRenderPlace"));

        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 1, 0, 255, false, "SurroundRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 1, 0, 255, false, "SurroundGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 1, 0, 255, false, "SurroundBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(a = new Setting("alpha", this, 1, 0, 1, false, "SurroundAlpha"));

    }

    @Override
    public void onUpdate() {
        int blockSlot = this.findObiInHotbar();
        if (blockSlot > 1) {
            delay++;
            BlockPos side1 = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ + 1);
            BlockPos side2 = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ - 1);
            BlockPos side3 = new BlockPos(mc.player.posX + 1, mc.player.posY, mc.player.posZ);
            BlockPos side4 = new BlockPos(mc.player.posX - 1, mc.player.posY, mc.player.posZ);
            BlockPos side1Sneak = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ + 1);
            BlockPos side2Sneak = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ - 1);
            BlockPos side3Sneak = new BlockPos(mc.player.posX + 1, mc.player.posY - 1, mc.player.posZ);
            BlockPos side4Sneak = new BlockPos(mc.player.posX - 1, mc.player.posY - 1, mc.player.posZ);

            if (mc.world.getBlockState(side1).getBlock().canPlaceBlockAt(mc.world, side1)) {
                side1basic = true;
            } else {
                side1basic = false;
            }
            if (mc.world.getBlockState(side2).getBlock().canPlaceBlockAt(mc.world, side2)) {
                side2basic = true;
            } else {
                side2basic = false;
            }
            if (mc.world.getBlockState(side3).getBlock().canPlaceBlockAt(mc.world, side3)) {
                side3basic = true;
            } else {
                side3basic = false;
            }
            if (mc.world.getBlockState(side4).getBlock().canPlaceBlockAt(mc.world, side4)) {
                side4basic = true;
            } else {
                side4basic = false;
            }

            if (mc.world.getBlockState(side1Sneak).getBlock().canPlaceBlockAt(mc.world, side1Sneak) && side1basic) {
                side1basicS = true;
            } else {
                side1basicS = false;
            }
            if (mc.world.getBlockState(side2Sneak).getBlock().canPlaceBlockAt(mc.world, side2Sneak) && side2basic) {
                side2basicS = true;
            } else {
                side2basicS = false;
            }
            if (mc.world.getBlockState(side3Sneak).getBlock().canPlaceBlockAt(mc.world, side3Sneak) && side3basic) {
                side3basicS = true;
            } else {
                side3basicS = false;
            }
            if (mc.world.getBlockState(side4Sneak).getBlock().canPlaceBlockAt(mc.world, side4Sneak) && side4basic) {
                side4basicS = true;
            } else {
                side4basicS = false;
            }

       /* if (mc.world.getBlockState(side2).getBlock().canPlaceBlockAt(mc.world, side2)) {
            if (mc.world.getBlockState(side2).getBlock() == BlockInteractionHelper.blackList || mc.world.getBlockState(side2).getBlock() == BlockInteractionHelper.shulkerList) {
                //mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                BlockInteractionHelper.placeBlockScaffold(side2);
               // mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }

        } */


            //place blocks
            if (delay > 2) {
                if (side1basicS) {
                    BlockInteractionHelper.placeBlockScaffold(side1Sneak);
                    if (renderPlace.getValBoolean()) {
                        NordTessellator.drawBoundingBoxBlockPos(side1Sneak, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                    }
                    delay = 0;
                }

                if (side2basicS) {
                    BlockInteractionHelper.placeBlockScaffold(side2Sneak);
                    if (renderPlace.getValBoolean()) {
                        NordTessellator.drawBoundingBoxBlockPos(side2Sneak, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                    }
                    delay = 0;
                }
                if (side3basicS) {
                    BlockInteractionHelper.placeBlockScaffold(side3Sneak);
                    if (renderPlace.getValBoolean()) {
                        NordTessellator.drawBoundingBoxBlockPos(side3Sneak, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                    }
                    delay = 0;
                }
                if (side4basicS) {
                    BlockInteractionHelper.placeBlockScaffold(side4Sneak);
                    if (renderPlace.getValBoolean()) {
                        NordTessellator.drawBoundingBoxBlockPos(side4Sneak, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                    }
                    delay = 0;
                }
            }

            if (delay > 2) {
                if (side1basic) {
                    BlockInteractionHelper.placeBlockScaffold(side1);
                    if (renderPlace.getValBoolean()) {
                        NordTessellator.drawBoundingBoxBlockPos(side1, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                    }
                    delay = 0;
                }

                if (side2basic) {
                    BlockInteractionHelper.placeBlockScaffold(side2);
                    if (renderPlace.getValBoolean()) {
                        NordTessellator.drawBoundingBoxBlockPos(side2, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                    }
                    delay = 0;
                }
                if (side3basic) {
                    BlockInteractionHelper.placeBlockScaffold(side3);
                    if (renderPlace.getValBoolean()) {
                        NordTessellator.drawBoundingBoxBlockPos(side3, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                    }
                    delay = 0;
                }
                if (side4basic) {
                    BlockInteractionHelper.placeBlockScaffold(side4);
                    if (renderPlace.getValBoolean()) {
                        NordTessellator.drawBoundingBoxBlockPos(side4, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                    }
                    delay = 0;
                }
            }


            if (!mc.player.onGround) {
                this.toggle();
            }
            if (mc.currentScreen instanceof GuiContainer) {
                this.toggle();
            }
            int pearlSlot = this.findObiInHotbar();
            if (this.lastHotbarSlot != pearlSlot) {
                mc.player.inventory.currentItem = pearlSlot;
                this.lastHotbarSlot = pearlSlot;
            }
            if (!side1basic && !side1basicS && !side2basic && !side2basicS && !side3basic && !side3basicS && !side4basic && !side4basicS) {
                delay2++;

                if (delay2 == 1) {
                    //this.playerHotbarSlot = mc.player.inventory.currentItem;
                    mc.player.inventory.currentItem = this.playerHotbarSlot;

                }
            }
            if (side1basic || side1basicS || side2basic || side2basicS || side3basic || side3basicS || side4basic || side4basicS) {
                delay2 = 0;
                for (int i = 0; i < 9; i++) {

                    // filter out non-block items
                    ItemStack stack = mc.player.inventory.getStackInSlot(i);
                    Block block = ((ItemBlock) stack.getItem()).getBlock();
                    if (block instanceof BlockObsidian) {
                        mc.player.inventory.currentItem = pearlSlot;
                    }
                }


            }
        }
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        BlockPos side1 = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ + 1);
        BlockPos side2 = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ - 1);
        BlockPos side3 = new BlockPos(mc.player.posX + 1, mc.player.posY, mc.player.posZ);
        BlockPos side4 = new BlockPos(mc.player.posX - 1, mc.player.posY, mc.player.posZ);
        BlockPos side1Sneak = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ + 1);
        BlockPos side2Sneak = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ - 1);
        BlockPos side3Sneak = new BlockPos(mc.player.posX + 1, mc.player.posY - 1, mc.player.posZ);
        BlockPos side4Sneak = new BlockPos(mc.player.posX - 1, mc.player.posY - 1, mc.player.posZ);


            if (side1basicS) {
                if (renderPlace.getValBoolean()) {
                    NordTessellator.drawBoundingBoxBlockPos(side1Sneak, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                }
            }

            if (side2basicS) {
                if (renderPlace.getValBoolean()) {
                    NordTessellator.drawBoundingBoxBlockPos(side2Sneak, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                }
            }
            if (side3basicS) {
                if (renderPlace.getValBoolean()) {
                    NordTessellator.drawBoundingBoxBlockPos(side3Sneak, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                }
            }
            if (side4basicS) {
                if (renderPlace.getValBoolean()) {
                    NordTessellator.drawBoundingBoxBlockPos(side4Sneak, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                }

        }


            if (side1basic) {
                if (renderPlace.getValBoolean()) {
                    NordTessellator.drawBoundingBoxBlockPos(side1, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                }
            }

            if (side2basic) {
                if (renderPlace.getValBoolean()) {
                    NordTessellator.drawBoundingBoxBlockPos(side2, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                }
            }
            if (side3basic) {
                if (renderPlace.getValBoolean()) {
                    NordTessellator.drawBoundingBoxBlockPos(side3, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                }
            }
            if (side4basic) {
                if (renderPlace.getValBoolean()) {
                    NordTessellator.drawBoundingBoxBlockPos(side4, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                }
            }
        }


    public void onEnable() {
        this.playerHotbarSlot = mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
    }

    public void onDisable() {
        int finalfainl = mc.player.inventory.currentItem;
        this.playerHotbarSlot = finalfainl;
        delay2 = 0;
        //this.playerHotbarSlot = -1;
        //this.lastHotbarSlot = -1;
        delay = 0;

    }

    private int findObiInHotbar() {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockObsidian) {
                slot = i;
                break;
            }

        }

        return slot;

    }
}


