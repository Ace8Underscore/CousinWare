package io.ace.nordclient.mixin.mixins;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.hacks.client.Core;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = GuiConnecting.class, priority = 10000)
public abstract class MixinGuiConnecting extends GuiScreen {

    public NetworkManager networkManager;

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (Core.essentials.getValBoolean()) {
            ci.cancel();
            this.drawDefaultBackground();
            if (this.networkManager == null) {
                this.drawCenteredString(this.fontRenderer, I18n.format(ChatFormatting.GOLD + "Connecting to " + Objects.requireNonNull(mc.getCurrentServerData()).serverIP), this.width / 2, this.height / 2 - 50, 16777215);
            } else {
                this.drawCenteredString(this.fontRenderer, I18n.format(ChatFormatting.GOLD + "Logging into " + Objects.requireNonNull(mc.getCurrentServerData()).serverIP), this.width / 2, this.height / 2 - 50, 16777215);
            }

            super.drawScreen(mouseX, mouseY, partialTicks);
        }
    }
}
