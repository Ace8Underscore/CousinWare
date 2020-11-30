package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.NoLag;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityEnchantmentTableRenderer.class)
public abstract class MixinTileEntityEnchantmentTableRenderer {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(CallbackInfo info) {
        if (HackManager.getHackByName("NoLag").isEnabled() && NoLag.eTable.getValBoolean()) {
            info.cancel();
        }
    }
}
