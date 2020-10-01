package io.ace.nordclient.hacks.client;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;

public class Colors extends Hack {

    public static Setting saturation;
    public static Setting brightness;
    public static Setting speed;

    public Colors() {
        super("Colors", Category.CLIENT);

        CousinWare.INSTANCE.settingsManager.rSetting(saturation = new Setting("Saturation", this, .8, 0, 1,false,  "ColorsSaturation"));
        CousinWare.INSTANCE.settingsManager.rSetting(brightness = new Setting("Brightness", this, .8, 0, 1,false,  "ColorsBrightness"));
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, 1, 0, 5,false,  "ColorsSpeed"));

    }

    @Listener
    public void onUpdate(UpdateEvent event) {

    }
}
