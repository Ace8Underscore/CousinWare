package io.ace.nordclient.hacks.misc;

import io.ace.nordclient.event.UpdateEvent;
import io.ace.nordclient.hacks.Hack;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.Sound;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class DelayedSounds extends Hack {

    public DelayedSounds() {
        super("DelayedSounds", Category.MISC);
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.player.getHealth() + mc.player.getAbsorptionAmount() < 10) {
            mc.player.playSound(SoundEvents.ITEM_TOTEM_USE, 1, 1);
        }
    }
}
