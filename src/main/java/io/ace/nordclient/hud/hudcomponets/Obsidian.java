package io.ace.nordclient.hud.hudcomponets;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.client.ClickGuiHudHack;
import io.ace.nordclient.hud.Hud;
import io.ace.nordclient.utilz.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Obsidian extends Hud {

    public Obsidian() {
        super("Obsidian", 496, 953);
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
        ItemStack totem = new ItemStack(Block.getBlockFromItem(Item.getItemFromBlock(Blocks.OBSIDIAN)), 1);
        GL11.glPushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();

        int grabTotemCount = InventoryUtil.getBlocks(Blocks.OBSIDIAN);

        mc.getRenderItem().renderItemAndEffectIntoGUI(totem, (this.getX() / 2) - 5, ((this.getY() / -2) + 530));
        itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, totem, (this.getX() / 2) - 5, ((this.getY() / -2) + 530), ClickGuiHudHack.colorchoice() + String.valueOf(grabTotemCount));
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
    }

}