package io.ace.nordclient.hacks.combat;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.init.Items;

public class FastXp extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public FastXp() {
        super("FastXp", Category.COMBAT, 6);
    }

    @Override
    public void onUpdate() {
        if (mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            mc.rightClickDelayTimer = 0;
        }
    }
}
