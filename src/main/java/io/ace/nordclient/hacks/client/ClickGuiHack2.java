package io.ace.nordclient.hacks.client;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import org.lwjgl.input.Keyboard;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class ClickGuiHack2 extends Hack {
    public ClickGuiHack2 INSTANCE;

    public static Setting red;
    public static Setting green;
    public static Setting blue;
    public static Setting alpha;
    public static Setting descriptions;
    public static Setting guiModes;
    public static Setting snow;
    public static Setting noise;
    public static Setting rainbow;

    public ClickGuiHack2() {
        super("ClickGUI2", Category.CLIENT, "Opens the ClickGUI");
        setBind(Keyboard.KEY_O);
        INSTANCE = this;


        // NordClient.INSTANCE.settingsManager.rSetting(new Setting("Rainbow", this, false, "ClickGuiRainbow"));
        CousinWare.INSTANCE.settingsManager.rSetting(red = new Setting("Red", this, 165, 0, 255, true, "ClickGuiHack2Red"));
        CousinWare.INSTANCE.settingsManager.rSetting(green = new Setting("Green", this, 147, 0, 255, true, "ClickGuiHac2kGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(blue = new Setting("Blue", this, 44, 0, 255, true, "ClickGuiHack2Blue"));
        CousinWare.INSTANCE.settingsManager.rSetting(alpha = new Setting("Alpha", this, 255, 0, 255, true, "ClickGuiHack2Alpha"));
        //NordClient.INSTANCE.settingsManager.rSetting(customFont = new Setting("CFont", this, true, "GlickGuiCustomFont"));
        CousinWare.INSTANCE.settingsManager.rSetting(descriptions = new Setting("Descriptions", this, true, "ClickGuiHackDescriptions"));
        CousinWare.INSTANCE.settingsManager.rSetting(noise = new Setting("Sound", this, true, "ClickGuiHackSound"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "ClickGuiHackRainbow"));

        //NordClient.INSTANCE.settingsManager.rSetting(snow = new Setting("Snow", this, true, "ClickGuiHackSnow"));

       // ArrayList<String> guiMode = new ArrayList<>();
       // guiMode.add("New");
        //guiMode.add("Old");
        //NordClient.INSTANCE.settingsManager.rSetting(guiModes = new Setting("GuiMode", this, "New", guiMode, "ClickGuiMode"));


    }

    public static Setting customFont;


    public void onEnable() {
        mc.displayGuiScreen(CousinWare.INSTANCE.clickGui2);
        disable();
    }

    @Listener
    public void onUpdate(UpdateEvent event) {

    }
}

