package io.ace.nordclient.mixin.mixins;


import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RenderPlayer.class)
public class MixinRenderPlayer {

  /*  @Inject(method = "renderEntityName", at = @At("HEAD"), cancellable = true)
    public void renderLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
        if (HackManager.getHackByName("NameTags").isEnabled()) {
            info.cancel();
        }

    } */

}
