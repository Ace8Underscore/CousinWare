package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.managers.RotationManager;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class PistonAura2 extends Hack {

    Vec3d startPos;
    Entity attackTarget;

    int pistonSlot;
    int crystalSlot;
    int redstoneSlot;
    int delay = 0;
    int delay2 = 0;
    boolean placedRedstone;
    boolean placedPiston;
    boolean placedCrystal;
    boolean crystalInOff;
    boolean brokeCrystal;
    double yaw;
    double pitch;

    Setting mode;
    Setting delaySetting;
    Setting redstoneDelay;
    Setting twobtwot;
    Setting strict;
    Setting check;
    Setting lagComp;
    Setting pistonRotate;

    private EntityPlayer closestTarget;

    public PistonAura2() {
        super("PistonAura", Category.COMBAT, 13632022);
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("RedstoneBlock");
        modes.add("RedstoneTorch");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("PowerMode", this, "RedstoneBlock", modes, "PistonAura2Modes"));
        CousinWare.INSTANCE.settingsManager.rSetting(delaySetting = new Setting("Delay", this, 1, 0, 20, true, "PistonAura2Delay"));
        CousinWare.INSTANCE.settingsManager.rSetting(redstoneDelay = new Setting("RedstoneDelay", this, 1, 0, 6, true, "PistonAura2RedstoneDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(twobtwot = new Setting("2b2t", this, true, "PistionAura22b2t"));
        CousinWare.INSTANCE.settingsManager.rSetting(strict = new Setting("StrictPlace", this, false, "PistonAura2Strict"));
        CousinWare.INSTANCE.settingsManager.rSetting(check = new Setting("Check", this, true, "PistonAura2Check"));
        CousinWare.INSTANCE.settingsManager.rSetting(lagComp = new Setting("LagComp", this, 1, 0, 20, true, "PistonAura2LagComp"));
        CousinWare.INSTANCE.settingsManager.rSetting(pistonRotate = new Setting("PistonRotate", this, true, "PistonAura2Rotate"));
    }

    public void onUpdate() {
        delay++;
        BlockPos placePistonPos = new BlockPos(startPos.x, startPos.y, startPos.z);
        if (closestTarget == null) return;

    if (delay >= delaySetting.getValInt()) {
        if (!placedRedstone && !placedPiston && !placedCrystal) {
            mc.player.inventory.currentItem = pistonSlot;
            if (mc.world.getBlockState(placePistonPos.west()).getBlock().canPlaceBlockAt(mc.world, placePistonPos)) {
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.EAST) {
                    if (strict.getValBoolean()) {
                        lookAtPacket(placePistonPos.west().getX(), placePistonPos.west().getY(), placePistonPos.west().getZ(), mc.player);
                    } else {
                        lookAtPacket(placePistonPos.west().getX(), placePistonPos.west().west().getY(), placePistonPos.west().west().getZ(), mc.player);
                    }
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation((int) yaw, (int) pitch, mc.player.onGround));
                    if (pistonRotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffold(placePistonPos);
                    else BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos);
                    delay = 0;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.WEST) {
                    if (strict.getValBoolean()) {
                        lookAtPacket(placePistonPos.east().getX(), placePistonPos.east().getY(), placePistonPos.east().getZ(), mc.player);
                    } else {
                        lookAtPacket(placePistonPos.east().east().getX(), placePistonPos.east().getY(), placePistonPos.east().east().getZ(), mc.player);
                    }
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation((int) yaw, (int) pitch, mc.player.onGround));
                    if (pistonRotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffold(placePistonPos);
                    else BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos);
                    delay = 0;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.SOUTH) {
                    if (strict.getValBoolean()) {
                        lookAtPacket(placePistonPos.north().getX(), placePistonPos.north().getY(), placePistonPos.north().getZ(), mc.player);
                    } else {
                        lookAtPacket(placePistonPos.north().north().getX(), placePistonPos.north().getY(), placePistonPos.north().north().getZ(), mc.player);
                    }
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation((int) yaw, (int) pitch, mc.player.onGround));
                    if (pistonRotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffold(placePistonPos);
                    else BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos);
                    delay = 0;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.NORTH) {
                    if (strict.getValBoolean()) {
                        lookAtPacket(placePistonPos.south().getX(), placePistonPos.south().getY(), placePistonPos.south().getZ(), mc.player);
                    } else {
                        lookAtPacket(placePistonPos.south().south().getX(), placePistonPos.south().getY(), placePistonPos.south().south().getZ(), mc.player);
                    }
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation((int) yaw, (int) pitch, mc.player.onGround));
                    if (pistonRotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffold(placePistonPos);
                    else BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos);
                    delay = 0;
                }
            }
            if (check.getValBoolean()) {
                if (mc.world.getBlockState(placePistonPos).getBlock().equals(Blocks.PISTON)) {
                    delay2++;
                    if (delay2 > lagComp.getValInt()) {
                        placedPiston = true;
                        delay2 = 0;
                    }
                }

            } else {
                placedPiston = true;
            }

        }
    }
        if (delay >= delaySetting.getValInt()) {
            if (!placedRedstone && placedPiston && !placedCrystal) {
                if (!crystalInOff) {
                    mc.player.inventory.currentItem = crystalSlot;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.EAST) {
                    if (twobtwot.getValBoolean()) {
                        BlockInteractionHelper.placeBlockScaffold(placePistonPos.east());
                    } else {
                        BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos.east());
                    }
                    placedCrystal = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.WEST) {
                    if (twobtwot.getValBoolean()) {
                        BlockInteractionHelper.placeBlockScaffold(placePistonPos.west());
                    } else {
                        BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos.west());
                    }
                    placedCrystal = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.SOUTH) {
                    if (twobtwot.getValBoolean()) {
                        BlockInteractionHelper.placeBlockScaffold(placePistonPos.south());
                    } else {
                        BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos.south());
                    }
                    placedCrystal = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.NORTH) {
                    if (twobtwot.getValBoolean()) {
                        BlockInteractionHelper.placeBlockScaffold(placePistonPos.north());
                    } else {
                        BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos.north());
                    }
                    placedCrystal = true;
                    delay = 0;
                }
            }
        }
        if (delay >= delaySetting.getValInt() + redstoneDelay.getValInt()) {
            if (!placedRedstone && placedPiston && placedCrystal) {
                mc.player.inventory.currentItem = redstoneSlot;
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.EAST) {
                    BlockInteractionHelper.placeBlockScaffold(placePistonPos.west());
                    placedRedstone = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.WEST) {
                    BlockInteractionHelper.placeBlockScaffold(placePistonPos.east());
                    placedRedstone = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.SOUTH) {
                    BlockInteractionHelper.placeBlockScaffold(placePistonPos.north());
                    placedRedstone = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.NORTH) {
                    BlockInteractionHelper.placeBlockScaffold(placePistonPos.south());
                    placedRedstone = true;
                    delay = 0;
                }
            }
        }
       /* if (delay >= delaySetting.getValInt() && placedRedstone && placedPiston && placedCrystal && !brokeCrystal) {
            for (Entity crystal : mc.world.loadedEntityList) {
                if (crystal instanceof EntityEnderCrystal) {
                    if (mc.player.getDistance(crystal) <= 4) {
                        mc.playerController.attackEntity(mc.player, crystal);
                    }
                    if (crystal.isDead) {
                        brokeCrystal = true;
                    }
                }
            }
        } */

        if (delay >= delaySetting.getValInt() && mc.world.getBlockState(placePistonPos).getBlock().canPlaceBlockAt(mc.world, placePistonPos)) {
            placedCrystal = false;
            placedPiston = false;
            placedRedstone = false;
            brokeCrystal = false;
        }



    }


    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = RotationManager.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private void setYawAndPitch(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }



    public void onEnable() {
        startPos = mc.objectMouseOver.hitVec;
        placedCrystal = false;
        placedPiston = false;
        placedRedstone = false;
        crystalInOff = false;
        brokeCrystal = false;
        findClosestTarget();
        if (!mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL)) {
            if (InventoryUtil.findItemInHotbar(Items.END_CRYSTAL) == -1) {
                Command.sendClientSideMessage("No Crystals Found");
                this.disable();
            } else {
                crystalSlot = InventoryUtil.findItemInHotbar(Items.END_CRYSTAL);
            }
        } else {
            crystalInOff = true;
        }
        if (mode.getValString().equalsIgnoreCase("RedstoneTorch")) {
            if (InventoryUtil.findBlockInHotbar(Blocks.REDSTONE_TORCH) == -1) {
                Command.sendClientSideMessage("No Redstone Torches Found");
                this.disable();
            } else {
                redstoneSlot = InventoryUtil.findBlockInHotbar(Blocks.REDSTONE_TORCH);
            }
        } else {
            if (InventoryUtil.findBlockInHotbar(Blocks.REDSTONE_BLOCK) == -1) {
                Command.sendClientSideMessage("No Redstone Blocks Found");
                this.disable();
            } else {
                redstoneSlot = InventoryUtil.findBlockInHotbar(Blocks.REDSTONE_BLOCK);
            }
        }

        if (InventoryUtil.findBlockInHotbar(Blocks.PISTON) == -1) {
            Command.sendClientSideMessage("No Pistons Found");
            this.disable();
        } else {
            pistonSlot = InventoryUtil.findBlockInHotbar(Blocks.PISTON);
        }
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

    public EnumFacing getPistonPlaceDirection(Entity enemy, BlockPos pistonPos) {
        double enemyX = enemy.posX;
        double enemyZ = enemy.posZ;
        double pistonX = pistonPos.getX();
        double pistonZ = pistonPos.getZ();
        EnumFacing face = null;

        if (enemyX > pistonX + .75) {
            face = EnumFacing.EAST;
        }

        if (enemyX < pistonX - .75) {
            face = EnumFacing.WEST;
        }

        if (enemyZ > pistonZ + .75) {
            face = EnumFacing.SOUTH;
        }

        if (enemyZ < pistonZ - .75) {
            face = EnumFacing.NORTH;
        }
        return face;
    }
}
//
