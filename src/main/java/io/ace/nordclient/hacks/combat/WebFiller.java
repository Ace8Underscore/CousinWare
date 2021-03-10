package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.mixin.accessor.IEntity;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.HoleUtil;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.ArrayList;
import java.util.List;

public class WebFiller extends Hack {

    Setting range;
    Setting yRange;
    Setting sDelay;
    Setting placeMode;
    Setting webMode;
    Setting toggleTicks;
    Setting noGhostBlocks;

    int webSlot = -1;
    int startingSlot = 0;
    int delay = 0;
    int tDelay = 0;

    public WebFiller() {
        super("WebFiller", Category.COMBAT, 1616480);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 5, 1, 8, false, "WebFillerRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(yRange = new Setting("Y-Range", this, 3, 1, 5, true, "WebFillerY-Range"));
        CousinWare.INSTANCE.settingsManager.rSetting(sDelay = new Setting("Delay", this, 1, 0, 20, true, "WebFillerDelay"));
        ArrayList<String> placeModes = new ArrayList<>();
        placeModes.add("NoRotate");
        placeModes.add("Rotate");
        placeModes.add("Strict");
        placeModes.add("StrictBeta");
        CousinWare.INSTANCE.settingsManager.rSetting(placeMode = new Setting("PlaceModes", this, "Strict", placeModes, "WebFillerPlaceModes"));

        ArrayList<String> webModes = new ArrayList<>();
        webModes.add("HoleFill");
        webModes.add("Self");
        webModes.add("Enemy");
        webModes.add("Slackin");
        CousinWare.INSTANCE.settingsManager.rSetting(webMode = new Setting("WebModes", this, "HoleFill", webModes, "WebFillerModes"));
        CousinWare.INSTANCE.settingsManager.rSetting(toggleTicks = new Setting("ToggleTicks", this, 10, 0, 60, true, "WebFilleToggleTicks"));
        CousinWare.INSTANCE.settingsManager.rSetting(noGhostBlocks = new Setting("NoGhostBlocks", this, true, "WebFillerNoGhostBlocks"));

    }

    @Override
    public void onUpdate() {
        if (InventoryUtil.findBlockInHotbar(Blocks.WEB) == -1) {
            Command.sendClientSideMessage("No Webs");
            this.disable();
        }
        delay++;
        tDelay++;
        double x = mc.player.posX;
        double y = mc.player.posY;
        double z = mc.player.posZ;
        BlockPos playerPos = new BlockPos(x, y, z);
        List<BlockPos> blocks = (BlockInteractionHelper.getSphere(playerPos, (float) range.getValDouble(), (int) yRange.getValDouble(), false, true, 0));
        //mc.player.inventory.currentItem = webSlot;
        if (webMode.getValString().equalsIgnoreCase("HoleFill")) {
            if (delay >= sDelay.getValInt()) {
            for (BlockPos block : blocks) {
                if (HoleUtil.isHole(block)) {
                        if (placeMode.getValString().equalsIgnoreCase("rotate")) {
                            mc.player.inventory.currentItem = webSlot;
                            BlockInteractionHelper.placeBlockScaffold(block);
                            mc.player.inventory.currentItem = startingSlot;
                            delay = 0;
                        }
                        if (placeMode.getValString().equalsIgnoreCase("norotate")) {
                            mc.player.inventory.currentItem = webSlot;
                            BlockInteractionHelper.placeBlockScaffoldNoRotate(block);
                            mc.player.inventory.currentItem = startingSlot;
                            delay = 0;
                        }
                        if (placeMode.getValString().equalsIgnoreCase("strict")) {
                                mc.player.inventory.currentItem = webSlot;
                                BlockInteractionHelper.placeBlockScaffoldStrict(block);
                                mc.player.inventory.currentItem = startingSlot;
                                delay = 0;
                        }
                    if (placeMode.getValString().equalsIgnoreCase("raytrace")) {
                        mc.player.inventory.currentItem = webSlot;
                        BlockInteractionHelper.placeBlockScaffoldStrictRaytrace(block);
                        mc.player.inventory.currentItem = startingSlot;
                        delay = 0;
                    }
                        if (noGhostBlocks.getValBoolean()) {
                            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, block, EnumFacing.SOUTH));
                            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, block, EnumFacing.SOUTH));

                        }
                    }

                }
            }
        }
        if (webMode.getValString().equalsIgnoreCase("Self")) {
            if (delay >= sDelay.getValInt()) {
                if (!((IEntity) mc.player).getIsInWeb()) {
                    if (placeMode.getValString().equalsIgnoreCase("rotate") || placeMode.getValString().equalsIgnoreCase("strict") || placeMode.getValString().equalsIgnoreCase("strictbeta")) {
                        mc.player.inventory.currentItem = webSlot;
                        BlockInteractionHelper.placeBlockScaffold(playerPos);
                        mc.player.inventory.currentItem = startingSlot;
                    }
                    if (placeMode.getValString().equalsIgnoreCase("norotate")) {
                        mc.player.inventory.currentItem = webSlot;
                        BlockInteractionHelper.placeBlockScaffoldNoRotate(playerPos);
                        mc.player.inventory.currentItem = startingSlot;
                    }
                    delay = 0;
                }
            }
        }
        if (webMode.getValString().equalsIgnoreCase("Enemy")) {
            for (Entity e : mc.world.playerEntities) {
                if (!FriendManager.isFriend(e.getName())) {
                    if (!e.getName().equalsIgnoreCase(mc.player.getName())) {
                        if (delay >= sDelay.getValInt()) {
                            BlockPos enemyPos = new BlockPos(e.posX, e.posY, e.posZ);
                            if (!mc.world.getBlockState(enemyPos).getBlock().equals(Blocks.WEB)) {
                                if (placeMode.getValString().equalsIgnoreCase("rotate") || placeMode.getValString().equalsIgnoreCase("strict") || placeMode.getValString().equalsIgnoreCase("strictbeta")) {
                                    mc.player.inventory.currentItem = webSlot;
                                    BlockInteractionHelper.placeBlockScaffold(enemyPos);
                                    mc.player.inventory.currentItem = startingSlot;
                                }
                                if (placeMode.getValString().equalsIgnoreCase("norotate")) {
                                    mc.player.inventory.currentItem = webSlot;
                                    BlockInteractionHelper.placeBlockScaffoldNoRotate(enemyPos);
                                    mc.player.inventory.currentItem = startingSlot;
                                }
                                }
                                delay = 0;
                            }
                        }
                    }
                }
            }
        if (webMode.getValString().equalsIgnoreCase("Slackin")) {
            BlockPos place = null;
            if (delay >= sDelay.getValInt()) {
                for (Entity e : mc.world.playerEntities) {
                    if (!FriendManager.isFriend(e.getName())) {
                        if (!e.getName().equalsIgnoreCase(mc.player.getName())) {
                            BlockPos enemyPos = new BlockPos(e.posX, e.posY, e.posZ);
                            for (BlockPos block : blocks) {
                                if (HoleUtil.isHole(block)) {
                                    if (HoleUtil.isHole(enemyPos) || HoleUtil.isHole(enemyPos.down()) ) {
                                        if (HoleUtil.isHole(enemyPos)) {
                                            place = new BlockPos(enemyPos.getX(), enemyPos.getY(), enemyPos.getZ());
                                        } else {
                                            place = new BlockPos(enemyPos.getX(), enemyPos.down().getY(), enemyPos.getZ());

                                        }
                                        if (block.getDistance((int) e.posX, (int) e.posY, (int) e.posZ) < 3 && e.posY > block.getY() + .3) {
                                            if (placeMode.getValString().equalsIgnoreCase("rotate")) {
                                                mc.player.inventory.currentItem = webSlot;
                                                BlockInteractionHelper.placeBlockScaffold(place);
                                                mc.player.inventory.currentItem = startingSlot;
                                                delay = 0;
                                            }
                                            if (placeMode.getValString().equalsIgnoreCase("norotate")) {
                                                mc.player.inventory.currentItem = webSlot;
                                                BlockInteractionHelper.placeBlockScaffoldNoRotate(place);
                                                mc.player.inventory.currentItem = startingSlot;
                                                delay = 0;
                                            }
                                            if (placeMode.getValString().equalsIgnoreCase("strict")) {
                                                mc.player.inventory.currentItem = webSlot;
                                                BlockInteractionHelper.placeBlockScaffoldStrict(place);
                                                mc.player.inventory.currentItem = startingSlot;
                                                delay = 0;
                                            }
                                            if (placeMode.getValString().equalsIgnoreCase("strictbeta")) {
                                                mc.player.inventory.currentItem = webSlot;
                                                BlockInteractionHelper.placeBlockScaffoldStrictRaytrace(place);
                                                mc.player.inventory.currentItem = startingSlot;
                                                delay = 0;
                                            }
                                            if (noGhostBlocks.getValBoolean()) {
                                                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, place, EnumFacing.SOUTH));
                                                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, place, EnumFacing.SOUTH));

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        if (tDelay >= toggleTicks.getValInt() && toggleTicks.getValInt() != 0) {
            this.disable();
        }
    }


    @Override
    public void onEnable() {
        startingSlot = mc.player.inventory.currentItem;
        if (InventoryUtil.findBlockInHotbar(Blocks.WEB) == -1) {
            Command.sendClientSideMessage("No Webs");
            this.disable();
        } else {
            webSlot = InventoryUtil.findBlockInHotbar(Blocks.WEB);
        }
    }

    @Override
    public void onDisable() {
        mc.player.inventory.currentItem = startingSlot;
        tDelay = 0;
//
    }
    @SubscribeEvent
    public void onPlayerLeaveEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.disable();
    }
}

/*
if (webMode.getValString().equalsIgnoreCase("Slackin")) {
            if (delay >= sDelay.getValInt()) {
                for (Entity e : mc.world.playerEntities) {
                    if (!FriendManager.isFriend(e.getName())) {
                        if (!e.getName().equalsIgnoreCase(mc.player.getName())) {
                            BlockPos enemyPos = new BlockPos(e.posX, e.posY, e.posZ);
                            if (HoleUtil.isHole(enemyPos) || HoleUtil.isHole(enemyPos.down())) {
                                if (placeMode.getValString().equalsIgnoreCase("rotate")) {
                                    mc.player.inventory.currentItem = webSlot;
                                    BlockInteractionHelper.placeBlockScaffold(enemyPos);
                                    mc.player.inventory.currentItem = startingSlot;
                                    delay = 0;
                                }
                                if (placeMode.getValString().equalsIgnoreCase("norotate")) {
                                    mc.player.inventory.currentItem = webSlot;
                                    BlockInteractionHelper.placeBlockScaffoldNoRotate(enemyPos);
                                    mc.player.inventory.currentItem = startingSlot;
                                    delay = 0;
                                }
                                if (placeMode.getValString().equalsIgnoreCase("strict")) {
                                    mc.player.inventory.currentItem = webSlot;
                                    BlockInteractionHelper.placeBlockScaffoldStrict(enemyPos);
                                    mc.player.inventory.currentItem = startingSlot;
                                    delay = 0;
                                }
                                if (noGhostBlocks.getValBoolean()) {
                                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, enemyPos, EnumFacing.SOUTH));
                                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, enemyPos, EnumFacing.SOUTH));

                                }
                            }
                        }
                    }
                }
            }

        }
 */
