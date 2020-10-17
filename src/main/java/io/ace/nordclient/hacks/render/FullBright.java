package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class FullBright extends Hack {
    Setting mode;
    Setting num;

    public FullBright() {
        super("FullBright", Category.RENDER);
        java.util.ArrayList<String> modes = new ArrayList<>();
        modes.add("Gamma");
        modes.add("NightVision");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "Gamma", modes, "FullBrightModes"));
    }

    @Override
    public void onUpdate() {
        if (mode.getValString().equalsIgnoreCase("gamma")) {
            mc.gameSettings.gammaSetting = 2000;
            mc.player.removeActivePotionEffect(Potion.getPotionById(16));
        } else {
            mc.gameSettings.gammaSetting = 50;
            mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 1000, 1));
        }
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = 50;
    }
}




