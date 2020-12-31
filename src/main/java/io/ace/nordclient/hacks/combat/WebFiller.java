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
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class WebFiller extends Hack {

    Setting range;
    Setting yRange;
    Setting sDelay;
    Setting rotate;
    Setting webMode;
    Setting toggleTicks;

    int webSlot = -1;
    int startingSlot = 0;
    int delay = 0;
    int tDelay = 0;

    public WebFiller() {
        super("WebFiller", Category.COMBAT, 1616480);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 5, 1, 8, false, "WebFillerRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(yRange = new Setting("Y-Range", this, 3, 1, 5, true, "WebFillerY-Range"));
        CousinWare.INSTANCE.settingsManager.rSetting(sDelay = new Setting("Delay", this, 1, 0, 20, true, "WebFillerDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(rotate = new Setting("Rotate", this, true, "WebFillerRotate"));
        ArrayList<String> webModes = new ArrayList<>();
        webModes.add("HoleFill");
        webModes.add("Self");
        webModes.add("Enemy");
        CousinWare.INSTANCE.settingsManager.rSetting(webMode = new Setting("WebModes", this, "HoleFill", webModes, "WebFillerModes"));
        CousinWare.INSTANCE.settingsManager.rSetting(toggleTicks = new Setting("ToggleTicks", this, 10, 0, 60, true, "WebFilleToggleTicks"));

    }

    public void onUpdate() {
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
                        if (rotate.getValBoolean()) {
                            mc.player.inventory.currentItem = webSlot;
                            BlockInteractionHelper.placeBlockScaffold(block);
                            mc.player.inventory.currentItem = startingSlot;
                            delay = 0;
                        } else {
                            mc.player.inventory.currentItem = webSlot;
                            BlockInteractionHelper.placeBlockScaffoldNoRotate(block);
                            mc.player.inventory.currentItem = startingSlot;
                            delay = 0;
                        }
                        //Command.sendClientSideMessage(String.valueOf(delay));
                    }

                }
            }
        }
        if (webMode.getValString().equalsIgnoreCase("Self")) {
            if (delay >= sDelay.getValInt()) {
                if (!((IEntity) mc.player).getIsInWeb()) {
                    if (rotate.getValBoolean()) {
                        mc.player.inventory.currentItem = webSlot;
                        BlockInteractionHelper.placeBlockScaffold(playerPos);
                        mc.player.inventory.currentItem = startingSlot;
                    } else {
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
                                if (rotate.getValBoolean()) {
                                    mc.player.inventory.currentItem = webSlot;
                                    BlockInteractionHelper.placeBlockScaffold(enemyPos);
                                    mc.player.inventory.currentItem = startingSlot;
                                } else {
                                    mc.player.inventory.currentItem = webSlot;
                                    BlockInteractionHelper.placeBlockScaffoldNoRotate(enemyPos);
                                    mc.player.inventory.currentItem = startingSlot;
                                }
                                delay = 0;
                            }
                        }
                    }
                }
            }
        }
        if (tDelay >= toggleTicks.getValInt()) {
            this.disable();
        }
    }

    public void onEnable() {
        startingSlot = mc.player.inventory.currentItem;
        if (InventoryUtil.findBlockInHotbar(Blocks.WEB) == -1) {
            Command.sendClientSideMessage("No Webs");
            this.disable();
        } else {
            webSlot = InventoryUtil.findBlockInHotbar(Blocks.WEB);
        }
    }

    public void onDisable() {
        mc.player.inventory.currentItem = startingSlot;
        tDelay = 0;
//
    }
}
