package io.ace.nordclient.hacks.render;

import io.ace.nordclient.hacks.Hack;

public class SelfParticle extends Hack {

    public SelfParticle() {
        super("SelfParticle", Category.RENDER, 45);
    }

    @Override
    public void onUpdate() {
        int x = (int) ((Math.random() * ((2 - -2) + 1))+0);
        int y = (int) ((Math.random() * ((2 - 0) + 1))+1);
        int z = (int) ((Math.random() * ((2 - -2) + 1))+-1);

        int particleId = (int) ((Math.random() * ((49 - 3) + 1))+1);

        if (!(particleId == 1) && !(particleId == 2) && !(particleId == 41))
        mc.effectRenderer.spawnEffectParticle(particleId, mc.player.posX + 1.5 +- x , mc.player.posY + y, mc.player.posZ + 1.5 +- z, 0, .5, 0, 10);
    }
}
