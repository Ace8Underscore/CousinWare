package io.ace.nordclient.gui;

import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI2 extends GuiScreen
{
    public static ArrayList<io.ace.nordclient.gui.Frame> frames;
    public static int color;


    public ClickGUI2() {
        ClickGUI2.frames = new ArrayList<io.ace.nordclient.gui.Frame>();
        int frameX = 5;
        for (final Hack.Category category : Hack.Category.values()) {
            final io.ace.nordclient.gui.Frame frame = new io.ace.nordclient.gui.Frame(category);
            frame.setX(frameX);
            ClickGUI2.frames.add(frame);
            frameX += frame.getWidth() + 10;
        }

    }

    public void initGui() {
    }
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        ClickGUI2.color = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).getRGB();
        for (final io.ace.nordclient.gui.Frame frame : ClickGUI2.frames) {
            frame.renderFrame(this.fontRenderer);
            frame.updatePosition(mouseX, mouseY);
            for (final Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }

        }




    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final io.ace.nordclient.gui.Frame frame : ClickGUI2.frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final io.ace.nordclient.gui.Frame frame : ClickGUI2.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final io.ace.nordclient.gui.Frame frame : ClickGUI2.frames) {
            frame.setDrag(false);
        }
        for (final Frame frame : ClickGUI2.frames) {
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        ClickGUI2.color = -1;
    }
}