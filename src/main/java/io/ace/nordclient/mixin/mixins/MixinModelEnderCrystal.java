package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.Crystal;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelEnderCrystal.class)
public abstract class MixinModelEnderCrystal {
    /**
     * @author Ace_______
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V" ))
    public void render(final float x, final float y, final float z) {
        if (HackManager.getHackByName("DjCrystal").isEnabled()) {
            GlStateManager.scale(x + Crystal.x.getValDouble(), y + Crystal.y.getValDouble(), z + Crystal.z.getValDouble());
        } else {
            GlStateManager.scale( x, y, z);
        }
    }
}
