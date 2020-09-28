package io.ace.nordclient.guinew;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;

import java.awt.*;
import java.io.IOException;

public class RubyClickGui extends GuiScreen {
    private String buttonName="null";
    private String currentCategory="combat";
    private boolean settingListen = false;
    public static float scaleUp=0;
    private int snowLimit;
    private boolean addingSnow;
    private int checkSize;
    private Minecraft mc = Minecraft.getMinecraft();

    public RubyClickGui() {
        Snow.render.clear();
        ScaledResolution sr = new ScaledResolution(mc);
        Snow.render.add(new Snow(Util.random(0, sr.getScaledWidth()), 0));
        new Snow(0,0);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (scaleUp < 1) {
            scaleUp += 0.025;
        }
        ScaledResolution sr = new ScaledResolution(mc);
        int uiX = sr.getScaledWidth() / 2;
        int uiY = sr.getScaledHeight() / 2;
        if (scaleUp >= 1) {
            //MultipleGLScissor.rectBlurry(uiX - 200, uiY - 150, uiX + 200, uiY + 150);
            //MultipleGLScissor.rectEffect(uiX - 250, uiY - 170, uiX + 250, uiY + 170,"invert");
            scaleUp=1;
        }
        if (addingSnow) {
            Snow snows = new Snow(Util.random(0, sr.getScaledWidth()), Util.random(-100, -200));
            Snow.render.add(snows);
            snowLimit += 1;
            addingSnow = false;
        }
        if (Snow.render.size() > 1) {
            checkSize = Snow.render.size()-2;
        }
        for (Snow snow : Snow.getRender()) {
            Snow last = Snow.render.get(checkSize);
            Snow first = Snow.getRender().get(0);
            if (snow != first) {
                snow.drawSnow();
            }
            snow.setY((int) (snow.getY() + 0.5));
            snow.setX((int) (snow.getX() + Util.random(-1, 2)));
            if (snow.getY() == last.getY()) {
                last.setY((int) (last.getY() + 10));
            }
            if (snow.getY() > sr.getScaledHeight()) {
                if (snowLimit < 30) {
                    addingSnow = true;
                }
                snow.setX(Util.random(0, sr.getScaledWidth()));
                snow.setY(0);
            }
        }
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleUp,scaleUp,0);
        Gui.drawRect(uiX - 250, uiY - 170, uiX + 250, uiY - 169, new Color(1, 1, 1).getRGB());
        Gui.drawRect(uiX - 250, uiY - 170, uiX - 249, uiY + 170, new Color(1, 1, 1).getRGB());
        Gui.drawRect(uiX - 250, uiY + 170, uiX + 250, uiY + 169, new Color(1, 1, 1).getRGB());
        Gui.drawRect(uiX + 250, uiY - 170, uiX + 249, uiY + 170, new Color(1, 1, 1).getRGB());
        Gui.drawRect(uiX - 190, uiY - 170, uiX - 191, uiY + 170, new Color(1, 1, 1).getRGB());
        if (currentCategory != null) {
            Gui.drawRect(uiX - 110, uiY - 170, uiX - 111, uiY + 170, new Color(1, 1, 1).getRGB());
        }
        int categoryY = 160;
        for (Hack.Category category : Hack.Category.values()) {
            if (mouseX > uiX - 250 && mouseX < uiX - 190) {
                if (mouseY > uiY-categoryY-10 && mouseY < uiY-categoryY+20) {
                    Gui.drawRect(uiX-250,uiY-categoryY-10,uiX-190,uiY-categoryY+20,new Color(1,1,1).getRGB());
                }
            }
            assert currentCategory != null;
            if (currentCategory.equalsIgnoreCase(category.name())) {
                mc.fontRenderer.drawStringWithShadow("-> "+category.toString(), uiX - 245, uiY - categoryY, new Color(255, 255, 255).getRGB());
            } else {
                mc.fontRenderer.drawStringWithShadow(category.toString(), uiX - 245, uiY - categoryY, new Color(255, 255, 255).getRGB());
            }
            categoryY -= 40;
        }
        int moduleY = uiY - 160;
        int setY = uiY - 160;
        int setX = uiX - 107;
        if (settingListen) {
            if (CousinWare.INSTANCE.settingsManager.getSettingsByMod(HackManager.getHackByName(buttonName)) != null) {
                for (Setting set : CousinWare.INSTANCE.settingsManager.getSettingsByMod(HackManager.getHackByName(buttonName))) {
                    if (set != null) {
                        if (setY+15>uiY+170) {
                            setX=uiX+57;
                            setY = uiY-160;
                        }
                        if (mouseX > setX - 4 && mouseX < setX + 157 && mouseY > setY - 5 && mouseY < setY + 10) {
                            if (!set.isSlider()) {
                                Gui.drawRect(setX - 4, setY - 3, setX + 157, setY + 10, new Color(1, 1, 1).getRGB());
                            }
                        }
                        if (set.isSlider()) {
                            mc.fontRenderer.drawStringWithShadow(set.getDisplayName() + " : " + set.getValDouble(), setX, setY, new Color(255, 255, 255).getRGB());
                            Gui.drawRect(setX - 4, setY + 10, setX + 158, setY + 25, new Color(1, 1, 1).getRGB());
                            double value = (set.getValDouble() - set.getMin()) / (set.getMax() - set.getMin());
                            Gui.drawRect(setX - 3, setY + 11, (int) ((setX - 3) + (value * ((setX + 157) - (setX - 3)))), setY + 24, new Color(75, 255, 75).getRGB());
                            setY += 15;
                        } else if (set.isCheck()) {
                            mc.fontRenderer.drawStringWithShadow(set.getDisplayName(), setX, setY, new Color(255, 255, 255).getRGB());
                            Gui.drawRect(setX + 126, setY - 2, setX + 148, setY + 9, new Color(150, 150, 150).getRGB());
                            if (set.getValBoolean()) {
                                Gui.drawRect(setX + 127, setY + 3, setX + 147, setY + 5, new Color(75, 75, 75).getRGB());
                                Gui.drawRect(setX + 140, setY, setX + 147, setY + 8, new Color(75, 255, 75).getRGB());
                            } else {
                                Gui.drawRect(setX + 127, setY + 3, setX + 147, setY + 5, new Color(75, 75, 75).getRGB());
                                Gui.drawRect(setX + 127, setY, setX + 134, setY + 8, new Color(255, 75, 75).getRGB());
                            }
                        } else if (set.isCombo()) {
                            mc.fontRenderer.drawStringWithShadow(set.getDisplayName() + " : " + set.getValString(), setX, setY, new Color(255, 255, 255).getRGB());
                        }
                        setY += 15;
                    }
                }
            }
        }
        for (Hack hack : HackManager.getHacks()) {
            if (hack.getCategory().name().equalsIgnoreCase(currentCategory)) {
                if (hack.isEnabled()) {
                    Gui.drawRect(uiX - 190, moduleY-3, uiX - 111,moduleY+10, new Color(150, 150, 150).getRGB());
                }
                boolean za = mouseX > uiX - 192 && mouseX < uiX - 110 && mouseY > moduleY - 3 && mouseY < moduleY + 10;
                if (za) {
                    Gui.drawRect(uiX - 190, moduleY-3, uiX - 111,moduleY+10, new Color(1, 1, 1).getRGB());
                    if (hack.isEnabled()) {
                        Gui.drawRect(uiX - 190, moduleY-3, uiX - 111,moduleY+10, new Color(150, 150, 150,150).getRGB());
                    }
                    if (!settingListen) {
                        buttonName = hack.getName();
                    }
                }
                mc.fontRenderer.drawStringWithShadow(hack.getName(), uiX - 185, moduleY, new Color(255, 255, 255).getRGB());
                moduleY += 15;
            }
        }
        int setY2 = uiY - 160;
        int setX2 = uiX - 107;
        if (settingListen) {
            if (CousinWare.INSTANCE.settingsManager.getSettingsByMod(HackManager.getHackByName(buttonName)) != null) {
                for (Setting set : CousinWare.INSTANCE.settingsManager.getSettingsByMod(HackManager.getHackByName(buttonName))) {
                    if (set != null) {
                        if (setY2+15 > uiY+170) {
                            setY2=uiY-160;
                            setX2=uiX+57;
                        }
                        if (set.isSlider()) {
                            setY2 += 15;
                        }
                        if (mouseX > setX2-4 && mouseX < setX2+157 && mouseY > setY2 - 5 && mouseY < setY2 + 10) {
                            Gui.drawRect(mouseX + 4, mouseY - 2, mouseX + 4 + mc.fontRenderer.getStringWidth(set.getDisplayName()) + 3, mouseY + 11, new Color(25, 50, 50).getRGB());
                            Gui.drawRect(mouseX + 5, mouseY - 1, mouseX + 5 + mc.fontRenderer.getStringWidth(set.getDisplayName()) + 1, mouseY + 10, new Color(75, 100, 100).getRGB());
                            mc.fontRenderer.drawString(set.getDisplayName(), mouseX + 6, mouseY, new Color(255, 255, 255).getRGB());
                        }
                        setY2 += 15;
                    }
                }
            }
        }
        int moduleY2 = uiY - 160;
        for (Hack hack : HackManager.getHacks()) {
            if (hack.getCategory().name().equalsIgnoreCase(currentCategory)) {
                boolean za = mouseX > uiX - 192 && mouseX < uiX - 110 && mouseY > moduleY2 - 3 && mouseY < moduleY2 + 10;
                if (za) {
                    Gui.drawRect(mouseX +4, mouseY - 2, mouseX +4 + mc.fontRenderer.getStringWidth(hack.getDescription()) + 3, mouseY + 11, new Color(25, 50, 50).getRGB());
                    Gui.drawRect(mouseX +5, mouseY - 1, mouseX +5 + mc.fontRenderer.getStringWidth(hack.getDescription()) + 1, mouseY + 10, new Color(75, 100, 100).getRGB());
                    mc.fontRenderer.drawString(hack.getDescription(), mouseX+6, mouseY, new Color(255, 255, 255).getRGB());
                }
                moduleY2 += 15;
            }
        }
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        ScaledResolution sr = new ScaledResolution(mc);
        int uiX = sr.getScaledWidth()/2;
        int uiY = sr.getScaledHeight()/2;
        int setY = uiY - 160;
        int setX = uiX - 107;
        if (settingListen) {
            if (CousinWare.INSTANCE.settingsManager.getSettingsByMod(HackManager.getHackByName(buttonName)) != null) {
                for (Setting set : CousinWare.INSTANCE.settingsManager.getSettingsByMod(HackManager.getHackByName(buttonName))) {
                    if (set != null) {
                        boolean b = mouseX > setX-3 && mouseX < setX+153 && mouseY > setY + 11 && mouseY < setY + 24;
                        if (set.isSlider()) {
                            if (setY+15 > uiY+170) {
                                setY=uiY-160;
                                setX=uiX+57;
                            }
                            if (b) {
                                double min = set.getMin();
                                double max = set.getMax();
                                double inc = set.getValDouble();
                                double valAbs = mouseX - (setX-3 + 2 + 1.0D);
                                double perc = valAbs / ((setX+157) - (setX-4) - 5.0D);
                                perc = Math.min(Math.max(0.0D, perc), 1.0D);
                                double valRel = (max - min) * perc;
                                double val1 = min + valRel;
                                val1 = Math.round(val1 * (1.0D / inc)) / (1.0D / inc);
                                set.setValDouble(val1);
                            }
                            setY += 15;
                        }
                        setY += 15;
                    }
                }
            }
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);
        int uiX = sr.getScaledWidth()/2;
        int uiY = sr.getScaledHeight()/2;
        int moduleY = uiY - 160;
        for (Hack hack : HackManager.getHacks()) {
            if (hack.getCategory().name().equalsIgnoreCase(currentCategory)) {
                if (mouseX > uiX-192 && mouseX < uiX-110 && mouseY > moduleY-3 && mouseY < moduleY+10) {
                    if (mouseButton==0) {
                        HackManager.getHackByName(hack.getName()).toggle();
                        Minecraft.getMinecraft().player.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 0.5f, 0.5f);
                        //HackManager.save();
                    } else if (mouseButton==1) {
                        if (CousinWare.INSTANCE.settingsManager.getSettingsByMod(HackManager.getHackByName(hack.getName())) != null) {
                            if (settingListen) {
                                //Minecraft.getMinecraft().player.playSound("tile.piston.in", 0.5f, 0.1f);
                            } else {
                                //Minecraft.getMinecraft().player.playSound("tile.piston.out", 0.5f, 0.1f);
                            }
                            settingListen = !settingListen;
                            //NordClient.INSTANCE.settingsManager.save();
                        }
                    }
                }
                moduleY += 15;
            }
        }
        int setY = uiY - 160;
        int setX = uiX - 107;
        if (settingListen) {
            if (CousinWare.INSTANCE.settingsManager.getSettingsByMod(HackManager.getHackByName(buttonName)) != null) {
                for (Setting set : CousinWare.INSTANCE.settingsManager.getSettingsByMod(HackManager.getHackByName(buttonName))) {
                    if (set != null) {
                        if (setY+15 > uiY+170) {
                            setY=uiY-160;
                            setX=uiX+57;
                        }
                        boolean b = mouseX > setX-4 && mouseX < setX+153 && mouseY > setY - 3 && mouseY < setY + 10;
                        if (set.isSlider()) {
                            setY += 15;
                        } else if (set.isCheck()) {
                            if (b) {
                                if (mouseButton == 0) {
                                    CousinWare.INSTANCE.settingsManager.getSettingByDisplayName(set.getDisplayName()).setValBoolean(!set.getValBoolean());
                                }
                            }
                        } else if (set.isCombo()) {
                            int current = set.getOptions().indexOf(set.getValString());
                            if (b) {
                                if (mouseButton == 0) {
                                    current += 1;
                                    if (current >= set.getOptions().size()) {
                                        current = 0;
                                    }
                                    CousinWare.INSTANCE.settingsManager.getSettingByDisplayName(set.getDisplayName()).setValString(set.getOptions().get(current));
                                } else if (mouseButton == 1) {
                                    current -= 1;
                                    if (current < 0) {
                                        current = set.getOptions().size() - 1;
                                    }
                                    CousinWare.INSTANCE.settingsManager.getSettingByDisplayName(set.getDisplayName()).setValString(set.getOptions().get(current));
                                }
                            }
                        }
                        //Ruby.settingsManager.save();
                        setY += 15;
                    }
                }
            }
        }
        int categoryY = 160;
        for (Hack.Category category : Hack.Category.values()) {
            if (mouseX > uiX - 250 && mouseX < uiX - 190) {
                if (mouseY > uiY-categoryY && mouseY < uiY-categoryY+40) {
                    currentCategory = category.name().toLowerCase();
                }
            }
            categoryY -= 40;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

}