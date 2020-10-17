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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;

public class Totem extends Hud {

    public Totem() {
        super("Totem", 400, 400);
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
    public void onRender(RenderGameOverlayEvent.Text event) {
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();

        ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING, 1);

        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        GlStateManager.enableBlend();
        int grabTotemCount = InventoryUtil.getItems(Items.TOTEM_OF_UNDYING);
        GlStateManager.pushAttrib();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableDepth();
        mc.getRenderItem().renderItemAndEffectIntoGUI(totem, (this.getX() / 2) - 5, ((this.getY() / -2) + 530));
        itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, totem, (this.getX() / 2) - 5, ((this.getY() / -2) + 530), ClickGuiHudHack.colorchoice() + String.valueOf(grabTotemCount));
        GlStateManager.enableDepth();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.disableBlend();

        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }

}