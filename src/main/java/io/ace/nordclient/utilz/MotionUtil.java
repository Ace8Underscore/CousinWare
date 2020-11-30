package io.ace.nordclient.utilz;

import net.minecraft.client.Minecraft;

public class MotionUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isMoving() {
        return mc.player.motionX > .05 || mc.player.motionX < -.05 || mc.player.motionZ > .05 || mc.player.motionZ < -.05;
    }

}
