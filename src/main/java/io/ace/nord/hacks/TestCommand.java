package io.ace.nord.hacks;

import io.ace.nord.command.Command;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TestCommand extends Hack {
    private int delay = 0;

    @SubscribeEvent
    public void onClientTick(TickEvent.WorldTickEvent event) {
        delay++;
        if (delay > 1000) {
            if (mc.player.isSneaking()) {
                Command.sendClientSideMessage("Working?");
            }
            //Command.sendClientSideMessage("Working?");
            //event.

        }
    }
}
