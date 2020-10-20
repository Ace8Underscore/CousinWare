package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;

public class QuickDrop extends Hack {
    int delay = 0;
    Setting speed;


    public QuickDrop() {
        super("QuickDrop", Category.MISC, "When Holding Q items will contine to drop");
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, 5, 0, 5, true, "QuickDropSpeed"));

    }

    @Override
    public void onUpdate() {

    if (mc.gameSettings.keyBindDrop.isKeyDown()) {
        delay++;
        if (delay > speed.getValInt()) {
            mc.player.dropItem(false);
            delay = 0;
        }



        } else {
        delay = 0;
    }

    }
}
