package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.NoRender;
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
        if (NoRender.minecart.getValBoolean() && HackManager.getHackByName("NoRender").isEnabled()) {
            info.cancel();
        }
    }
}
