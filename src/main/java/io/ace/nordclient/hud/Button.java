package io.ace.nordclient.hud;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.hacks.client.Core;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;

import java.awt.*;
import java.util.ArrayList;

public class Button extends HudComponent {
    public Hud hud;
    public HudFrame parent;
    public int offset;
    private boolean isHovered;
    private ArrayList<HudComponent> subcomponents;
    public boolean open;
    private int height;
    public static Boolean opened;

    public Button(final Hud hud, final HudFrame parent, final int offset) {
        this.hud = hud;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<HudComponent>();
        this.open = false;
        this.height = 16;
        int opY = offset + 16;


    }




    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 16;
        for (final HudComponent comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 16;
        }
    }

    @Override
    public void renderComponent() {

        Color c = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(), ClickGuiHack.blue.getValInt(), 255);
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset + 1, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 16 + this.offset, this.isHovered ? (this.hud.isEnabled() ? new Color(29, 37,48, 255).getRGB() : new Color(29, 37, 48, 255).darker().darker().getRGB()) : (this.hud.isEnabled() ? new Color(29, 37,48, 255).getRGB() : new Color(29, 37, 48, 255).getRGB()));
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + this.offset + 1, new Color(29, 37, 48, 255).getRGB());
        //FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValInt(), this.mod.getName(), this.parent.getX() + 2, this.parent.getY() + this.offset + 2 + 2, -1);

        if (!Core.customFont.getValBoolean()) {
            if (this.hud.isEnabled())
                mc.fontRenderer.drawStringWithShadow(this.hud.getName(), this.parent.getX() + 2, this.parent.getY() + this.offset + 2 + 2, c.getRGB());
            else
                mc.fontRenderer.drawStringWithShadow(this.hud.getName(), this.parent.getX() + 2, this.parent.getY() + this.offset + 2 + 2, -1);
        } else {
            if (this.hud.isEnabled())
                CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(this.hud.getName(), this.parent.getX() + 2, this.parent.getY() + this.offset + 2 + 2, c.getRGB());
            else
                CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(this.hud.getName(), this.parent.getX() + 2, this.parent.getY() + this.offset + 2 + 2, -1);

        }

        if (this.subcomponents.size() > 1) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1, 1, 1);
            GlStateManager.translate(0, 0, 4);
            GlStateManager.glLineWidth(100);
            GlStateManager.popMatrix();
            //FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValInt(), this.open ? "-" : "+", this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 2 + 2, -1);
            if (!Core.customFont.getValBoolean()) mc.fontRenderer.drawStringWithShadow(this.open ? "v" : ">", this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 2 + 2, -1);
            else CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(this.open ? "v" : ">", this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 2 + 2, -1);
        }
        if (this.open && !this.subcomponents.isEmpty()) {
            for (final HudComponent comp : this.subcomponents) {
                comp.renderComponent();
                //Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset + 1, this.parent.getX() + 1, this.parent.getY() + this.offset + 16, new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(), ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).getRGB());
            }
        }
        if (this.open) opened = true;
        else opened = false;
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return 16 * (this.subcomponents.size() + 1);
        }
        return 16;
    }

    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.isHovered = this.isMouseOnButton(mouseX, mouseY);
        if (this.isHovered && ClickGuiHack.descriptions.getValBoolean()) {

        }
        if (!this.subcomponents.isEmpty()) {
            for (final HudComponent comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.hud.toggle();
            if (ClickGuiHack.noise.getValBoolean()) {
                Minecraft.getMinecraft().player.playSound(SoundEvents.UI_BUTTON_CLICK, 2f, 1f);
            }
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (final HudComponent comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        for (final HudComponent comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(final char typedChar, final int key) {
        for (final HudComponent comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 16 + this.offset;
    }
}