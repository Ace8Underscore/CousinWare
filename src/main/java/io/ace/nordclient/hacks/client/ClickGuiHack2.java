package io.ace.nordclient.hacks.client;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;

public class ClickGuiHack2 extends Hack {

    public ClickGuiHack2() {
        super("ClickGui2", Category.CLIENT, 1);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(CousinWare.INSTANCE.cousinWareGui);
        try {
            if (CousinWare.INSTANCE.fontRenderer.getFontName().equalsIgnoreCase("null")) {
                CousinWare.INSTANCE.fontRenderer.setFontName("Arial");
                CousinWare.INSTANCE.fontRenderer.setFontSize(18);
                CousinWare.INSTANCE.configUtils.saveFont();
                CousinWare.INSTANCE.configUtils.loadFont();
            }
        } catch (Exception ignored) {

//
        }
        disable();
    }
}
