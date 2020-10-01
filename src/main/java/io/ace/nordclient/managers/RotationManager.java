package io.ace.nordclient.managers;

import net.minecraft.client.Minecraft;

public class RotationManager {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void rotateHead(float yaw) {
        mc.player.setRotationYawHead(yaw);



    }
}
