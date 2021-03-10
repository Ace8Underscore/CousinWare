package io.ace.nordclient.managers;

import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hud.Hud;
import io.ace.nordclient.utilz.NordTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Ace________/Ace_#1233
 */

public class HudManager {
    public static ArrayList<Hud> hudElement;

    public static void addHud(Hud h){
        hudElement.add(h);
    }

    public static ArrayList<Hud> getHuds() {
        return hudElement;
    }

    public static Hud getHudByName(String name){
        return getHuds().stream().filter(hm->hm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static void onUpdate() {
        hudElement.stream().filter(Hud::isEnabled).forEach(Hud::onUpdate);
    }

    public static ArrayList<Hud> getHudsInCategory(Hud.Category c){
        return (ArrayList<Hud>) getHuds().stream().filter(h -> h.getCategory().equals(c)).collect(Collectors.toList());
    }


    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft.getMinecraft().profiler.startSection("nordClient");

        Minecraft.getMinecraft().profiler.startSection("setup");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();

        GlStateManager.glLineWidth(1f);
        Vec3d renderPos = NordTessellator.getInterpolatedPos(Minecraft.getMinecraft().player, event.getPartialTicks());

        RenderEvent e = new RenderEvent(NordTessellator.INSTANCE, renderPos, event.getPartialTicks());
        e.resetTranslation();
        Minecraft.getMinecraft().profiler.endSection();

        hudElement.stream().filter(Hud::isEnabled).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().profiler.endSection();
        });

        Minecraft.getMinecraft().profiler.startSection("release");
        GlStateManager.glLineWidth(1f);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        NordTessellator.releaseGL();
        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();
    }

}
