package io.ace.nordclient.gui.components;

import io.ace.nordclient.gui.Component;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.utilz.FontRenderUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class CheckBox extends Component
{
    private boolean hovered;
    private Setting op;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    
    public CheckBox(final Setting option, final Button button, final int offset) {
        this.op = option;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 1, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 16, this.hovered ? (this.op.getValBoolean() ?
                new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).getRGB() :
                new Color(30, 30, 30, 150).darker().darker().getRGB()) : (this.op.getValBoolean() ?
                new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).darker().getRGB() :
                new Color(30, 30, 30, 150).getRGB()));
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 1, new Color(30, 30, 30, 150).getRGB());
        //FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValInt(), this.op.getName(), this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 4, -1);

        FontRenderUtil.drawCenteredStringWithShadow(this.op.getDisplayName(), this.parent.parent.getX() + 40, this.parent.parent.getY() + this.offset + 4, -1);

    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
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
            this.op.setValBoolean(!this.op.getValBoolean());
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 16;
    }
}