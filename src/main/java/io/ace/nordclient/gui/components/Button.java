package io.ace.nordclient.gui.components;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.gui.Component;
import io.ace.nordclient.gui.Frame;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;

import java.awt.*;
import java.util.ArrayList;

public class Button extends Component
{
    public Hack hack;
    public Frame parent;
    public int offset;
    private boolean isHovered;
    private ArrayList<Component> subcomponents;
    public boolean open;
    private int height;

    public Button(final Hack hack, final Frame parent, final int offset) {
        this.hack = hack;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<Component>();
        this.open = false;
        this.height = 16;
        int opY = offset + 16;
        if (NordClient.INSTANCE.settingsManager.getSettingsByMod(hack) != null && !NordClient.INSTANCE.settingsManager.getSettingsByMod(hack).isEmpty()) {
            for (final Setting s : NordClient.INSTANCE.settingsManager.getSettingsByMod(hack)) {
                    if (s.isCombo()) {
                        this.subcomponents.add(new ModeButton((Setting)s, this, hack, opY));
                        opY += 16;
                        continue;
                    }
                   /* case STRING: {
                        this.subcomponents.add(new StringButton((Setting.s)s, this, opY));
                        opY += 16;
                        continue;
                    } */
                    else if (s.isCheck()) {
                        this.subcomponents.add(new CheckBox((Setting)s, this, opY));
                        opY += 16;
                        continue;
                    }
                    else if (s.isSlider()) {
                        this.subcomponents.add(new DoubleSlider((Setting)s, this, opY));
                        opY += 16;
                        continue;
                    }
                  /*  case INT: {
                        this.subcomponents.add(new IntSlider((Setting)s, this, opY));
                        opY += 16;
                        continue;
                    } */
                }
            }
        this.subcomponents.add(new Keybind(this, opY));

    }


    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 16;
        for (final Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 16;
        }
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset + 1, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 16 + this.offset, this.isHovered ? (this.hack.isEnabled() ? new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).getRGB() : new Color(30, 30, 30, 150).darker().darker().getRGB()) : (this.hack.isEnabled() ? new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).getRGB() : new Color(30, 30, 30, 150).getRGB()));
        Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + this.offset + 1, new Color(30, 30, 30, 150).getRGB());
        //FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValInt(), this.mod.getName(), this.parent.getX() + 2, this.parent.getY() + this.offset + 2 + 2, -1);
        mc.fontRenderer.drawStringWithShadow(this.hack.getName(), this.parent.getX() + 2, this.parent.getY() + this.offset + 2 + 2, -1);
        if (this.subcomponents.size() > 1) {
            //FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValInt(), this.open ? "-" : "+", this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 2 + 2, -1);
            mc.fontRenderer.drawStringWithShadow(this.open ? "-" : "+", this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 2 + 2, -1);
        }
        if (this.open && !this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.renderComponent();
            }
        }
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
            mc.fontRenderer.drawStringWithShadow(this.hack.getDescription(), mouseX + 12, mouseY + 4, -1);
        }
        if (!this.subcomponents.isEmpty()) {
            for (final Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.hack.toggle();
            if (ClickGuiHack.noise.getValBoolean()) {
                Minecraft.getMinecraft().player.playSound(SoundEvents.UI_BUTTON_CLICK, 2f, 1f);
            }
        }
        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (final Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(final char typedChar, final int key) {
        for (final Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 16 + this.offset;
    }
}