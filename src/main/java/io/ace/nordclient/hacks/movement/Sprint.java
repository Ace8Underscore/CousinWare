package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.EventPlayerTravel;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.MotionUtil;
import io.ace.nordclient.utilz.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Sprint extends Hack {

    Setting strict;
    String returnMessage = "";

    public Sprint() {
        super("Sprint", Category.MOVEMENT, 11592432);
        CousinWare.INSTANCE.settingsManager.rSetting(strict = new Setting("Strict", this, true, ""));
    }

    public void onUpdate() {
        if (strict.getValBoolean()) {
            if (mc.player.moveForward > 0 && !mc.player.collidedHorizontally) {
                mc.player.setSprinting(true);
                returnMessage = "Strict";
            }
        } else if (MotionUtil.isMoving()) {
            mc.player.setSprinting(true);
            returnMessage = "Rage";
        }

    }

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + returnMessage + "\u00A77]";
    }
}
