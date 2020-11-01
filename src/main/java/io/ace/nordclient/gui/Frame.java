package io.ace.nordclient.gui;

import io.ace.nordclient.gui.components.Button;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.utilz.FontRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class Frame
{
    public ArrayList<Component> components;
    public Hack.Category category;
    private boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    private int height;
    //ClickGuiHack hack = ((ClickGuiHack) HackManager.getHackByName("ClickGuiModule"));
    public Frame(final Hack.Category cat) {
        this.components = new ArrayList<Component>();
        this.category = cat;
        this.width = 95;
        this.x = 5;
        this.y = 5;
        this.barHeight = 16;
        this.dragX = 0;
        this.open = true;
        this.isDragging = false;
        int tY = this.barHeight;
        for (final Hack hack : HackManager.getHacksInCategory(cat)) {
            final Button hackButton = new Button(hack, this, tY);
            this.components.add(hackButton);
            tY += 16;
        }
        this.refresh();
    }

    public ArrayList<Component> getComponents() {
        return this.components;
    }

    public void setX(final int newX) {
        this.x = newX;
    }

    public void setY(final int newY) {
        this.y = newY;
    }

    public void setDrag(final boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(final boolean open) {
        this.open = open;
    }

    public void renderFrame(final FontRenderer fontRenderer) {
        Color c = new Color(29, 37, 48, 255);
        Color click = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(), ClickGuiHack.blue.getValInt(), 255);

        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, c.getRGB());
        //final Color outline = new Color(10, 10, 10, 200);
        //Gui.drawRect(this.x - 2, this.y - 2, this.x + this.width + 2, this.y, outline.getRGB());
        //Gui.drawRect(this.x - 2, this.y, this.x, this.y + this.height + 2, outline.getRGB());
        //Gui.drawRect(this.x, this.y + this.height, this.x + this.width + 2, this.y + this.height + 2, outline.getRGB());
        //Gui.drawRect(this.x + this.width, this.y, this.x + this.width + 2, this.y + this.height, outline.getRGB());
        Gui.drawRect(this.x, this.y + 17, this.x + 80 + 15,  this.y + 15, click.getRGB() );
        Minecraft mc = Minecraft.getMinecraft();

        //FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValue(), this.category.name(), this.x + 2, this.y + 3, -1);
        if (!Core.customFont.getValBoolean()) FontRenderUtil.drawCenteredStringWithShadow(this.category.name(), (float) (this.x + 47.5), this.y + 3, -1);
        else FontRenderUtil.drawCenteredStringWithShadowCustom(this.category.name(), (float) (this.x + 47.5), this.y + 3, -1);
        if (this.open && !this.components.isEmpty()) {
            for (final Component component : this.components) {
                component.renderComponent();
            }
        }
    }

    public void refresh() {
        int off = this.barHeight;
        for (final Component comp : this.components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
        this.height = off;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }

    public boolean isWithinHeader(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width + 15 && y >= this.y && y <= this.y + this.barHeight;
    }
}