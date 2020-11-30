package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

/**
 * @author Ace________/Ace_#1233
 */

public class FullBright extends Hack {
    Setting mode;
    Setting num;

    public FullBright() {
        super("FullBright", Category.RENDER, 11658243);
        java.util.ArrayList<String> modes = new ArrayList<>();
        modes.add("Gamma");
        modes.add("NightVision");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "Gamma", modes, "FullBrightModes"));
    }

    @Override
    public void onUpdate() {
        if (mode.getValString().equalsIgnoreCase("gamma")) {
            mc.gameSettings.gammaSetting = 100;
            mc.player.removeActivePotionEffect(Potion.getPotionById(16));
        } else {
            mc.gameSettings.gammaSetting = 0;
            mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 1000, 1));
        }
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = 0;
    }
}




