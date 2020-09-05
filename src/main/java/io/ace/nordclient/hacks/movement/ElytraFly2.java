package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraft.util.math.MathHelper;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class ElytraFly2 extends Hack {

    public ElytraFly2() {
        super("ElytraFly2", Category.MOVEMENT);

    }

    @Listener
    public void onUpdate(UpdateEvent event) {


        if (mc.player.isElytraFlying() && mc.gameSettings.keyBindJump.isKeyDown()) {
            final float yaw = GetRotationYawForCalc();
            mc.player.motionX -= MathHelper.sin(yaw) * .1 / 10;
            mc.player.motionZ += MathHelper.cos(yaw) * .1 / 10;

        }
        if (mc.player.isElytraFlying() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            final float yaw = GetRotationYawForCalc();
            mc.player.motionX -= MathHelper.sin(yaw) * .20 / 10;
            mc.player.motionZ += MathHelper.cos(yaw) * .20 / 10;

        }
    }

    private float GetRotationYawForCalc() {
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }

    @Override
    public String getHudInfo() {
        return "2b";
    }
}
