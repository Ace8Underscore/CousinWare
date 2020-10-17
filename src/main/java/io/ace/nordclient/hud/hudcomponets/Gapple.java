package io.ace.nordclient.hud.hudcomponets;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.hacks.client.ClickGuiHudHack;
import io.ace.nordclient.hud.Hud;
import io.ace.nordclient.utilz.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
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

import java.awt.*;

public class Gapple extends Hud {

    public Gapple() {
        super("Gapple", 500, 450);
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

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (Mouse.isButtonDown(0)) mouseClicked(Mouse.getX(), Mouse.getY(), 0);


    }
    public boolean isMouseOnButton(final int x, final int y) {
        return x >= this.getX() - 20 && x <= this.getX() +  20 && y >= this.getY() - 20 && y <= this.getY() + 20;
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Chat event) {
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();

        ItemStack gapple = new ItemStack(Items.GOLDEN_APPLE, 1);
        gapple.addEnchantment(Enchantments.AQUA_AFFINITY, 1);


        GL11.glPushMatrix();
        GL11.glDepthMask((true));
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        gapple.addEnchantment(Enchantments.AQUA_AFFINITY, 1);
        mc.getRenderItem().renderItemAndEffectIntoGUI(gapple, (this.getX() / 2) - 5, ((this.getY() / -2) + 530));
        int grabGapCount = InventoryUtil.getItems(Items.GOLDEN_APPLE);
        mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, gapple, (this.getX() / 2) - 5, ((this.getY() / -2) + 530),ClickGuiHudHack.colorchoice() + String.valueOf(grabGapCount));
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }

}