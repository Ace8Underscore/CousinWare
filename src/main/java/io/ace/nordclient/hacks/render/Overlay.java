package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.utilz.FontRenderUtil;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.TpsUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

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

    int pingPlayer;

    public Overlay() {
        super("Overlay", Category.RENDER, 1687568);
        CousinWare.INSTANCE.settingsManager.rSetting(x = new Setting("x", this, 959, 0, 1000, false, "OverlayX"));
        CousinWare.INSTANCE.settingsManager.rSetting(y = new Setting("y", this, 530, 0, 1000, false, "OverlayY"));
        CousinWare.INSTANCE.settingsManager.rSetting(ping = new Setting("Ping", this, true, "OverlayPing"));
        CousinWare.INSTANCE.settingsManager.rSetting(server = new Setting("Server", this, true, "OverlayServer"));
        CousinWare.INSTANCE.settingsManager.rSetting(fps = new Setting("Fps", this, true, "OverlayFps"));
        CousinWare.INSTANCE.settingsManager.rSetting(tps = new Setting("Tps", this, true, "OverlayTps"));
//
    }
    @Override
    public void onUpdate() {
        pingPlayer = mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime();
    }

    @SubscribeEvent
    public void onRenderWorld(RenderGameOverlayEvent.Text event) {
        ScaledResolution sr = new ScaledResolution(mc);

        int yOffset = 0;
        String tpsString = "Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0;
        String fpsString = "Fps " + mc.getDebugFPS();
        Color c = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(), ClickGuiHack.blue.getValInt(), 255);
        if (!Core.customFont.getValBoolean()) {
        if (server.getValBoolean()) {
            if (!mc.isSingleplayer()) {
                FontRenderUtil.drawLeftStringWithShadow("Server " + mc.getCurrentServerData().serverIP, sr.getScaledWidth() - mc.fontRenderer.getStringWidth("Server " + mc.getCurrentServerData().serverIP) + x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                yOffset++;
            }
        }

        if (ping.getValBoolean()) {
            if (mc.isSingleplayer()) {
                FontRenderUtil.drawLeftStringWithShadow("Ping " + "0" + "ms", x.getValInt(), y.getValInt() + yOffset * -10, c.getRGB());
                yOffset++;
            } else {
                for (Entity entity : mc.world.getLoadedEntityList()) {
                    if (entity instanceof EntityPlayer && entity.getName().equalsIgnoreCase(mc.player.getName())) {
                        FontRenderUtil.drawLeftStringWithShadow("Ping " + ((mc.getConnection() != null && mc.player != null && mc.getConnection().getPlayerInfo(mc.player.getUniqueID()) != null) ? Integer.valueOf(mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime()) : "-1") + "ms", x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                        yOffset++;
                    }
                }
            }
        }
        if (tpsString.length() > fpsString.length()) {
            if (tps.getValBoolean()) {
                FontRenderUtil.drawLeftStringWithShadow("Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                yOffset++;
            }
            if (fps.getValBoolean()) {
                FontRenderUtil.drawLeftStringWithShadow("Fps " + mc.getDebugFPS(), x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                yOffset++;
            }

        } else {
            if (fps.getValBoolean()) {
                FontRenderUtil.drawLeftStringWithShadow("Fps " + mc.getDebugFPS(), x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                yOffset++;
            }
            if (tps.getValBoolean()) {
                FontRenderUtil.drawLeftStringWithShadow("Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                yOffset++;
            }
        }


        }else {
            if (server.getValBoolean()) {
                if (!mc.isSingleplayer()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Server " + mc.getCurrentServerData().serverIP, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                    yOffset++;
                }
            }

            if (ping.getValBoolean()) {
                if (mc.isSingleplayer()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Ping " + "0" + "ms", x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                    yOffset++;
                } else {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Ping " + ((mc.getConnection() != null && mc.player != null && mc.getConnection().getPlayerInfo(mc.player.getUniqueID()) != null) ? Integer.valueOf(mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime()) : "-1") + "ms", x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                    yOffset++;
                }
                }
            }
            if (tpsString.length() > fpsString.length()) {
                if (tps.getValBoolean()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                    yOffset++;
                }
                if (fps.getValBoolean()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Fps " + mc.getDebugFPS(), x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                    yOffset++;
                }

            } else {
                if (fps.getValBoolean()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Fps " + mc.getDebugFPS(), x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                    yOffset++;
                }
                if (tps.getValBoolean()) {
                    FontRenderUtil.drawLeftStringWithShadowCustom("Tps " + Math.round(TpsUtils.getTickRate() * 10) / 10.0, x.getValInt(), sr.getScaledHeight() - y.getValInt() + yOffset * -10, c.getRGB());
                    yOffset++;
                }
            }
        }




    }

