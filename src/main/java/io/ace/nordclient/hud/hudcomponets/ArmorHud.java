package io.ace.nordclient.hud.hudcomponets;

import io.ace.nordclient.hacks.client.ClickGuiHudHack;
import io.ace.nordclient.hud.Hud;
import io.ace.nordclient.utilz.ColorHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

/**
 * @author Ace________/Ace_#1233
 */

public class ArmorHud extends Hud {

    public ArmorHud() {
        super("ArmorHud", 294, 982);
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

        }

    }

    public boolean isMouseOnButton(final int x, final int y) {
        return x >= this.getX() - 60 && x <= this.getX() + 140 && y >= this.getY() - 40 && y <= this.getY() + 30;
    }



    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (Mouse.isButtonDown(0)) mouseClicked(Mouse.getX(), Mouse.getY(), 0);

        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
            ScaledResolution resolution = new ScaledResolution(mc);
            GlStateManager.enableTexture2D();
            int i = resolution.getScaledWidth() / 2;
            int iteration = 0;
            int y = resolution.getScaledHeight() - (this.getY() / 2) - (mc.player.isInWater() ? 10 : 0);
            for (ItemStack is : mc.player.inventory.armorInventory) {
                ++iteration;
                if (is.isEmpty()) continue;
                int x = i - ((this.getX() / -2) + 600) + (9 - iteration) * 20 + 2;
                GlStateManager.enableDepth();
                itemRender.zLevel = 200.0f;
                itemRender.renderItemAndEffectIntoGUI(is, x, y);
                itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, is, x, y, "");
                itemRender.zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                String s = is.getCount() > 1 ? is.getCount() + "" : "";
                mc.fontRenderer.drawStringWithShadow(s, x + 19 - 2 - mc.fontRenderer.getStringWidth(s), y + 9, 16777215);
                float green = ((float) is.getMaxDamage() - (float) is.getItemDamage()) / (float) is.getMaxDamage();
                float red = 1.0f - green;
                int dmg = 100 - (int) (red * 100.0f);
                mc.fontRenderer.drawStringWithShadow(dmg + "", x + 8 - mc.fontRenderer.getStringWidth(dmg + "") / 2, y - 11, ColorHolder.toHex((int) (red * 255.0f), (int) (green * 255.0f), 0));
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }


