package io.ace.nordclient.managers;

import net.minecraft.client.Minecraft;

/**
 * @author Ace________/Ace_#1233
 */

public class RotationManager {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void rotateHead(float yaw) {
        mc.player.setRotationYawHead(yaw);



    }
}
