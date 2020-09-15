package io.ace.nordclient.gui;

import io.ace.nordclient.guinew.Snow;
import io.ace.nordclient.guinew.Util;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class DrawSnow extends GuiScreen {

    private int snowLimit;
    private boolean addingSnow;
    private int checkSize;


    public DrawSnow() {
        if (ClickGuiHack.snow.getValBoolean()) {
            Snow.render.clear();
            ScaledResolution sr = new ScaledResolution(mc);
            Snow.render.add(new Snow(Util.random(0, sr.getScaledWidth()), 0));
            new Snow(0, 0);
        }
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (ClickGuiHack.snow.getValBoolean()) {
            ScaledResolution sr = new ScaledResolution(mc);
            if (addingSnow) {
                Snow snows = new Snow(Util.random(0, sr.getScaledWidth()), Util.random(-100, -200));
                Snow.render.add(snows);
                snowLimit += 1;
                addingSnow = false;
            }
            if (Snow.render.size() > 1) {
                checkSize = Snow.render.size() - 2;
            }
            for (Snow snow : Snow.getRender()) {
                Snow last = Snow.render.get(checkSize);
                Snow first = Snow.getRender().get(0);
                if (snow != first) {
                    snow.drawSnow();
                }
                snow.setY((int) (snow.getY() + 0.5));
                snow.setX((int) (snow.getX() + Util.random(-1, 2)));
                if (snow.getY() == last.getY()) {
                    last.setY((int) (last.getY() + 10));
                }

                if (snow.getY() > sr.getScaledHeight()) {
                    if (snowLimit < 30) {
                        addingSnow = true;
                    }
                    snow.setX(Util.random(0, sr.getScaledWidth()));
                    snow.setY(0);
                }
            }
        }

    }
}
