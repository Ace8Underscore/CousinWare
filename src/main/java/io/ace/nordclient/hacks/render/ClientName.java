package io.ace.nordclient.hacks.render;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class ClientName extends Hack {

    public ClientName() {
        super("ClientName", Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderGameOverlayEvent.Text event) {
        Color c = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(), ClickGuiHack.blue.getValInt(), 255);

        mc.fontRenderer.drawStringWithShadow(NordClient.NAME + " " + NordClient.VERSION, 1, 1, c.getRGB());
    }
    //
}
