package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.utilz.FontRenderUtil;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.TpsUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ace________/Ace_#1233
 */

public class Overlay extends Hack {


    Setting x;
    Setting y;
    Setting ping;
    Setting server;
    Setting fps;
    Setting tps;
    Setting packetLoss;
    Setting potion;

    int pingPlayer;
    int tick = 0;
    int packetCounter = 0;
    int delayCount = 0;
    int cc = 0;
    boolean t = false;
    int tick2 = 0;

    public Overlay() {
        super("Overlay", Category.RENDER, 1687568);
        CousinWare.INSTANCE.settingsManager.rSetting(x = new Setting("x", this, 959, 0, 2000, false, "OverlayX"));
        CousinWare.INSTANCE.settingsManager.rSetting(y = new Setting("y", this, 530, 0, 2000, false, "OverlayY"));
        CousinWare.INSTANCE.settingsManager.rSetting(ping = new Setting("Ping", this, true, "OverlayPing"));
        CousinWare.INSTANCE.settingsManager.rSetting(server = new Setting("Server", this, true, "OverlayServer"));
        CousinWare.INSTANCE.settingsManager.rSetting(fps = new Setting("Fps", this, true, "OverlayFps"));
        CousinWare.INSTANCE.settingsManager.rSetting(tps = new Setting("Tps", this, true, "OverlayTps"));
        CousinWare.INSTANCE.settingsManager.rSetting(packetLoss = new Setting("PacketLoss", this, true, "OverlayPacketLoss"));
        CousinWare.INSTANCE.settingsManager.rSetting(potion = new Setting("PotionEffects", this, true, "OverlayPotion"));
//
    }
    @Override
    public void onUpdate() {
        pingPlayer = mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime();
    }

    @SubscribeEvent
    public void onRenderWorld(RenderGameOverlayEvent.Text event) {
        if (packetLoss.getValBoolean()) {
            tick++;
            tick2++;
            if (t) {
                packetCounter += 50;
                if (tick2 > 100) {
                    t = false;
                    tick2 = 0;
                }
            }
        }
        ScaledResolution sr = new ScaledResolution(mc);

        AtomicInteger yOffset = new AtomicInteger();
        String tpsString = "Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0;
        String fpsString = "Fps " + mc.getDebugFPS();
        Color c = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(), ClickGuiHack.blue.getValInt(), 255);
        if (!Core.customFont.getValBoolean()) {
            yOffset.set(0);
            if (potion.getValBoolean()) {
                AtomicInteger finalYOffset = new AtomicInteger(yOffset.get());
                mc.player.getActivePotionEffects().forEach(potionEffect -> {
                    String name = I18n.format(potionEffect.getPotion().getName());
                    DecimalFormat format2 = new DecimalFormat("00");
                    double duration = potionEffect.getDuration() / 19.99f;
                    int amplifier = potionEffect.getAmplifier() + 1;
                    int color = potionEffect.getPotion().getLiquidColor();
                    double p1 = duration % 60f;
                    String seconds = format2.format(p1);
                    String s = name + " " + amplifier + ChatFormatting.WHITE + " " +  (int) duration / 60 + ":" + seconds;
                    FontRenderUtil.drawLeftStringWithShadow(s, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, color);
                    yOffset.getAndIncrement();


                });
            }

        if (server.getValBoolean()) {
            if (!mc.isSingleplayer()) {
                FontRenderUtil.drawLeftStringWithShadow("Server " + mc.getCurrentServerData().serverIP, sr.getScaledWidth() - mc.fontRenderer.getStringWidth("Server " + mc.getCurrentServerData().serverIP) + x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                yOffset.getAndIncrement();
            }
        }

        if (ping.getValBoolean()) {
            if (mc.isSingleplayer()) {
                FontRenderUtil.drawLeftStringWithShadow("Ping " + "0" + "ms", x.getValInt(), y.getValInt() + yOffset.get() * -10, c.getRGB());
                yOffset.getAndIncrement();
            } else {
                for (Entity entity : mc.world.getLoadedEntityList()) {
                    if (entity instanceof EntityPlayer && entity.getName().equalsIgnoreCase(mc.player.getName())) {
                        FontRenderUtil.drawLeftStringWithShadow("Ping " + ((mc.getConnection() != null && mc.player != null && mc.getConnection().getPlayerInfo(mc.player.getUniqueID()) != null) ? Integer.valueOf(mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime()) : "-1") + "ms", x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                        yOffset.getAndIncrement();
                    }
                }
            }
        }
        if (tpsString.length() > fpsString.length()) {
            if (tps.getValBoolean()) {
                FontRenderUtil.drawLeftStringWithShadow("Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                yOffset.getAndIncrement();
            }
            if (fps.getValBoolean()) {
                FontRenderUtil.drawLeftStringWithShadow("Fps " + mc.getDebugFPS(), x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                yOffset.getAndIncrement();
            }

        } else {
            if (fps.getValBoolean()) {
                FontRenderUtil.drawLeftStringWithShadow("Fps " + mc.getDebugFPS(), x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                yOffset.getAndIncrement();
            }
            if (tps.getValBoolean()) {
                FontRenderUtil.drawLeftStringWithShadow("Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                yOffset.getAndIncrement();
            }
        }


        } else {
            yOffset.set(0);
            if (potion.getValBoolean()) {
                AtomicInteger finalYOffset = new AtomicInteger(yOffset.get());
                mc.player.getActivePotionEffects().forEach(potionEffect -> {
                    String name = I18n.format(potionEffect.getPotion().getName());
                    DecimalFormat format2 = new DecimalFormat("00");
                    double duration = potionEffect.getDuration() / 19.99f;
                    int amplifier = potionEffect.getAmplifier() + 1;
                    int color = potionEffect.getPotion().getLiquidColor();
                    double p1 = duration % 60f;
                    String seconds = format2.format(p1);
                    String s = name + " " + amplifier + ChatFormatting.WHITE + " " +  (int) duration / 60 + ":" + seconds;
                    FontRenderUtil.drawLeftStringWithShadowCustom(s, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, color);
                    yOffset.getAndIncrement();


                });
            }

            if (server.getValBoolean()) {
                if (!mc.isSingleplayer()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Server " + mc.getCurrentServerData().serverIP, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                    yOffset.getAndIncrement();
                }
            }

            if (packetLoss.getValBoolean()) {
                FontRenderUtil.drawLeftStringWithShadowCustom("PacketLoss " + getPacketLoss() + "%", x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                yOffset.getAndIncrement();

            }

            if (ping.getValBoolean()) {
                if (mc.isSingleplayer()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Ping " + "0" + "ms", x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                    yOffset.getAndIncrement();
                } else {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Ping " + ((mc.getConnection() != null && mc.player != null && mc.getConnection().getPlayerInfo(mc.player.getUniqueID()) != null) ? Integer.valueOf(mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime()) : "-1") + "ms", x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                    yOffset.getAndIncrement();
                }
                }
            }
            if (tpsString.length() > fpsString.length()) {
                if (tps.getValBoolean()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                    yOffset.getAndIncrement();
                }
                if (fps.getValBoolean()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Fps " + mc.getDebugFPS(), x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                    yOffset.getAndIncrement();
                }

            } else {
                if (fps.getValBoolean()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Fps " + mc.getDebugFPS(), x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                    yOffset.getAndIncrement();
                }
                if (tps.getValBoolean()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset.get() * -10, c.getRGB());
                    yOffset.getAndIncrement();
                }
            }

        }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        packetCounter += 2.5;


    }

    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            packetCounter += 50;
            t = true;
            tick2 = 0;
        }

    }

    public int getPacketLoss() {
        int ret = 0;
        int retVal = ((packetCounter * 2) / (tick)) * 2;
            ret = retVal;
            if (delayCount == 0) {
                cc = ret;
            }
            delayCount++;
            if (delayCount > 100) {
                delayCount = 0;
            }

        if (delayCount != 0 && !(delayCount > 100)) {
            ret = cc;
        }
        if (retVal < 1 && delayCount == 0) {
            ret = 0;
        }

        if (ret > 100) {
            ret = 100;
        }

        if (tick > 20) {
            tick = 0;
            packetCounter = 0;
        }


        return ret;
    }




    }

