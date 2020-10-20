package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyColor extends Hack {
    //
    Setting r;
    Setting g;
    Setting b;
    Setting rainbow;
    Setting speed;

    public SkyColor() {
        super("SkyColor", Category.RENDER, "Changes the sky's color.");
        //
        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 1, 0, 255, false, "SkyColorRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 1, 0, 255, false, "SkyColorGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 1, 0, 255, false, "SkyColorBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "SkyColorRainbow"));
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("RainbowSpeed", this, 1, 0, 255, true, "SkyColorSpeed"));

    }

    @Override
    public void onUpdate() {
        if (rainbow.getValBoolean()) {
            RainbowUtil.settingRainbow(r, g, b);
        }
    }

    @SubscribeEvent
    public void fogColors(EntityViewRenderEvent.FogColors event) {
        event.setRed((float) r.getValDouble() / 255);
        event.setGreen((float) g.getValDouble() / 255);
        event.setBlue((float) b.getValDouble() / 255);


    }


}