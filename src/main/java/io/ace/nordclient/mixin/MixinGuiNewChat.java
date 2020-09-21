/*package io.ace.nordclient.mixin;

import com.google.common.collect.Lists;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

import static net.minecraft.client.gui.GuiNewChat.calculateChatboxHeight;
import static net.minecraft.client.gui.GuiNewChat.calculateChatboxWidth;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat {
    Minecraft mc = Minecraft.getMinecraft();
    public final List<ChatLine> drawnChatLines = Lists.newArrayList();
    public final List<ChatLine> chatLines = Lists.newArrayList();
    public float getChatScale() {
        return this.mc.gameSettings.chatScale;
    }
    public int scrollPos;
    public boolean isScrolled;

    /**
     * @author Ace________
     *
    @Inject(method = "setChatLine", at = @At("HEAD"), cancellable = true)
    private void setChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly, CallbackInfo info) {
        if (HackManager.getHackByName("InfiniteChatLength").isEnabled()) {
            //info.cancel();
            if (chatLineId != 0) {
                this.deleteChatLine(chatLineId);
            }

            int i = MathHelper.floor((float) this.getChatWidth() / this.getChatScale());
            List<ITextComponent> list = GuiUtilRenderComponents.splitText(chatComponent, i, this.mc.fontRenderer, false, false);
            boolean flag = this.getChatOpen();

            ITextComponent itextcomponent;
            for (Iterator var8 = list.iterator(); var8.hasNext(); this.drawnChatLines.add(0, new ChatLine(updateCounter, itextcomponent, chatLineId))) {
                itextcomponent = (ITextComponent) var8.next();
                if (flag && this.scrollPos > 0) {
                    this.isScrolled = true;
                    this.scroll(1);
                }
            }

            while (this.drawnChatLines.size() > 200000) {
                this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
            }

            if (!displayOnly) {
                this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));

                while (this.chatLines.size() > 200000) {
                    this.chatLines.remove(this.chatLines.size() - 1);
                }
            }


        }
    }
    public void deleteChatLine(int id) {
        Iterator iterator = this.drawnChatLines.iterator();

        ChatLine chatline1;
        while (iterator.hasNext()) {
            chatline1 = (ChatLine) iterator.next();
            if (chatline1.getChatLineID() == id) {
                iterator.remove();
            }
        }
    }
        public int getChatWidth() {
            return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
        }

        public int getChatHeight() {
            return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
        }

    public boolean getChatOpen() {
        return this.mc.currentScreen instanceof GuiChat;
    }

    public void scroll(int amount) {
        this.scrollPos += amount;
        int i = this.drawnChatLines.size();
        if (this.scrollPos > i - this.getLineCount()) {
            this.scrollPos = i - this.getLineCount();
        }

        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }

    }
    public int getLineCount() {
        return this.getChatHeight() / 9;
    }



} */
