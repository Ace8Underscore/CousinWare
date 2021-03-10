package io.ace.nordclient.utilz;

import io.ace.nordclient.CousinWare;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class RenderUtilz {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void drawText(final float x, final float y, final float z, final String text) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(x + 0.5f, y + 0.5f, z + 0.5f, (EntityPlayer)mc.player, 1.0f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(mc.fontRenderer.getStringWidth(text) / 2.0), 0.0, 0.0);
        mc.fontRenderer.drawStringWithShadow(text, 0.0f, 0.0f, -5592406);
        GlStateManager.popMatrix();
    }
    public static void drawTextCustom(final float x, final float y, final float z, final String text) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(x + 0.5f, y + 0.5f, z + 0.5f, (EntityPlayer)mc.player, 1.0f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(CousinWare.INSTANCE.fontRenderer.getStringWidth(text) / 2.0), 0.0, 0.0);
        CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(text, 0.0f, 0.0f, -5592406);
        GlStateManager.popMatrix();
    }

    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance(x, y, z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }

    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.player.rotationPitch, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }


}
