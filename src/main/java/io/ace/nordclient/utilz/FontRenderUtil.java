package io.ace.nordclient.utilz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class FontRenderUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final ScaledResolution sr = new ScaledResolution(mc);




    public static float drawCenteredStringWithShadow(String text, float x, float y, int color) {
        return mc.fontRenderer.drawStringWithShadow(text, x - mc.fontRenderer.getStringWidth(text) / 2, y, color);
    }

    public static float drawCenteredString(String text, float x, float y, int color) {
        return mc.fontRenderer.drawString(text, (int) x - mc.fontRenderer.getStringWidth(text) / 2, (int) y, color);
    }

    public static float drawLeftStringWithShadow(String text, float x, float y, int color) {
        return mc.fontRenderer.drawStringWithShadow(text, x - mc.fontRenderer.getStringWidth(text), y, color);
    }

    public static float drawLeftString(String text, float x, float y, int color) {
        return mc.fontRenderer.drawString(text, mc.fontRenderer.getStringWidth(text) - (int) x, (int) y, color);
    }

}
