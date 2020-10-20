package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.hacks.Hack;
import net.minecraft.init.SoundEvents;

public class DelayedSounds extends Hack {

    public DelayedSounds() {
        super("DelayedSounds", Category.MISC);
    }

    @Override
    public void onUpdate() {
        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() < 10) {
            mc.player.playSound(SoundEvents.ITEM_TOTEM_USE, 1, 1);
        }
    }
}
