package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.hacks.Hack;

public class Fly extends Hack {

    public Fly() {
        super("Fly", Category.MOVEMENT, 11813813);
    }

    public void onEnable() {
        mc.player.capabilities.allowFlying = true;
        mc.player.capabilities.isFlying = true;
    }

    public void onDisable() {
        mc.player.capabilities.allowFlying = false;
        mc.player.capabilities.isFlying = false;
    }
}
