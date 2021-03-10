package io.ace.nordclient.utilz;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil {

    private final static Minecraft mc = Minecraft.getMinecraft();

    public static BlockPos getPlayerPos() {
        return new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    }
}
