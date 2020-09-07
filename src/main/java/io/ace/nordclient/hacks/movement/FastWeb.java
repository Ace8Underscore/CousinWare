package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.NordClient;
import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.clientutil.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class FastWeb extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    Setting downMode;
    public FastWeb() {
        super("FastWeb", Category.MOVEMENT);

        ArrayList<String> downModes = new ArrayList<>();
        downModes.add("2b");
        downModes.add("Other");
        NordClient.INSTANCE.settingsManager.rSetting(downMode = new Setting("DownMode", this, "2b", downModes, "FastWebDownMode"));

    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.player.isInWeb) {
            if (downMode.getValString().equalsIgnoreCase("2b")) {
                mc.player.motionY = 1.1 / -5;
            }
            if (downMode.getValString().equalsIgnoreCase("Other")) {
                mc.player.motionY = 20.7 / -5;
            }
            //
        }
    }

    @Override
    public String getHudInfo() {
        return "[" + downMode.getValString() + "]";
    }
}
