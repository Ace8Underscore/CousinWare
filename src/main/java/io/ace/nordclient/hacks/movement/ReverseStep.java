package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraft.client.Minecraft;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class ReverseStep extends Hack {

    Setting fallMode;

    private Double y;

    public ReverseStep() {
        super("ReverseStep", Category.MOVEMENT);
        ArrayList<String> fallModes = new ArrayList<>();
        fallModes.add("Fast");
        fallModes.add("Medium");
        fallModes.add("Slow");

        NordClient.INSTANCE.settingsManager.rSetting(fallMode = new Setting("FallModes", this,"slow" ,fallModes, "ReverseStepFallModes"));
    }

    @Listener
    public void onUpdate() {
        if (fallMode.getValString().equalsIgnoreCase("fast"))  y = -4D;
        if (fallMode.getValString().equalsIgnoreCase("medium"))  y = -2D;
        if (fallMode.getValString().equalsIgnoreCase("slow"))  y = -1D;
//
        if (mc.player.onGround && !mc.player.isInWater() && !mc.player.isInLava()) {
            mc.player.motionY = y;
        }

    }

}
