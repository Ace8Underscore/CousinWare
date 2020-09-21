package io.ace.nordclient.hacks.render;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.clientutil.Setting;
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
        NordClient.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "Gamma", modes, "FullBrightModes"));
        NordClient.INSTANCE.settingsManager.rSetting(num = new Setting("potion", this, 1, 0, 30, false, "FullBrightnum"));

    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mode.getValString().equalsIgnoreCase("gamma")) {
            mc.gameSettings.gammaSetting = 2000;
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




