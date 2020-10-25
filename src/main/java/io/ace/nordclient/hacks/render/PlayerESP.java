package io.ace.nordclient.hacks.render;

import io.ace.nordclient.event.PlayerMoveEvent;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.client.renderer.entity.RenderSpectralArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * @author Ace________/Ace_#1233
 */

public class PlayerESP extends Hack {

    public PlayerESP() {
        super("PlayerESP", Category.RENDER, 1);
    }

    @Override
    public void onWorldRender(RenderEvent event) {

        for (Entity e : mc.world.getLoadedEntityList()) {
            if (e instanceof EntityPlayer) {
                e.setGlowing(true);
            }
        }
    }
    public void onDisable() {
        for (Entity e : mc.world.getLoadedEntityList()) {
            if (e instanceof EntityPlayer) {
                e.setGlowing(false);
            }
        }
    }
//

}
