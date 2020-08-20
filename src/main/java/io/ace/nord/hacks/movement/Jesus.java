package io.ace.nord.hacks.movement;

import io.ace.nord.hacks.Hack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Jesus extends Hack {
    public Jesus() {
        super("Jesus", Category.MOVEMENT, "Walk on water duh");
    }
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.world == null)
            return;
        if (mc.player.isOverWater() && !mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = 0;
        }
    }
}
