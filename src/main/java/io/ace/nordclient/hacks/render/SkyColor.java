package io.ace.nordclient.hacks.render;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyColor extends Hack {
    //
    Setting r;
    Setting g;
    Setting b;

    public SkyColor() {
        super("SkyColor", Category.RENDER, "Changes the sky's color.");
        //
        r = new Setting("Red", this, 0, 0.0, 1.0, false, "SkyColorRed");
        NordClient.INSTANCE.settingsManager.rSetting(r);
        g = new Setting("Green", this, 0, 0.0, 1.0, false, "SkyColorGreen");
        NordClient.INSTANCE.settingsManager.rSetting(g);
        b = new Setting("Blue", this, 0, 0.0, 1.0, false, "SkyColorBlue");
        NordClient.INSTANCE.settingsManager.rSetting(b);


    }

    @SubscribeEvent
    public void fogColors(EntityViewRenderEvent.FogColors event) {
        event.setRed((float) r.getValDouble());
        event.setGreen((float) g.getValDouble());
        event.setBlue((float) b.getValDouble());


    }

}