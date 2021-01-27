package io.ace.nordclient.hacks.render;

import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author Ace________/Ace_#1233
 */

public class PlayerESP extends Hack {

    public PlayerESP() {
        super("PlayerESP", Category.RENDER, 14654750);
    }

    @Override
    public void onWorldRender(RenderEvent event) {

        for (Entity e : mc.world.getLoadedEntityList()) {
            if (e instanceof EntityPlayer) {
                e.setGlowing(true);
            }
        }
    }

    @Override
    public void onDisable() {
        for (Entity e : mc.world.getLoadedEntityList()) {
            if (e instanceof EntityPlayer) {
                e.setGlowing(false);
            }
        }
    }
//

}
