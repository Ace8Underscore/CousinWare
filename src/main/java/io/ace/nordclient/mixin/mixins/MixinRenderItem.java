package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.misc.EnchantColor;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.awt.*;
/**
 * @author Ace_______
 *
 * thx john for helping with compatibility
 */
@Mixin(value = RenderItem.class)
public abstract class MixinRenderItem {

    @Shadow protected abstract void renderModel(IBakedModel model, int color);

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"))
    private int renderEffects(int color) {
        int retColor;
        Color c = new Color((float) EnchantColor.r.getValDouble() / 255, (float) EnchantColor.g.getValDouble() / 255, (float) EnchantColor.b.getValDouble() / 255, 1.0F);
        if (HackManager.getHackByName("EnchantColor").isEnabled()) {
            retColor = c.getRGB();
        } else {
            retColor = -8372020;
        }
        return retColor;
    }
}




