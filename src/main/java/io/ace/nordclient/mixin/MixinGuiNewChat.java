package io.ace.nordclient.mixin;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

@Mixin(GuiNewChat.class)
public abstract class MixinGuiNewChat {
    @Shadow
    @Final
    public List<ChatLine> chatLines;

    @Shadow
    protected abstract void setChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly);

    @Shadow
    @Final
    public static Logger LOGGER;

    /**
     * @author Ace________
     */

    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawChatCustom(final FontRenderer fontRenderer, final String m, final float x, final float y, final int color) {
        int returnInt;
        if (HackManager.getHackByName("FancyChat").isEnabled()) {
            returnInt = (int) CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(m, x, y - 1, color);
        } else {
            returnInt = (int) Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(m, x, y, color);
        }
        return returnInt;
    }

    @Inject(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawChat(I)V"))
    private void drawChatCustomHeight(CallbackInfo info) {
        info.cancel();
        CousinWare.INSTANCE.fontRenderer.getHeight();
    }

}


