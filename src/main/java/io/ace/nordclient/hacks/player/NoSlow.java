package io.ace.nordclient.hacks.player;

import io.ace.nordclient.hacks.Hack;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlow extends Hack {

    public NoSlow() {
        super("NoSlow", Category.PLAYER, 10060642);
    }

    @SubscribeEvent
    public void onInput(InputUpdateEvent event) {
        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5.0f;
            event.getMovementInput().moveForward *= 5.0f;
        }
    }
}
