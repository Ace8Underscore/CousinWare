package io.ace.nordclient.hud;

import io.ace.nordclient.hacks.client.ClickGuiHack;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGuiHUD extends GuiScreen
{
    public static ArrayList<HudFrame> frames;
    public static int color;


    public ClickGuiHUD() {
        ClickGuiHUD.frames = new ArrayList<HudFrame>();
        int frameX = 5;
        for (final Hud.Category category : Hud.Category.values()) {
            final HudFrame frame = new HudFrame(category);
            frame.setX(frameX);
            ClickGuiHUD.frames.add(frame);
            frameX += frame.getWidth() + 10;
        }

    }

    public void initGui() {
    }
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        ClickGuiHUD.color = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).getRGB();
        for (final HudFrame frame : ClickGuiHUD.frames) {
            frame.renderFrame(this.fontRenderer);
            frame.updatePosition(mouseX, mouseY);
            for (final HudComponent comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }

        }


    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final HudFrame frame : ClickGuiHUD.frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final HudComponent component : frame.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final HudFrame frame : ClickGuiHUD.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final HudComponent component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final HudFrame frame : ClickGuiHUD.frames) {
            frame.setDrag(false);
        }
        for (final HudFrame frame : ClickGuiHUD.frames) {
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final HudComponent component : frame.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        ClickGuiHUD.color = -1;
    }
}