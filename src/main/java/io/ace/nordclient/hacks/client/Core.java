package io.ace.nordclient.hacks.client;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Core extends Hack {

    public static Setting saturation;
    public static Setting brightness;
    public static Setting speed;
    public static Setting customFont;

    public Core() {
        super("Core", Category.CLIENT);

        CousinWare.INSTANCE.settingsManager.rSetting(saturation = new Setting("Saturation", this, .8, 0, 1,false,  "ColorsSaturation"));
        CousinWare.INSTANCE.settingsManager.rSetting(brightness = new Setting("Brightness", this, .8, 0, 1,false,  "ColorsBrightness"));
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, 1, 0, 5,false,  "ColorsSpeed"));
        CousinWare.INSTANCE.settingsManager.rSetting(customFont = new Setting("CustomFont", this, true, "CoreCustomFont"));

    }

}
