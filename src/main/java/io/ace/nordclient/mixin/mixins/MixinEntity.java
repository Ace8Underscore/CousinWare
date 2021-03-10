package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.mixin.accessor.IEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity implements IEntity {


    @Shadow public boolean isInWeb;

    @Shadow public double motionY;

    @Shadow public double motionX;

    @Shadow public double motionZ;

    @Accessor @Override public abstract boolean getIsInWeb();

    @Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;motionY:D"),cancellable = true)
    public void move(MoverType type, double x, double y, double z, CallbackInfo info) {
        info.cancel();
        motionY = -1;

    }
}
