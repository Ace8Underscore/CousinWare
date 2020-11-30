package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderMinecart.class)
public abstract class MixinRenderMinecart {

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void doRender(CallbackInfo info) {
        if (HackManager.getHackByName("NoMinecartLag").isEnabled()) {
            info.cancel();
        }
    }
}
