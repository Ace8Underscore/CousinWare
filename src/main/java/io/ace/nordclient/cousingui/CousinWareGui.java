package io.ace.nordclient.cousingui;

import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CousinWareGui extends GuiScreen {

    public static ArrayList<io.ace.nordclient.cousingui.Frame> frames;
    public static int color;

    public CousinWareGui() {
        CousinWareGui.frames = new ArrayList<io.ace.nordclient.cousingui.Frame>();
        int frameX = 5;
        for (final Hack.Category category : Hack.Category.values()) {
            final io.ace.nordclient.cousingui.Frame frame = new io.ace.nordclient.cousingui.Frame(category);
            frame.setX(frameX);
            CousinWareGui.frames.add(frame);
            frameX += frame.getWidth() + 10;
        }

    }

    public void initGui() {
    }
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        CousinWareGui.color = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).getRGB();
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            frame.renderFrame(this.fontRenderer);
            frame.updatePosition(mouseX, mouseY);
            for (final io.ace.nordclient.cousingui.Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
                if (frame.category.equals(Hack.Category.CLIENT)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(50);
                }
                if (frame.category.equals(Hack.Category.COMBAT)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(75);
                }
                if (frame.category.equals(Hack.Category.PLAYER)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(100);
                }
                if (frame.category.equals(Hack.Category.MOVEMENT)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(125);
                }
                if (frame.category.equals(Hack.Category.MISC)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(150);
                }
                if (frame.category.equals(Hack.Category.EXPLOIT)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(175);
                }
                if (frame.category.equals(Hack.Category.PLAYER)) {
                    frame.setDrag(false);
                    frame.setX(100);
                    frame.setY(200);
                }

            }
        }

    }


    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final io.ace.nordclient.cousingui.Component component : frame.getComponents()) {
                    component.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final io.ace.nordclient.cousingui.Component component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
            frame.setDrag(false);
        }
        for (final io.ace.nordclient.cousingui.Frame frame : CousinWareGui.frames) {
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
        CousinWareGui.color = -1;
    }
}



