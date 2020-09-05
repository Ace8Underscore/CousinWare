package io.ace.nordclient.gui;

import net.minecraft.client.Minecraft;

public class Component
{

    protected Minecraft mc = Minecraft.getMinecraft();
    public void renderComponent() {
    }
    
    public void updateComponent(final int mouseX, final int mouseY) {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public int getParentHeight() {
        return 0;
    }
    
    public void keyTyped(final char typedChar, final int key) {
    }
    
    public void setOff(final int newOff) {
    }
    
    public int getHeight() {
        return 0;
    }
}