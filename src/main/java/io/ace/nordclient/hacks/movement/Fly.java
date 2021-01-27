package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;

public class Fly extends Hack {

    Setting speed;

    public Fly() {
        super("Fly", Category.MOVEMENT, 11813813);
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, 1, 0, 3, false, "FlySpeed"));
    }

    @Override
    public void onUpdate() {
        mc.player.capabilities.flySpeed = (float) speed.getValDouble();
    }

    @Override
    public void onEnable() {
        mc.player.capabilities.allowFlying = true;
        mc.player.capabilities.isFlying = true;
    }

    @Override
    public void onDisable() {
        mc.player.capabilities.allowFlying = false;
        mc.player.capabilities.isFlying = false;
    }
}
