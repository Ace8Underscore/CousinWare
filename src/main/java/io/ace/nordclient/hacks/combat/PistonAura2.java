package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.utilz.BlockInteractionHelper;
import io.ace.nordclient.utilz.InventoryUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
    Setting pistonMode;
    Setting delaySetting;
    Setting redstoneDelay;
    Setting twobtwot;
    Setting strict;
    Setting check;
    Setting lagComp;
    Setting pistonRotate;
    Setting autoBreak;
    Setting crystalBreakRange;
    Setting rotateBreak;
    Setting swing;
    Setting breakDelay;

    private EntityPlayer closestTarget;

    public PistonAura2() {
        super("PistonAura", Category.COMBAT, 13632022);
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("RedstoneBlock");
        modes.add("RedstoneTorch");
        ArrayList<String> pistonModes = new ArrayList<String>();
        pistonModes.add("Piston");
        pistonModes.add("StickyPiston");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("PowerMode", this, "RedstoneBlock", modes, "PistonAura2Modes"));
        CousinWare.INSTANCE.settingsManager.rSetting(pistonMode = new Setting("PisonMode", this, "Piston", pistonModes, "PistonAura2PistonModes"));
        CousinWare.INSTANCE.settingsManager.rSetting(delaySetting = new Setting("Delay", this, 1, 0, 20, true, "PistonAura2Delay"));
        CousinWare.INSTANCE.settingsManager.rSetting(redstoneDelay = new Setting("RedstoneDelay", this, 1, 0, 20, true, "PistonAura2RedstoneDelay"));
        CousinWare.INSTANCE.settingsManager.rSetting(twobtwot = new Setting("2b2t", this, true, "PistionAura22b2t"));
        CousinWare.INSTANCE.settingsManager.rSetting(strict = new Setting("StrictPlace", this, false, "PistonAura2Strict"));
        CousinWare.INSTANCE.settingsManager.rSetting(check = new Setting("Check", this, true, "PistonAura2Check"));
        CousinWare.INSTANCE.settingsManager.rSetting(lagComp = new Setting("LagComp", this, 1, 0, 20, true, "PistonAura2LagComp"));
        CousinWare.INSTANCE.settingsManager.rSetting(pistonRotate = new Setting("PistonRotate", this, true, "PistonAura2Rotate"));
        CousinWare.INSTANCE.settingsManager.rSetting(autoBreak = new Setting("CrystalBreak", this, true, "PistonAuraAutoBreak"));
        CousinWare.INSTANCE.settingsManager.rSetting(crystalBreakRange = new Setting("BreakRange", this, 3, 0, 6, true, "PistonAura2BreakRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(rotateBreak = new Setting("RotateBreak", this, true, "PistonAuraRotateBreak"));
        CousinWare.INSTANCE.settingsManager.rSetting(swing = new Setting("Swing", this, false, "PistonAuraSwing"));
        CousinWare.INSTANCE.settingsManager.rSetting(breakDelay = new Setting("BreakDelay", this, 3, 0, 20, true, "PistonAura2BreakDelay"));

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
                    if (pistonRotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffoldPiston(placePistonPos, placePistonPos.west().west());
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
                    if (pistonRotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffoldPiston(placePistonPos, placePistonPos.east().east());
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
                    if (pistonRotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffoldPiston(placePistonPos, placePistonPos.north().north());
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
                    if (pistonRotate.getValBoolean()) BlockInteractionHelper.placeBlockScaffoldPiston(placePistonPos, placePistonPos.south().south());
                    else BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos);
                    delay = 0;
                }
            }
            if (check.getValBoolean()) {
                if (mc.world.getBlockState(placePistonPos).getBlock().equals(Blocks.PISTON) || mc.world.getBlockState(placePistonPos).getBlock().equals(Blocks.STICKY_PISTON)) {
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
        if (delay >= delaySetting.getValInt() + breakDelay.getValInt() && placedRedstone && placedPiston && placedCrystal && !brokeCrystal && autoBreak.getValBoolean()) {

            if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.EAST) {
                if (mc.world.getBlockState(placePistonPos.east()).getBlock().equals(Blocks.PISTON_HEAD)) {
                    for (Entity e : mc.world.loadedEntityList) {
                        if (e instanceof EntityEnderCrystal) {
                            if (mc.player.getDistance(e) < crystalBreakRange.getValDouble()) {
                                if (rotateBreak.getValBoolean()) lookAtPacket(e.posX, e.posY, e.posZ, mc.player);
                                if (rotateBreak.getValBoolean()) mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float)yaw, (float) pitch, mc.player.onGround));
                                mc.playerController.attackEntity(mc.player, e);
                                if (swing.getValBoolean()) mc.player.swingArm(EnumHand.MAIN_HAND);
                            }
                        }
                    }
                    brokeCrystal = true;
                    delay = 0;
                }

            }
            if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.WEST) {
                if (mc.world.getBlockState(placePistonPos.west()).getBlock().equals(Blocks.PISTON_HEAD)) {
                    for (Entity e : mc.world.loadedEntityList) {
                        if (e instanceof EntityEnderCrystal) {
                            if (mc.player.getDistance(e) < crystalBreakRange.getValDouble()) {
                                if (rotateBreak.getValBoolean()) lookAtPacket(e.posX, e.posY, e.posZ, mc.player);
                                if (rotateBreak.getValBoolean()) mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float)yaw, (float) pitch, mc.player.onGround));
                                mc.playerController.attackEntity(mc.player, e);
                                if (swing.getValBoolean()) mc.player.swingArm(EnumHand.MAIN_HAND);

                            }
                        }
                    }
                    brokeCrystal = true;
                    delay = 0;
                }

            }
            if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.SOUTH) {
                if (mc.world.getBlockState(placePistonPos.south()).getBlock().equals(Blocks.PISTON_HEAD)) {
                    for (Entity e : mc.world.loadedEntityList) {
                        if (e instanceof EntityEnderCrystal) {
                            if (mc.player.getDistance(e) < crystalBreakRange.getValDouble()) {
                                if (rotateBreak.getValBoolean()) lookAtPacket(e.posX, e.posY, e.posZ, mc.player);
                                if (rotateBreak.getValBoolean()) mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float)yaw, (float) pitch, mc.player.onGround));
                                mc.playerController.attackEntity(mc.player, e);
                                if (swing.getValBoolean()) mc.player.swingArm(EnumHand.MAIN_HAND);

                            }
                        }
                    }
                    brokeCrystal = true;
                    delay = 0;
                }

            }
            if (getPistonPlaceDirection(closestTarget, placePistonPos) == EnumFacing.NORTH) {
                if (mc.world.getBlockState(placePistonPos.north()).getBlock().equals(Blocks.PISTON_HEAD)) {
                    for (Entity e : mc.world.loadedEntityList) {
                        if (e instanceof EntityEnderCrystal) {
                            if (mc.player.getDistance(e) < crystalBreakRange.getValDouble()) {
                                if (rotateBreak.getValBoolean()) lookAtPacket(e.posX, e.posY, e.posZ, mc.player);
                                if (rotateBreak.getValBoolean()) mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float)yaw, (float) pitch, mc.player.onGround));
                                mc.playerController.attackEntity(mc.player, e);
                                if (swing.getValBoolean()) mc.player.swingArm(EnumHand.MAIN_HAND);

                            }
                        }
                    }
                    brokeCrystal = true;
                    delay = 0;
                }

            }
        }

        if (delay >= delaySetting.getValInt() && mc.world.getBlockState(placePistonPos).getBlock().canPlaceBlockAt(mc.world, placePistonPos) && brokeCrystal) {
            placedCrystal = false;
            placedPiston = false;
            placedRedstone = false;
            brokeCrystal = false;
        }
        if (delay >= delaySetting.getValInt() && mc.world.getBlockState(placePistonPos).getBlock().canPlaceBlockAt(mc.world, placePistonPos) && !autoBreak.getValBoolean()) {
            placedCrystal = false;
            placedPiston = false;
            placedRedstone = false;
            brokeCrystal = false;
        }



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
        if (pistonMode.getValString().equalsIgnoreCase("Piston")) {
            if (InventoryUtil.findBlockInHotbar(Blocks.PISTON) == -1) {
                Command.sendClientSideMessage("No Pistons Found");
                this.disable();
            } else {
                pistonSlot = InventoryUtil.findBlockInHotbar(Blocks.PISTON);
            }
        } else {
            if (InventoryUtil.findBlockInHotbar(Blocks.STICKY_PISTON) == -1) {
                Command.sendClientSideMessage("No Pistons Found");
                this.disable();
            } else {
                pistonSlot = InventoryUtil.findBlockInHotbar(Blocks.STICKY_PISTON);
            }
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
    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    //this modifies packets being sent so no extra ones are made. NCP used to flag with "too many packets"
    private void setYawAndPitch(float yaw1, float pitch1) {
        this.yaw = yaw1;
        pitch = pitch1;
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
}
//
