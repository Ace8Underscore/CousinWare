package io.ace.nordclient.utilz;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class HoleUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isBedrockHole(BlockPos pos) {
        boolean retVal = false;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK))
        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK))
        if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK))
        if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK))
        if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK))
        retVal = true;
        return retVal;

    }

    public static boolean isObiHole(BlockPos pos) {
        boolean retVal = false;
        int obiCount = 0;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
            if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
                if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
            if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                    obiCount++;
                }
                if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                    if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                        obiCount++;
                    }
                    if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                        if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                            obiCount++;
                        }
                        if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                            if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                                obiCount++;
                            }
                            if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                    obiCount++;
                                }
                                if (obiCount >= 1) {
                                    retVal = true;
                                }
                            }
                        }
                    }
                }
            }
                }
            }
        }
        return retVal;

    }

    public static boolean isHole(BlockPos pos) {
        boolean retVal = false;
        if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR))
        if (mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN))
        if (mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN))
        retVal = true;

        return retVal;
    }


}
