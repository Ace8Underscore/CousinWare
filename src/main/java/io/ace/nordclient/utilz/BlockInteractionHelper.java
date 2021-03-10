package io.ace.nordclient.utilz;

import io.ace.nordclient.command.Command;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockInteractionHelper
{

    public static final List<Block> blackList;
    public static final List<Block> shulkerList;
    private static final Minecraft mc;
    public static double yaw;
    public static double pitch;

    public static boolean hotbarSlotCheckEmpty(final ItemStack stack) {
        return stack != ItemStack.EMPTY;
    }

    public static boolean blockCheckNonBlock(final ItemStack stack) {
        return stack.getItem() instanceof ItemBlock;
    }

    public static void placeBlockScaffold(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 18.0625) {
                    faceVectorPacketInstant(hitVec);
                    processRightClickBlock(neighbor, side2, hitVec);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    //mc.rightClickDelayTimer = 4;
                    return;
                }
            }
        }
    }

    public static void placeBlockScaffoldStrictRaytrace(final BlockPos pos) {
        BlockPos neighbor = null;
        EnumFacing side2 = null;
        if (findBlockFacingLocationBlock(pos) == 1) {
            // block pos west and EnumFacing east
            neighbor = pos.west();
            side2 = EnumFacing.EAST;
        }
        if (findBlockFacingLocationBlock(pos) == 2) {
            // block pos east and EnumFacing west
            neighbor = pos.east();
            side2 = EnumFacing.WEST;

        }
        if (findBlockFacingLocationBlock(pos) == 3) {
            // block pos north and EnumFacing south
            neighbor = pos.north();
            side2 = EnumFacing.SOUTH;

        }
        if (findBlockFacingLocationBlock(pos) == 4) {
            // block pos south and EnumFacing north
            neighbor = pos.south();
            side2 = EnumFacing.NORTH;

        }
        double y = 0;
        if (neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) < 1) y = .95;
        if (neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) > 1 && neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) < 2) y = .85;
        if (neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) > 2 && neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) < 3) y = .75;
        if (neighbor.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ) > 3) y = .65;


        RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double) mc.player.getEyeHeight(), mc.player.posZ), new Vec3d((double) neighbor.getX() + .5, (double) pos.north().getY() + y, (double) neighbor.getZ() + .5));

            final Vec3d hitVec = new Vec3d((Vec3i) neighbor).add(.5, y, .5).add(new Vec3d(result.sideHit.getDirectionVec()).scale(0.5));
            faceVectorPacketInstant(hitVec);
            processRightClickBlock(neighbor, side2, hitVec);
            mc.player.swingArm(EnumHand.MAIN_HAND);

    }
    public static void placeBlockScaffoldStrict(final BlockPos pos) {
        BlockPos neighbor = null;
        EnumFacing side2 = null;
        if (findBlockFacingLocationBlock(pos) == 1) {
            // block pos west and EnumFacing east
            neighbor = pos.west();
            side2 = EnumFacing.EAST;
        }
        if (findBlockFacingLocationBlock(pos) == 2) {
            // block pos east and EnumFacing west
            neighbor = pos.east();
            side2 = EnumFacing.WEST;

        }
        if (findBlockFacingLocationBlock(pos) == 3) {
            // block pos north and EnumFacing south
            neighbor = pos.north();
            side2 = EnumFacing.SOUTH;

        }
        if (findBlockFacingLocationBlock(pos) == 4) {
            // block pos south and EnumFacing north
            neighbor = pos.south();
            side2 = EnumFacing.NORTH;

        }
        final Vec3d hitVec = new Vec3d((Vec3i) neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
        Command.sendClientSideMessage("Placing at " + pos);
        faceVectorPacketInstant(hitVec);
        processRightClickBlock(neighbor, side2, hitVec);
        mc.player.swingArm(EnumHand.MAIN_HAND);

    }



    public static int findBlockFacingLocationBlock(final BlockPos pos) {
        double playerX = 0;
        double posX = 0;
        double distanceX = 0;
        double playerZ = 0;
        double posZ = 0;
        double distanceZ = 0;
        int direction = 0;

         playerX = mc.player.posX;
         posX = pos.getX();

        if (playerX > posX) {
            distanceX = playerX - posX;

        } else {
            distanceX = posX - playerX;
        }

         playerZ = mc.player.posZ;
         posZ = pos.getZ();

        if (playerZ > posZ) {
            distanceZ = playerZ - posZ;

        } else {
            distanceZ = posZ - playerZ;
        }

        if (distanceX > distanceZ) {
            if (playerX > posX) {
                direction = 1;

            } else {
                direction = 2;
            }

        } else {
            if (playerZ > posZ) {
                direction = 3;

            } else {
                direction = 4;
            }
        }

        return direction;
    }

    public static int findBlockFacingLocationPlayer(final BlockPos pos) {
        double playerX = 0;
        double enemyX = 0;
        double distanceX = 0;
        double playerZ = 0;
        double enemyZ = 0;
        double distanceZ = 0;
        int direction = 0;

        playerX = mc.player.posX;
        enemyX = pos.getX();

        if (playerX > enemyX) {
            distanceX = playerX - enemyX;

        } else {
            distanceX = enemyX - playerX;
        }

        playerZ = mc.player.posZ;
        enemyZ = pos.getZ();

        if (playerZ > enemyZ) {
            distanceZ = playerZ - enemyZ;

        } else {
            distanceZ = enemyZ - playerZ;
        }

        if (distanceX > distanceZ) {
            if (playerX > enemyX) {
                direction = 1;

            } else {
                direction = 2;
            }

        } else {
            if (playerZ > enemyZ) {
                direction = 3;

            } else {
                direction = 4;
            }
        }

        return direction;
    }

    public static void placeBlockScaffoldPiston(final BlockPos pos, final BlockPos look) {
        final Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final BlockPos neighborLook = look.offset(side);
            final EnumFacing side2 = side.getOpposite();
            final EnumFacing side2Look = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.9, 0.1, 0.9).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                final Vec3d hitVecLook = new Vec3d((Vec3i)neighborLook).add(0.9, 0.1, 0.9).add(new Vec3d(side2Look.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 18.0625) {
                    faceVectorPacketInstant(hitVecLook);
                    processRightClickBlock(neighbor, side2, hitVec);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    //mc.rightClickDelayTimer = 4;
                    return;
                }
            }
        }
    }

    public static void placeBlockScaffoldNoRotate(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                    processRightClickBlock(neighbor, side2, hitVec);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    //mc.rightClickDelayTimer = 4;
                    return;

            }
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

    private static void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
    }

    private static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch) };
    }

    private static Vec3d getEyesPos() {
        return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    }

    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getLegitRotations(vec);
        mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
    }

    private static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(mc.player, mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }

    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }

    private static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }

    private static PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }

    private static IBlockState getState(final BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    public static boolean checkForNeighbours(final BlockPos blockPos) {
        if (!hasNeighbour(blockPos)) {
            for (final EnumFacing side : EnumFacing.values()) {
                final BlockPos neighbour = blockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private static boolean hasNeighbour(final BlockPos blockPos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = blockPos.offset(side);
            if (!mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                return true;
            }
        }
        return false;
    }

    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }

    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public static List<BlockPos> getCircle(final BlockPos loc, final int y, final float r, final boolean hollow) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z);
                if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                    final BlockPos l = new BlockPos(x, y, z);
                    circleblocks.add(l);
                }
            }
        }
        return circleblocks;
    }

    public static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    return side;
                }
            }
        }
        return null;
    }

    static {
        blackList = Arrays.asList(Blocks.ENDER_CHEST, (Block) Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block) Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE);
        shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        mc = Minecraft.getMinecraft();
    }

    public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck, float height) {
        return !shouldCheck || mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ), new Vec3d((double)pos.getX(), (double)((float)pos.getY() + height), (double)pos.getZ()), false, true, false) == null;
    }

    public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck) {
        return rayTracePlaceCheck(pos, shouldCheck, 1.0f);
    }
}