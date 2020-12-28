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
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PistonAura extends Hack {

    Vec3d startPos;
    Entity attackTarget;

    int pistonSlot;
    int crystalSlot;
    int redstoneSlot;
    int delay = 0;
    boolean placedRedstone;
    boolean placedPiston;
    boolean placedCrystal;
    double yaw;
    double pitch;

    Setting delaySetting;

    public PistonAura() {
        super("PistonAura", Category.COMBAT, 13632022);
        CousinWare.INSTANCE.settingsManager.rSetting(delaySetting = new Setting("Delay", this, 1, 0, 20, true, "PistonAuraDelay"));
    }

    public void onUpdate() {
        delay++;
        BlockPos placePistonPos = new BlockPos(startPos.x, startPos.y, startPos.z);
        if (getTarget() == null) return;

    if (delay >= delaySetting.getValInt()) {
        if (!placedRedstone && !placedPiston && !placedCrystal) {
            mc.player.inventory.currentItem = redstoneSlot;
            if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.EAST) {
                BlockInteractionHelper.placeBlockScaffold(placePistonPos.west());
                placedRedstone = true;
                delay = 0;
            }
            if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.WEST) {
                BlockInteractionHelper.placeBlockScaffold(placePistonPos.east());
                placedRedstone = true;
                delay = 0;
            }
            if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.SOUTH) {
                BlockInteractionHelper.placeBlockScaffold(placePistonPos.north());
                placedRedstone = true;
                delay = 0;
            }
            if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.NORTH) {
                BlockInteractionHelper.placeBlockScaffold(placePistonPos.south());
                placedRedstone = true;
                delay = 0;
            }
        }
        if (delay >= delaySetting.getValInt()) {
            if (placedRedstone && !placedPiston && !placedCrystal) {
                mc.player.inventory.currentItem = pistonSlot;
                if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.EAST) {
                    placedPiston = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.WEST) {
                    BlockInteractionHelper.placeBlockScaffoldPiston(placePistonPos);
                    placedPiston = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.SOUTH) {
                    BlockInteractionHelper.placeBlockScaffoldPiston(placePistonPos);
                    placedPiston = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.NORTH) {
                    lookAtPacket(placePistonPos.south().getX(), placePistonPos.south().getY() - 1, placePistonPos.south().getZ(), mc.player);
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation((int) yaw, (int) pitch, mc.player.onGround));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(placePistonPos, EnumFacing.NORTH, EnumHand.MAIN_HAND, placePistonPos.south().x, placePistonPos.south().y, placePistonPos.south().z));
                    BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos);
                    placedPiston = true;
                    delay = 0;
                }

            }
        }
        if (delay >= delaySetting.getValInt()) {
            if (placedRedstone && placedPiston && !placedCrystal) {

                mc.player.inventory.currentItem = crystalSlot;
                if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.EAST) {
                    BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos.east());
                    placedCrystal = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.WEST) {
                    BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos.west());
                    placedCrystal = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.SOUTH) {
                    BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos.south());
                    placedCrystal = true;
                    delay = 0;
                }
                if (getPistonPlaceDirection(getTarget(), placePistonPos) == EnumFacing.NORTH) {
                    lookAtPacket(placePistonPos.south().getX(), placePistonPos.south().getY() - 1, placePistonPos.south().getZ(), mc.player);
                    BlockInteractionHelper.placeBlockScaffoldNoRotate(placePistonPos.north());
                    placedCrystal = true;
                    delay = 0;
                }
            }
        }

        if (delay >= delaySetting.getValInt() && mc.world.getBlockState(placePistonPos).getBlock().canPlaceBlockAt(mc.world, placePistonPos)) {
            placedCrystal = false;
            placedPiston = false;
            placedRedstone = false;
        }


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
        if (InventoryUtil.findItemInHotbar(Items.END_CRYSTAL) == -1) {
            Command.sendClientSideMessage("No Crystals Found");
            this.disable();
        } else {
            crystalSlot = InventoryUtil.findItemInHotbar(Items.END_CRYSTAL);
        }

        if (InventoryUtil.findBlockInHotbar(Blocks.REDSTONE_BLOCK) == -1) {
            Command.sendClientSideMessage("No Redstone Blocks Found");
            this.disable();
        } else {
            redstoneSlot = InventoryUtil.findBlockInHotbar(Blocks.REDSTONE_BLOCK);
        }

        if (InventoryUtil.findBlockInHotbar(Blocks.PISTON) == -1) {
            Command.sendClientSideMessage("No Pistons Found");
            this.disable();
        } else {
            pistonSlot = InventoryUtil.findBlockInHotbar(Blocks.PISTON);
        }
    }

    public Entity getTarget() {
        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= 5)
                .filter(entity -> !entity.isDead)
                .filter(entity -> entity instanceof EntityPlayer)
                .filter(entity -> ((EntityPlayer) entity).getHealth() > 0)
                .filter(entity -> !FriendManager.isFriend(entity.getName()))
                .sorted(Comparator.comparing(e -> mc.player.getDistance(e)))
                .collect(Collectors.toList());

        targets.forEach(target -> {
            attackTarget = target;
        });
        return attackTarget;
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

