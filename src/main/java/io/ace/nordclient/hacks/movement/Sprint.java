package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.MathUtil;
import io.ace.nordclient.utilz.MotionUtil;
import io.ace.nordclient.utilz.Setting;

public class Sprint extends Hack {

    Setting strict;
    String returnMessage = "";

    public Sprint() {
        super("Sprint", Category.MOVEMENT, 11592432);
        CousinWare.INSTANCE.settingsManager.rSetting(strict = new Setting("Strict", this, true, ""));
    }

    @Override
    public void onUpdate() {
        if (strict.getValBoolean()) {
            returnMessage = "Strict";
            if (mc.player.moveForward > 0 && !mc.player.collidedHorizontally) {
                mc.player.setSprinting(true);
            }
        } else {
            returnMessage = "Rage";
            if (MotionUtil.isMoving()) {
                mc.player.setSprinting(true);
                if (mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() && !mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown()) {
                    final double[] dir = MathUtil.directionSpeed(.18);
                    mc.player.motionX = dir[0];
                    mc.player.motionZ = dir[1];
                }
            }
        }

    }



    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + returnMessage + "\u00A77]";
    }
}
