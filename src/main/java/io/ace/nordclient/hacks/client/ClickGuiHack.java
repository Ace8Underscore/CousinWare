package io.ace.nordclient.hacks.client;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import org.lwjgl.input.Keyboard;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class ClickGuiHack extends Hack {
    public ClickGuiHack INSTANCE;

    public static Setting red;
    public static Setting green;
    public static Setting blue;
    public static Setting alpha;
    public static Setting descriptions;
    public static Setting noise;
    public static Setting rainbow;

    public ClickGuiHack() {
        super("ClickGUI", Category.CLIENT, "Opens the ClickGUI");
        setBind(Keyboard.KEY_Y);
        INSTANCE = this;


        CousinWare.INSTANCE.settingsManager.rSetting(red = new Setting("Red", this, 165, 0, 255, true, "ClickGuiHackRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(green = new Setting("Green", this, 147, 0, 255, true, "ClickGuiHackGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(blue = new Setting("Blue", this, 44, 0, 255, true, "ClickGuiHackBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(alpha = new Setting("Alpha", this, 255, 0, 255, true, "ClickGuiHackAlpha"));
        CousinWare.INSTANCE.settingsManager.rSetting(descriptions = new Setting("Descriptions", this, true, "ClickGuiHackDescriptions"));
        CousinWare.INSTANCE.settingsManager.rSetting(noise = new Setting("Sound", this, true, "ClickGuiHackSound"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "ClickGuiHackRainbow"));

    }

    public static Setting customFont;

    @Override
    public void onEnable() {
        mc.displayGuiScreen(CousinWare.INSTANCE.clickGui2);
        try {
            if (CousinWare.INSTANCE.fontRenderer.getFontName().equalsIgnoreCase("null")) {
                CousinWare.INSTANCE.fontRenderer.setFontName("Arial");
                CousinWare.INSTANCE.fontRenderer.setFontSize(18);
                CousinWare.INSTANCE.configUtils.saveFont();
                CousinWare.INSTANCE.configUtils.loadFont();
            }
        } catch (Exception ignored) {


        }
        disable();
    }

}

