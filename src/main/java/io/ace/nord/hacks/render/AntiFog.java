package io.ace.nord.hacks.render;

import io.ace.nord.hacks.Hack;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiFog extends Hack {
    public AntiFog() {
        super("AntiFog", Category.RENDER, "Removes Fog From Le Word");
    }

    @SubscribeEvent
    public void fogDensity(EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0);
        event.setCanceled(true);
    }
}
