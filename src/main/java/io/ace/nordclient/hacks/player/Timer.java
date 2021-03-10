package io.ace.nordclient.hacks.player;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.mixin.accessor.ITimer;
import io.ace.nordclient.utilz.Setting;

public class Timer extends Hack {

    Setting speed;

    public Timer() {
        super("Timer", Category.PLAYER, 6816291);
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Timer", this, 3, .1, 10, false, "TimerSpeed"));

    }

    @Override
    public void onUpdate() {
        ((ITimer)mc).setTickLength((float) (50 /speed.getValDouble()));
    }

    @Override
    public void onDisable() {
        ((ITimer)mc.timer).setTickLength(50);


    }
}
