package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;

public class EnchantColor extends Hack {

    public static Setting r;
    public static Setting g;
    public static Setting b;
    public static Setting rainbow;

    public EnchantColor() {
        super("EnchantColor", Category.MISC, 9300540);
        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 255, 0, 255, false, "EnchantColorRed"));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 1, 0, 255, false, "EnchantColorGreen"));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 255, 0, 255, false, "EnchantColorBlue"));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "EnchantColorRainbow"));

    }

    @Override
    public void onUpdate() {
        if (rainbow.getValBoolean()) {
            RainbowUtil.settingRainbow(r, g, b);
        }
    }
}
