package io.ace.nordclient.hacks.render;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.client.particle.ParticleTotem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Hand extends Hack {

    public Hand() {
        super("Hand", Category.RENDER);

    }

    @Listener
    public void onUpdate(UpdateEvent event) {

        mc.entityRenderer.itemRenderer.renderManager.viewerPosX = 10;
        mc.entityRenderer.itemRenderer.renderManager.renderPosX = 10;
        mc.entityRenderer.itemActivationTicks = 100;
    }
    @SubscribeEvent
    public void onTickRender(TickEvent.RenderTickEvent event) {
        if (mc.world == null)
            return;

        mc.entityRenderer.itemRenderer.renderManager.viewerPosX = 10;
        mc.entityRenderer.itemRenderer.renderManager.renderPosX = 10;
        mc.entityRenderer.itemActivationTicks = 100;
        mc.entityRenderer.itemActivationOffX = 100;
        mc.entityRenderer.itemRenderer.renderManager.playerViewX = 10;
        //mc.effectRenderer.

        //mc.effectRenderer.addEffect(ParticleTotem);
    }
}
