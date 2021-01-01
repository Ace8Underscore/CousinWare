package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.hacks.client.Core;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

/**
 * @author Ace________/Ace_#1233
 */

public class ClientName extends Hack {

    public ClientName() {
        super("Watermark", Category.RENDER, 586816);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderGameOverlayEvent.Text event) {
        Color c = new Color(37, 113, 32, 255);

       if (!Core.customFont.getValBoolean()) mc.fontRenderer.drawStringWithShadow("CousinWare" + " Nigga Edition", 1, 1, c.getRGB());
        else  CousinWare.INSTANCE.fontRenderer.drawStringWithShadow("CousinWare" + " Nigga Edition", 1, 1, c.getRGB());
    }
    //
}
