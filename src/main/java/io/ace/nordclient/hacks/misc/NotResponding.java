package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;

public class NotResponding extends Hack {

    int counter = 0;

    Setting r;
    Setting g;
    Setting b;
    Setting rainbow;

    public NotResponding() {
        super("NotResponding", Category.MISC, 14707765);
        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 255, 0, 255, true, "ArrayListRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 26, 0, 255, true, "ArrayListGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 42, 0, 255, true, "ArrayListBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "ArrayListRainbow"));

    }

    @Override
    public void onUpdate() {
        counter++;

    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event)  {

        ScaledResolution sr = new ScaledResolution(mc);
        Color c = new Color(r.getValInt(), g.getValInt(), b.getValInt(), 255);
        if (rainbow.getValBoolean()) RainbowUtil.settingRainbow(r, g, b);
        double counterRemainder = (counter % 20) / 2;
        double timeNotRespond = counter / 20 + (counterRemainder / 10);

        if (counter >= 20) {
            if (!HackManager.getHackByName("Welcomer").isEnabled()) {
                if (!Core.customFont.getValBoolean())
                    mc.fontRenderer.drawStringWithShadow("Server is not responding " + timeNotRespond + "s", (sr.getScaledWidth() - mc.fontRenderer.getStringWidth("Server is not responding " + timeNotRespond + "s")) / 2, 0, c.getRGB());
                else
                    CousinWare.INSTANCE.fontRenderer.drawStringWithShadow("Server is not responding " + timeNotRespond + "s", (sr.getScaledWidth() - CousinWare.INSTANCE.fontRenderer.getStringWidth("Server is not responding " + timeNotRespond + "s")) / 2, 0, c.getRGB());
            } else {
                if (!Core.customFont.getValBoolean())
                    mc.fontRenderer.drawStringWithShadow("Server is not responding " + timeNotRespond + "s", (sr.getScaledWidth() - mc.fontRenderer.getStringWidth("Server is not responding " + timeNotRespond + "s")) / 2, 15, c.getRGB());
                else
                    CousinWare.INSTANCE.fontRenderer.drawStringWithShadow("Server is not responding " + timeNotRespond + "s", (sr.getScaledWidth() - CousinWare.INSTANCE.fontRenderer.getStringWidth("Server is not responding " + timeNotRespond + "s")) / 2, 15, c.getRGB());

            }

        }


    }

    @Listener
    public void onUpdate(PacketEvent.Receive event) {
            counter = 0;


    }
}
