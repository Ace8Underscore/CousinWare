package io.ace.nordclient.gui.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.gui.Component;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.utilz.FontRenderUtil;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleSlider extends Component
{
    private boolean hovered;
    private Setting set;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging;
    private double renderWidth;
    
    public DoubleSlider(final Setting value, final Button button, final int offset) {
        this.dragging = false;
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 1, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 16, this.hovered ? new Color(30, 30, 30, 150).darker().darker().getRGB() : new Color(30, 30, 30, 150).getRGB());
        final int drag = (int)(this.set.getValDouble() / this.set.getMax() * this.parent.parent.getWidth());
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 1, this.parent.parent.getX() + (int)this.renderWidth, this.parent.parent.getY() + this.offset + 16, this.hovered ?new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).getRGB() : new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(),ClickGuiHack.blue.getValInt(), ClickGuiHack.alpha.getValInt()).darker().getRGB());
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 1, new Color(30, 30, 30, 150).getRGB());
        //FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValue(), this.set.getName() + " " + ChatFormatting.GRAY + this.set.getValue(), (int)(this.parent.parent.getX() + 2), this.parent.parent.getY() + this.offset + 4, -1);
        mc.fontRenderer.drawStringWithShadow(this.set.getDisplayName(), (int)(this.parent.parent.getX() + 2), this.parent.parent.getY() + this.offset + 4, -1);
        FontRenderUtil.drawLeftStringWithShadow(ChatFormatting.GRAY + String.valueOf(this.set.getValDouble()), (int)(this.parent.parent.getX() + 80), this.parent.parent.getY() + this.offset + 4, -1);
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = (this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY));
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        final double diff = Math.min(88, Math.max(0, mouseX - this.x));
        final double min = this.set.getMin();
        final double max = this.set.getMax();
        this.renderWidth = 80.0 * (this.set.getValDouble() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0) {
                this.set.setValDouble(this.set.getMin());
            }
            else {
                final double newValue = roundToPlace(diff / 88.0 * (max - min) + min, 2);
                this.set.setValDouble(newValue);
            }
        }
    }
    
    private static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
        if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.dragging = false;
    }
    
    public boolean isMouseOnButtonD(final int x, final int y) {
        return x > this.x && x < this.x + (this.parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 16;
    }
    
    public boolean isMouseOnButtonI(final int x, final int y) {
        return x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 16;
    }
}