package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.NoRender;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderFallingBlock.class)
public abstract class MixinRenderFallingBlock {

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void doRender(CallbackInfo info) {
        if (NoRender.fallingAni.getValBoolean() && HackManager.getHackByName("NoRender").isEnabled()) {
            info.cancel();
        }
    }

}
