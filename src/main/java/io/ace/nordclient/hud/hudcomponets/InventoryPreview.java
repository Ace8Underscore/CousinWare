package io.ace.nordclient.hud.hudcomponets;

import io.ace.nordclient.hacks.client.ClickGuiHudHack;
import io.ace.nordclient.hud.Hud;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class InventoryPreview extends Hud {

    public InventoryPreview() {
        super("InventoryPreview", 288, 900);
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
        ScaledResolution sr = new ScaledResolution(mc);
        return x >= this.getX()  - 60 && x <= this.getX() +  60 && y >= this.getY() - 60 && y <= this.getY() + 60;
        //return x >= ((sr.getScaledWidth() - ((this.getX() - 1000) * -1))) - 60 && x <= ((sr.getScaledWidth() - ((this.getX() - 1000) * -1))) +  60 && y >= ((sr.getScaledHeight() - this.getY())) - 60 && y <= ((sr.getScaledHeight() - this.getY())) + 60;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        ScaledResolution sr = new ScaledResolution(mc);
        if (mc.world == null || mc.player == null)
            return;
        if (Mouse.isButtonDown(0)) mouseClicked(Mouse.getX(), Mouse.getY(), 0);
        final NonNullList<ItemStack> items = (NonNullList<ItemStack>)mc.player.inventory.mainInventory;
        itemrender(items, ((sr.getScaledWidth() - ((this.getX() - 1000) * -1)) / 2) , ((sr.getScaledHeight() - this.getY() / 2)));
        //Gui.drawRect(this.getX()  - 60, this.getY() + 60,  this.getX() + 60, this.getY() - 60, 8421504); ((sr.getScaledHeight() - ((this.getY() - 500))) / 2));
        //Command.sendClientSideMessage(String.valueOf(((sr.getScaledWidth() - this.getX()) / 2)));

    }

    private static void preitemrender() {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
    }

    private static void postitemrender() {
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


    private void itemrender(final NonNullList<ItemStack> items, final int x, final int y) {
        for (int size = items.size(), item = 9; item < size; ++item) {
            final int slotx = x + 1 + item % 9 * 18;
            final int sloty = y + 1 + (item / 9 - 1) * 18;
            preitemrender();
            mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)items.get(item), slotx, sloty);
            mc.getRenderItem().renderItemOverlays(mc.fontRenderer, (ItemStack)items.get(item), slotx, sloty);
            postitemrender();
        }
    }
}
