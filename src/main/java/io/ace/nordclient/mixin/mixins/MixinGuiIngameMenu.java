package io.ace.nordclient.mixin.mixins;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.hacks.client.Core;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = GuiIngameMenu.class, priority = 10000)
public abstract class MixinGuiIngameMenu extends GuiScreen {


    @Inject(method = "initGui", at = @At(value = "HEAD"), cancellable = true)
    public void initGui(CallbackInfo ci) {
        if (Core.essentials.getValBoolean()) {
            this.buttonList.clear();
            int i = -16;
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + -16, I18n.format("menu.returnToMenu", new Object[0])));
            if (!this.mc.isIntegratedServerRunning()) {
                ((GuiButton) this.buttonList.get(0)).displayString = " \u00A76 See you again soon!";
            }
            if (!this.mc.integratedServerIsRunning) {
                this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + -16, ChatFormatting.GOLD + "Return to " + Objects.requireNonNull(mc.getCurrentServerData()).serverIP));
            } else this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + -16, ChatFormatting.GOLD + "Return to Game"));
            this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + -16, 98, 20, I18n.format("menu.options", new Object[0])));
            this.buttonList.add(new GuiButton(12, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("fml.menu.modoptions", new Object[0])));
            GuiButton guibutton = this.addButton(new GuiButton(7, this.width / 2 - 100, this.height / 4 + 72 + -16, 200, 20, I18n.format("menu.shareToLan", new Object[0])));
            guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
            this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + -16, 98, 20, I18n.format("gui.advancements", new Object[0])));
            this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + -16, 98, 20, I18n.format("gui.stats", new Object[0])));
        }
    }
}
