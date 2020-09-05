package io.ace.nordclient.hacks.render;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.clientutil.Setting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGuiHack extends Hack {
    public ClickGuiHack INSTANCE;

    public static Setting red;
    public static Setting green;
    public static Setting blue;
    public static Setting alpha;

    public ClickGuiHack() {
        super("ClickGUI", Category.CLIENT, "Opens the ClickGUI");
        setBind(Keyboard.KEY_Y);
        INSTANCE = this;


        NordClient.INSTANCE.settingsManager.rSetting(new Setting("Rainbow", this, false, "ClickGuiRainbow"));
        NordClient.INSTANCE.settingsManager.rSetting(red = new Setting("Red", this, 255, 0, 255, true, "ClickGuiRed"));
        NordClient.INSTANCE.settingsManager.rSetting(green = new Setting("Green", this, 26, 0, 255, true, "ClickGuiGreen"));
        NordClient.INSTANCE.settingsManager.rSetting(blue = new Setting("Blue", this, 42, 0, 255, true, "ClickGuiBlue"));
        NordClient.INSTANCE.settingsManager.rSetting(alpha = new Setting("Alpha", this, 255, 0, 255, true, "ClickGuiAlpha"));
        NordClient.INSTANCE.settingsManager.rSetting(customFont = new Setting("CFont", this, true, "GlickGuiCustomFont"));
        NordClient.INSTANCE.settingsManager.rSetting(new Setting("Tooltips", this, true, "ClickGuiTooltips"));
    }
    public static Setting customFont;


    public void onEnable(){
        mc.displayGuiScreen(NordClient.INSTANCE.clickGui);
        disable();
    }
}
