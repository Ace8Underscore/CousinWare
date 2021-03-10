package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.MathUtil;
import io.ace.nordclient.utilz.Setting;

import java.util.ArrayList;

/**
 * @author Ace________/Ace_#1233
 */

public class Strafe extends Hack {

    int delay = 0;
    Setting speedMode;
    Setting speed;
    Setting smartFall;
    boolean lowHopped;

    public Strafe() {
        super("Speed", Category.MOVEMENT, 16617836);

        ArrayList<String> speedModes = new ArrayList<>();
        speedModes.add("Launch");
        speedModes.add("Strafe");
        speedModes.add("StrafeAcel");
        speedModes.add("LowHop");
        CousinWare.INSTANCE.settingsManager.rSetting(speedMode = new Setting("Mode", this,"Launch" ,speedModes, "StrafeMode"));
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, .5, 0, .6, false, "StrafeSpeed"));
        CousinWare.INSTANCE.settingsManager.rSetting(smartFall = new Setting("SmartFall", this, true, "StrafeSmartFall"));
    }

    @Override
    public void onUpdate() {
        mc.player.setSprinting(true);
        if (smartFall.getValBoolean()) {
            if (mc.player.motionY < 0 && mc.player.motionY > -.5) mc.player.motionY *= 1.10;
            else mc.player.motionY *= 1;
        }
        delay++;
        if (speedMode.getValString().equalsIgnoreCase("launch")) doSpeedLaunch();
        if (speedMode.getValString().equalsIgnoreCase("strafe")) doSpeedStrafe();
        if (speedMode.getValString().equalsIgnoreCase("strafeacel")) doSpeedStafeAcel();
        if (speedMode.getValString().equalsIgnoreCase("lowhop")) doSpeedLowHop();

//

    }

    public void doSpeedLowHop() {
        mc.player.motionY *= .985;
        if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
            delay++;
            if (mc.player.onGround) {
                mc.player.jump();
                lowHopped = false;
                final double[] dir3 = MathUtil.directionSpeed(speed.getValDouble());
                mc.player.motionX = dir3[0];
                mc.player.motionZ = dir3[1];
            } else {
                final double[] dir = MathUtil.directionSpeed(.26);
                mc.player.motionX = dir[0];
                mc.player.motionZ = dir[1];
            }
            if (delay > 10 && !lowHopped) {
                final double[] dir2 = MathUtil.directionSpeed(.3);
                mc.player.setVelocity(dir2[0], -.1, dir2[1]);
                delay = 0;
                lowHopped = true;
            }
        }
    }
    //

    public void doSpeedLaunch() {
        mc.player.motionY *= 1;
        if (mc.player.onGround) {
            if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                mc.player.jump();
                final double[] dir = MathUtil.directionSpeed(.43);
                mc.player.motionX = dir[0];
                mc.player.motionZ = dir[1];
            }
        }
    }

    public void doSpeedStrafe() {
        mc.player.motionY *= .985;
        if (mc.player.onGround) {
            if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                mc.player.jump();
                final double[] dir = MathUtil.directionSpeed(speed.getValDouble());
                mc.player.motionX = dir[0];
                mc.player.motionZ = dir[1];
            }
        } else {
                final double[] dir = MathUtil.directionSpeed(.26);
                mc.player.motionX = dir[0];
                mc.player.motionZ = dir[1];
            }

        }

        public void doSpeedStafeAcel() {
            mc.player.motionY *= 1;
            if (mc.player.onGround) {
                if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                    mc.player.jump();
                    final double[] dir = MathUtil.directionSpeed(.26);
                    mc.player.motionX = dir[0];
                    mc.player.motionZ = dir[1];
                }
            } else {
                if (delay == 0 || delay == 1 || delay == 2 || delay == 3) {
                    final double[] dir = MathUtil.directionSpeed(.26);
                    mc.player.motionX = dir[0];
                    mc.player.motionZ = dir[1];
                }
                if (delay >= 4) {
                    final double[] dir = MathUtil.directionSpeed(speed.getValDouble());
                    mc.player.motionX = dir[0];
                    mc.player.motionZ = dir[1];
                    delay = 0;
                }
            }
        }

        public void onDisable() {
        delay = 0;
        }
//
}
