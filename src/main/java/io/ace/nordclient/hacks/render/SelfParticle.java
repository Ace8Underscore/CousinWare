package io.ace.nordclient.hacks.render;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SelfParticle extends Hack {

    public SelfParticle() {
        super("SelfParticle", Category.RENDER);
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        int x = (int) ((Math.random() * ((2 - -2) + 1))+0);
        int y = (int) ((Math.random() * ((2 - 0) + 1))+1);
        int z = (int) ((Math.random() * ((2 - -2) + 1))+-1);

        int particleId = (int) ((Math.random() * ((49 - 3) + 1))+1);

        if (!(particleId == 41) && !(particleId == 1) && !(particleId == 2))
        mc.effectRenderer.spawnEffectParticle(particleId, mc.player.posX + 1.5 +- x , mc.player.posY + y, mc.player.posZ + 1.5 +- z, 0, .5, 0, 10);
    }
}
