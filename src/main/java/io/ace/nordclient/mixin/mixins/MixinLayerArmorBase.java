package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.misc.EnchantColor;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LayerArmorBase.class)
public abstract class MixinLayerArmorBase {
    /**
     * @author Ace_______
     */
    @Redirect(method = "renderEnchantedGlint", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private static void armorGlint(final float red, final float green, final float blue, final float alpha) {
        if (HackManager.getHackByName("EnchantColor").isEnabled()) {
            GlStateManager.color((float) EnchantColor.r.getValDouble() / 255, (float) EnchantColor.g.getValDouble() / 255, (float) EnchantColor.b.getValDouble() / 255, 1.0F);
        } else {
            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
        }
    }

}
