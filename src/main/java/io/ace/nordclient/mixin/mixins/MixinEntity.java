package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.mixin.accessor.IEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public abstract class MixinEntity implements IEntity {

    @Accessor @Override public abstract boolean getIsInWeb();


}
