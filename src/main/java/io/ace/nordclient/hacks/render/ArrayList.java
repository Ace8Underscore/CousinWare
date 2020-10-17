package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.utilz.FontRenderUtil;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Comparator;

public class ArrayList extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    int hackCount;



    Setting x;
    Setting y;
    Setting sideMode;
    Setting orderMode;
    Setting r;
    Setting g;
    Setting b;
    Color c;
    Setting rainbow;

    public ArrayList() {
        super("ArrayList", Category.RENDER);
        this.drawn = true;
        CousinWare.INSTANCE.settingsManager.rSetting(x = new Setting("x", this, 1, 0, 1000, false, "ArrayListX"));
        CousinWare.INSTANCE.settingsManager.rSetting(y = new Setting("y", this, 3, 0, 1000, false, "ArrayListY"));

        java.util.ArrayList<String> sideModes = new java.util.ArrayList<>();
        sideModes.add("Left");
        sideModes.add("Right");
        CousinWare.INSTANCE.settingsManager.rSetting(sideMode = new Setting("Side", this, "Left", sideModes, "ArrayListSideMode"));

        java.util.ArrayList<String> orderModes = new java.util.ArrayList<>();
        orderModes.add("Up");
        orderModes.add("Down");
        CousinWare.INSTANCE.settingsManager.rSetting(orderMode = new Setting("Order", this, "Up", orderModes, "ArrayListOrderMode"));

        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 255, 0, 255, true, "ArrayListRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 26, 0, 255, true, "ArrayListGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 42, 0, 255, true, "ArrayListBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "ArrayListRainbow"));

    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event)  {
        if (mc.world == null)
            return;
        hackCount = 0;
        HackManager.getHacks()
                .stream()
                .filter(Hack::isEnabled)
                .filter(Hack::isDrawn)
                .sorted(Comparator.comparing(hack -> mc.fontRenderer.getStringWidth(hack.getName() + hack.getHudInfo()) * (-1)))
                .forEach(h -> {
                    if (!Core.customFont.getValBoolean()) {
                        if (orderMode.getValString().equalsIgnoreCase("up") && sideMode.getValString().equalsIgnoreCase("left")) {
                        if (rainbow.getValBoolean()) {
                            c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                            mc.fontRenderer.drawStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), RainbowUtil.getRainbow(hackCount * 150));
                            hackCount++;
                            RainbowUtil.settingRainbow(r, g, b);
                        } else {
                            c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                            mc.fontRenderer.drawStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), c.getRGB());
                            hackCount++;

                        }
                    }
                    if (orderMode.getValString().equalsIgnoreCase("down") && sideMode.getValString().equalsIgnoreCase("right")) {
                        if (rainbow.getValBoolean()) {
                            c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                            FontRenderUtil.drawLeftStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() - (hackCount * 10), RainbowUtil.getRainbow(hackCount * 150));
                            hackCount++;
                            RainbowUtil.settingRainbow(r, g, b);
                        } else {
                            c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                            FontRenderUtil.drawLeftStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() - (hackCount * 10), c.getRGB());
                            hackCount++;

                        }
                    }

                    if (orderMode.getValString().equalsIgnoreCase("up") && sideMode.getValString().equalsIgnoreCase("right")) {
                        if (rainbow.getValBoolean()) {
                            c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                            FontRenderUtil.drawLeftStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), RainbowUtil.getRainbow(hackCount * 150));
                            hackCount++;
                            RainbowUtil.settingRainbow(r, g, b);
                        } else {
                            c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                            FontRenderUtil.drawLeftStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), c.getRGB());
                            hackCount++;

                        }
                    }

                    if (orderMode.getValString().equalsIgnoreCase("down") && sideMode.getValString().equalsIgnoreCase("left")) {
                        if (rainbow.getValBoolean()) {
                            c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                            mc.fontRenderer.drawStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * -10), RainbowUtil.getRainbow(hackCount * 150));
                            hackCount++;
                            RainbowUtil.settingRainbow(r, g, b);
                        } else {
                            c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                            mc.fontRenderer.drawStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * -10), c.getRGB());
                            hackCount++;

                        }
                    }
                }
                    if (Core.customFont.getValBoolean()) {
                        if (orderMode.getValString().equalsIgnoreCase("up") && sideMode.getValString().equalsIgnoreCase("left")) {
                            if (rainbow.getValBoolean()) {
                                c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                                CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), RainbowUtil.getRainbow(hackCount * 150));
                                hackCount++;
                                RainbowUtil.settingRainbow(r, g, b);
                            } else {
                                c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                                CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), c.getRGB());
                                hackCount++;

                            }
                        }
                        if (orderMode.getValString().equalsIgnoreCase("down") && sideMode.getValString().equalsIgnoreCase("right")) {
                            if (rainbow.getValBoolean()) {
                                c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                                FontRenderUtil.drawLeftStringWithShadowCustom(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() - (hackCount * 10), RainbowUtil.getRainbow(hackCount * 150));
                                hackCount++;
                                RainbowUtil.settingRainbow(r, g, b);
                            } else {
                                c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                                FontRenderUtil.drawLeftStringWithShadowCustom(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() - (hackCount * 10), c.getRGB());
                                hackCount++;

                            }
                        }

                        if (orderMode.getValString().equalsIgnoreCase("up") && sideMode.getValString().equalsIgnoreCase("right")) {
                            if (rainbow.getValBoolean()) {
                                c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                                FontRenderUtil.drawLeftStringWithShadowCustom(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), RainbowUtil.getRainbow(hackCount * 150));
                                hackCount++;
                                RainbowUtil.settingRainbow(r, g, b);
                            } else {
                                c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                                FontRenderUtil.drawLeftStringWithShadowCustom(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * 10), c.getRGB());
                                hackCount++;

                            }
                        }

                        if (orderMode.getValString().equalsIgnoreCase("down") && sideMode.getValString().equalsIgnoreCase("left")) {
                            if (rainbow.getValBoolean()) {
                                c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                                CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * -10), RainbowUtil.getRainbow(hackCount * 150));
                                hackCount++;
                                RainbowUtil.settingRainbow(r, g, b);
                            } else {
                                c = new Color(r.getValInt(), g.getValInt(), b.getValInt());
                                CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(h.getName() + ChatFormatting.GRAY + " " + h.getHudInfo(), x.getValInt(), y.getValInt() + (hackCount * -10), c.getRGB());
                                hackCount++;

                            }
                        }
                    }



                });
    }

}
