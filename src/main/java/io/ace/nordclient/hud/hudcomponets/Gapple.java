package io.ace.nordclient.hud.hudcomponets;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.client.ClickGuiHudHack;
import io.ace.nordclient.hud.Hud;
import io.ace.nordclient.utilz.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Gapple extends Hud {

    public Gapple() {
        super("Gapple", 543, 912);
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
        ItemStack gapple = new ItemStack(Items.GOLDEN_APPLE, 1);
        gapple.addEnchantment(Enchantments.AQUA_AFFINITY, 1);
        int grabGapCount = InventoryUtil.getItems(Items.GOLDEN_APPLE);
        GL11.glPushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        mc.getRenderItem().renderItemAndEffectIntoGUI(gapple, (this.getX() / 2) - 5, ((this.getY() / -2) + 530));
        mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, gapple, (this.getX() / 2) - 5, ((this.getY() / -2) + 530),ClickGuiHudHack.colorchoice() + String.valueOf(grabGapCount));
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
    }

}