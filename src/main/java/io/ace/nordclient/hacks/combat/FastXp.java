package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.init.Items;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class FastXp extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public FastXp() {
        super("FastXp", Category.COMBAT);
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            mc.rightClickDelayTimer = 0;
        }
    }
}
