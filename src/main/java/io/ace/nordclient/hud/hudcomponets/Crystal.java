package io.ace.nordclient.hud.hudcomponets;

import io.ace.nordclient.hacks.client.ClickGuiHudHack;
import io.ace.nordclient.hud.Hud;
import io.ace.nordclient.utilz.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * @author Ace________/Ace_#1233
 */

public class Crystal extends Hud {

    public Crystal() {
        super("Crystal", 540, 953);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (button == 0) {
            setHeld(this.name);
            if (isMouseOnButton(mouseX, mouseY) && ClickGuiHudHack.canMove) {
                setX(mouseX);
                setY(mouseY);

            }
        }

        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            //this.open = !this.open;
            //this.parent.refresh();
        }

    }


    public boolean isMouseOnButton(final int x, final int y) {
        return x >= this.getX() - 20 && x <= this.getX() +  20 && y >= this.getY() - 20 && y <= this.getY() + 20;
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (Mouse.isButtonDown(0)) mouseClicked(Mouse.getX(), Mouse.getY(), 0);
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        ItemStack xp = new ItemStack(Items.END_CRYSTAL, 1);
        GL11.glPushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        mc.getRenderItem().renderItemAndEffectIntoGUI(xp, (this.getX() / 2) - 5, ((this.getY() / -2) + 530));
        int grabGapCount = InventoryUtil.getItems(Items.END_CRYSTAL);
        mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, xp, (this.getX() / 2) - 5, ((this.getY() / -2) + 530),ClickGuiHudHack.colorchoice() + String.valueOf(grabGapCount));
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
    }

}