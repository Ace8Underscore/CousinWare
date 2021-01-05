package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Calendar;


public class Welcomer extends Hack {

    Setting r;
    Setting g;
    Setting b;
    Setting rainbow;

    public Welcomer() {
        super("Welcomer", Category.RENDER,3093151);
        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 255, 0, 255, true, "ArrayListRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 26, 0, 255, true, "ArrayListGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 42, 0, 255, true, "ArrayListBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "ArrayListRainbow"));

    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        ScaledResolution sr = new ScaledResolution(mc);
        String timeMessage = "";
        long time = Calendar.getInstance().getTime().getHours();
        Color c = new Color(r.getValInt(), g.getValInt(), b.getValInt(), 255);
        if (time >= 0 && time <= 11) timeMessage = "Good Morning ";
        if (time > 11 && time <= 18) timeMessage = "Good Afternoon ";
        if (time > 18 && time < 24) timeMessage = "Good Night ";
        if (rainbow.getValBoolean()) RainbowUtil.settingRainbow(r, g, b);
        //


        if (!Core.customFont.getValBoolean()) mc.fontRenderer.drawStringWithShadow(timeMessage + mc.player.getName(), (sr.getScaledWidth() - mc.fontRenderer.getStringWidth(timeMessage + mc.player.getName())) / 2, 0, c.getRGB());
        else CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(timeMessage + mc.player.getName(), (sr.getScaledWidth() - CousinWare.INSTANCE.fontRenderer.getStringWidth(timeMessage + mc.player.getName())) / 2, 0, c.getRGB());
    }
}
