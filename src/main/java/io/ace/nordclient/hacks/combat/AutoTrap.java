package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.command.commands.Set;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.BlockUtil;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class AutoTrap extends Hack {

    int delay = 0;
    int delayToggle = 0;
    int startingHand;
    Setting placeRange;
    Setting placeDelay;
    Setting toggleTicks;

    public AutoTrap() {
        super("AutoTrap", Category.COMBAT, 1);
        CousinWare.INSTANCE.settingsManager.rSetting(placeRange = new Setting("PlaceRange", this, 5.5, 0, 8, false, "AutoTrapPlaceRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(placeDelay = new Setting("PlaceDelay", this, 2, 0, 20, true, "AutoTrapPlaceDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(toggleTicks = new Setting("ToggleTicks", this, 8, 0, 20, true, "AutoTrapToggleTicks"));

    }

    public void onUpdate() {
        delay++;
        delayToggle++;
        Vec3d[] place = new Vec3d[]{
                new Vec3d(1, -1, 0),
                new Vec3d(1, 0, 0),
                new Vec3d(-1, -1, 0),
                new Vec3d(-1, 0, 0),
                new Vec3d(0, -1, 1),
                new Vec3d(0, 0, 1),
                new Vec3d(0, -1, -1),
                new Vec3d(0, 0, -1),
                new Vec3d(1, 1, 0),
                new Vec3d(-1, 1, 0),
                new Vec3d(0, 1, 1),
                new Vec3d(0, 1, -1),
                new Vec3d(1, 2, 0),
                new Vec3d(0, 2, 0)
        };
        for (int i = 0; i < 14; i += 1) {
            for (Entity e : mc.world.getLoadedEntityList()) {
                if (e instanceof EntityPlayer) {
                    if (mc.player.getDistance(e) <= placeRange.getValDouble() && !e.getName().equalsIgnoreCase(mc.player.getName()) && !FriendManager.isFriend(e.getName())) {
                        if (delay >= placeDelay.getValInt()) {
                            BlockPos pos = new BlockPos(e.getPositionVector().add(place[i]));
                            if (mc.world.getBlockState(pos).getBlock().canPlaceBlockAt(mc.world, pos)) {
                                BlockInteractionHelper.placeBlockScaffold(pos);
                                delay = 0;

                            }
                        }
                    }
                }
            }
        }
        if (delayToggle >= toggleTicks.getValInt()) {
            this.toggle();
        }
    }
    public void onEnable() {
        startingHand = mc.player.inventory.currentItem;
        delayToggle = 0;
        delay = 0;
        int obsidianHand = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
        if (obsidianHand == -1) {
            Command.sendClientSideMessage("No Obsidian Toggling!");
        } else {
            mc.player.inventory.currentItem = obsidianHand;
        }
    }
    public void onDisable() {
        mc.player.inventory.currentItem = startingHand;

    }

}
