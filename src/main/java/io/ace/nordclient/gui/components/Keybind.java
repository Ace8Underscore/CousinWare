package io.ace.nordclient.gui.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.gui.Component;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.hacks.client.Core;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Keybind extends Component
{
    private boolean hovered;
    private boolean binding;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    
    public Keybind(final Button button, final int offset) {
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 1, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, this.hovered ? new Color(29, 37, 48, ClickGuiHack.alpha.getValInt()).darker().getRGB() : new Color(29, 37, 48, ClickGuiHack.alpha.getValInt()).getRGB());
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 1, new Color(29, 37, 48, ClickGuiHack.alpha.getValInt()).getRGB());
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 15, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 16, new Color(29, 37, 48, ClickGuiHack.alpha.getValInt()).getRGB());
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 1, this.parent.parent.getY() + this.offset + 16, new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(), ClickGuiHack.blue.getValInt(), 255).getRGB());

        //FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValue(),this.binding ? "Press a key..." : ("Key: " + ChatFormatting.GRAY + Keyboard.getKeyName(this.parent.mod.getBind())), this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 4, -1);
        if (!Core.customFont.getValBoolean()) mc.fontRenderer.drawStringWithShadow(this.binding ? "Press a key..." : ("Key: " + ChatFormatting.GRAY + Keyboard.getKeyName(this.parent.hack.getBind())), this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 4, -1);
        else CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(this.binding ? "Press a key..." : ("Key: " + ChatFormatting.GRAY + Keyboard.getKeyName(this.parent.hack.getBind())), this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 4, -1);
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX() - 10;
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.binding = !this.binding;
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int key) {
        if (this.binding) {
            if (key == 211) {
                this.parent.hack.setBind(0);
            }
            else {
                this.parent.hack.setBind(key);
            }
            this.binding = false;
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 88 + 15 && y > this.y && y < this.y + 16;
    }
}