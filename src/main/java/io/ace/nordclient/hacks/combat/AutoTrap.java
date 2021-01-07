package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.mixin.accessor.ICPacketPlayer;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.List;

public class AutoTrap extends Hack {

    boolean north, south, east, west;

    int delay = 0;
    int delayToggle = 0;
    int startingHand;
    int obsidianSlot = 0;
    Setting placeRange;
    Setting placeDelay;
    Setting toggleTicks;
    Setting noGhostBlocks;


    boolean packetsBeingSent;

    private EntityPlayer closestTarget;

    public AutoTrap() {
        super("AutoTrap", Category.COMBAT, 13648212);
        CousinWare.INSTANCE.settingsManager.rSetting(placeRange = new Setting("PlaceRange", this, 5.5, 0, 8, false, "AutoTrapPlaceRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(placeDelay = new Setting("PlaceDelay", this, 2, 0, 20, true, "AutoTrapPlaceDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(toggleTicks = new Setting("ToggleTicks", this, 8, 0, 20, true, "AutoTrapToggleTicks"));
        CousinWare.INSTANCE.settingsManager.rSetting(noGhostBlocks = new Setting("NoGhostBlocks", this, true, "AutoTrapNoGhostBlocks"));

    }

    public void onUpdate() {
        if (closestTarget == null) return;
        delay++;
        delayToggle++;
        Vec3d[] placeEast = new Vec3d[]{ new Vec3d(1, -1, 0), new Vec3d(1, 0, 0), new Vec3d(-1, -1, 0), new Vec3d(-1, 0, 0), new Vec3d(0, -1, 1), new Vec3d(0, 0, 1), new Vec3d(0, -1, -1), new Vec3d(0, 0, -1), new Vec3d(1, 1, 0), new Vec3d(-1, 1, 0), new Vec3d(0, 1, 1), new Vec3d(0, 1, -1), new Vec3d(1, 2, 0), new Vec3d(0, 2, 0)};
        Vec3d[] placeWest = new Vec3d[]{ new Vec3d(1, -1, 0), new Vec3d(1, 0, 0), new Vec3d(-1, -1, 0), new Vec3d(-1, 0, 0), new Vec3d(0, -1, 1), new Vec3d(0, 0, 1), new Vec3d(0, -1, -1), new Vec3d(0, 0, -1), new Vec3d(1, 1, 0), new Vec3d(-1, 1, 0), new Vec3d(0, 1, 1), new Vec3d(0, 1, -1), new Vec3d(-1, 2, 0), new Vec3d(0, 2, 0)};
        Vec3d[] placeSouth = new Vec3d[]{ new Vec3d(1, -1, 0), new Vec3d(1, 0, 0), new Vec3d(-1, -1, 0), new Vec3d(-1, 0, 0), new Vec3d(0, -1, 1), new Vec3d(0, 0, 1), new Vec3d(0, -1, -1), new Vec3d(0, 0, -1), new Vec3d(1, 1, 0), new Vec3d(-1, 1, 0), new Vec3d(0, 1, 1), new Vec3d(0, 1, -1), new Vec3d(0, 2, 1), new Vec3d(0, 2, 0)};
        Vec3d[] placeNorth = new Vec3d[]{ new Vec3d(1, -1, 0), new Vec3d(1, 0, 0), new Vec3d(-1, -1, 0), new Vec3d(-1, 0, 0), new Vec3d(0, -1, 1), new Vec3d(0, 0, 1), new Vec3d(0, -1, -1), new Vec3d(0, 0, -1), new Vec3d(1, 1, 0), new Vec3d(-1, 1, 0), new Vec3d(0, 1, 1), new Vec3d(0, 1, -1), new Vec3d(0, 2, -1), new Vec3d(0, 2, 0)};

        for (Vec3d vec3d : placeEast) {
            if (delay >= placeDelay.getValInt()) {
                BlockPos pos = new BlockPos(closestTarget.getPositionVector().add(vec3d));
                if (mc.world.mayPlace(Blocks.OBSIDIAN, pos, false, EnumFacing.UP, mc.player)) {
                    mc.player.inventory.currentItem = obsidianSlot;
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    BlockInteractionHelper.placeBlockScaffold(pos);
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    mc.player.inventory.currentItem = startingHand;
                    if (noGhostBlocks.getValBoolean()) {
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.SOUTH));
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, pos, EnumFacing.SOUTH));
                    }
                    delay = 0;

                }
            }
        }
        if (delayToggle >= toggleTicks.getValInt()) {
            this.toggle();
        }
        if (!packetsBeingSent) {
            Command.sendClientSideMessage("Sending Packet");
            mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
            packetsBeingSent = true;
        }
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            if (isSpoofingAngles) {
                ((ICPacketPlayer) event.getPacket()).setYaw((float) yaw);
                ((ICPacketPlayer) event.getPacket()).setPitch((float) pitch);
            }
        } else if (isSpoofingAngles) {
        packetsBeingSent = false;

        }
    }
    public void onEnable() {
        findClosestTarget();
        startingHand = mc.player.inventory.currentItem;
        delayToggle = 0;
        delay = 0;
        packetsBeingSent = true;
        int obsidianHand = InventoryUtil.findBlockInHotbar(Blocks.OBSIDIAN);
        if (obsidianHand == -1) {
            Command.sendClientSideMessage("No Obsidian Toggling!");
        } else {
            obsidianSlot = obsidianHand;
        }
        int var24 = MathHelper.floor((double) (Minecraft.getMinecraft().player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;


        float yaw = Minecraft.getMinecraft().player.rotationYaw;



         if (var24 == 2) {

            //North
        }

        if (var24 == 1) {

            //West
        }
        if (var24 == 3) {

            //East
        }
        if (var24 == 0) {

            //South
        }
    }
    public void onDisable() {
        mc.player.inventory.currentItem = startingHand;

    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }


    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;

    //this modifies packets being sent so no extra ones are made. NCP used to flag with "too many packets"
    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = mc.player.rotationYaw;
            pitch = mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }

    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;

        double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        //to degree
        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]{yaw,pitch};
    }
    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == mc.player) {
                continue;
            }
            if (FriendManager.isFriend(target.getName())) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (this.closestTarget == null) {
                this.closestTarget = target;
            }
            else {
                if (mc.player.getDistance((Entity)target) >= mc.player.getDistance((Entity)this.closestTarget)) {
                    continue;
                }
                this.closestTarget = target;
            }
        }
    }



}
