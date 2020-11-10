package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.utilz.NordTessellator;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.RenderUtilz;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

/**
 * @author Ace________/Ace_#1233
 */

public class ItemESP extends Hack {

    Setting range;
    Setting r;
    Setting g;
    Setting b;
    Setting rainbow;

    public ItemESP() {
        super("ItemESP", Category.RENDER, 1);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 100, 0, 400, false, "ItemESPRange"));
        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 1, 0, 255, false, "ItemESPRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 1, 0, 255, false, "ItemESPGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 1, 0, 255, false, "ItemESPBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "ItemESPRainbow"));
    }

    @Listener
    public void onWorldRender(RenderEvent event) {

        for (Entity item : mc.world.getLoadedEntityList()) {
            if (item instanceof EntityItem) {
                if (mc.player.getDistance(item) <= range.getValDouble()) {
                     NordTessellator.drawBoundingBoxItem(item.posX, item.posY, item.posZ, 1, r.getValInt(), g.getValInt(), b.getValInt(), 255);
                    if (!Core.customFont.getValBoolean()) RenderUtilz.drawText((float) item.posX - (float) .5, (float) item.posY + (float) .5, (float) item.posZ - (float) .5, ChatFormatting.WHITE + ((EntityItem) item).getItem().getDisplayName() + " " + ((EntityItem) item).getItem().getCount() + "x");
                    else RenderUtilz.drawTextCustom((float) item.posX - (float) .5, (float) item.posY + (float) .5, (float) item.posZ - (float) .5, ChatFormatting.WHITE + ((EntityItem) item).getItem().getDisplayName() + " " + ((EntityItem) item).getItem().getCount() + "x");

                }
            }
        }
    }


    public void onUpdate() {
        if (rainbow.getValBoolean()) {
            RainbowUtil.settingRainbow(r, g, b);
        }
    }
}
