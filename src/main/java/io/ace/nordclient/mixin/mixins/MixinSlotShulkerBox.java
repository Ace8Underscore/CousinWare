package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.SlotShulkerBox;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlotShulkerBox.class)
public abstract class MixinSlotShulkerBox {

    final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "isItemValid", at = @At("HEAD"), cancellable = true)
    public void isItemValid(ItemStack stack, CallbackInfoReturnable info) {
        if (HackManager.getHackByName("ShulkerMod").isEnabled() && mc.player.isCreative()) {
            info.setReturnValue(true);
        }

    }
}
