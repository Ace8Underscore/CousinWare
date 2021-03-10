package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.mixin.accessor.ITimer;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Timer.class)
public abstract class MixinTimer implements ITimer {

    @Shadow protected float tickLength;


    @Override
    public void setTickLength(float tickLength) {
        this.tickLength = tickLength;
    }

    public float getTickLength() {
        return tickLength;
    }
}
