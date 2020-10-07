package io.ace.nordclient.managers;

import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.hacks.client.Colors;
import io.ace.nordclient.hacks.combat.Surround;
import io.ace.nordclient.hacks.combat.*;
import io.ace.nordclient.hacks.misc.*;
import io.ace.nordclient.hacks.movement.*;
import io.ace.nordclient.hacks.player.*;
import io.ace.nordclient.hacks.render.*;
import io.ace.nordclient.utilz.NordTessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HackManager {
    public static ArrayList<Hack> hacks;
    private static String allHackNames = "Hacks: ";
    private static String officialAllHackNames;

    public HackManager(){
        hacks = new ArrayList<>();
        //addHack(new TestCommand());
        addHack(new io.ace.nordclient.hacks.render.ArrayList());
        addHack(new AutoTotem());
        addHack(new Jesus());
        addHack(new AntiFog());
        addHack(new GoonSquad());
        addHack(new Swing());
        addHack(new Velocity());
        addHack(new LogoutCoords());
        addHack(new AntiVoid());
        //addHack(new FastWeb());
        addHack(new FastXp());
        addHack(new FastSwim());
        addHack(new QuickDrop());
        addHack(new SelfParticle());
        addHack(new ElytraFly());
        addHack(new SkyColor());
        addHack(new ClickGuiHack());
        addHack(new Overlay());
        addHack(new DonkeyAlert());
        addHack(new ReverseStep());
        addHack(new ArmorHud());
        addHack(new ClientName());
        addHack(new AutoOffHand());
        addHack(new DelayedSounds());
        addHack(new BlockHighlight());
        addHack(new ShulkerMod());
        addHack(new ViewModelChanger());
        addHack(new InfiniteChatlength());
        addHack(new FullBright());
        addHack(new SpeedMine());
        addHack(new MultiTask());
        addHack(new StorageESP());
        addHack(new Surround());
        addHack(new Scaffold());
        //addHack(new AutoCrystal());
        //addHack(new Reach());
        addHack(new Colors());
        addHack(new AutoTntMinecart());
        addHack(new AutoBedBombDumb());
        addHack(new MCF());
        addHack(new Spammer());
        addHack(new FriendTab());
        addHack(new Step());
        addHack(new NoInteract());






    }
    public static void addHack(Hack h){
        hacks.add(h);
    }

    public static ArrayList<Hack> getHacks() {
        return hacks;
    }

//help m e plea ssse aa


    public static Hack getHackByName(String name){
        return getHacks().stream().filter(hm->hm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static void onUpdate() {
        hacks.stream().filter(Hack::isEnabled).forEach(Hack::onUpdate);
    }

    public static String getAllHackList() {
        HackManager.getHacks()
                .forEach(hack -> {
                    officialAllHackNames = allHackNames + " " + hack.name;

                });

        return officialAllHackNames;
    }

    public static ArrayList<Hack> getHacksInCategory(Hack.Category c){
        return (ArrayList<Hack>) getHacks().stream().filter(h -> h.getCategory().equals(c)).collect(Collectors.toList());
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

        hacks.stream().filter(Hack::isEnabled).forEach(module -> {
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


    public static void onBind(int key) {
        if (key == 0) return;
        hacks.forEach(hack -> {
            if(hack.getBind() == key){
                hack.toggle();
            }
        });
    }
}
