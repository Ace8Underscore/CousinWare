package io.ace.nordclient.hacks.movement;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Jesus extends Hack {
    public Jesus() {
        super("Jesus", Category.MOVEMENT, "Walk on water duh");
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.world == null)
            return;
        if (mc.player.isOverWater() && !mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = 0;
        }
    }
}
