package io.ace.nordclient.hud.hudcomponets;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.client.ClickGuiHudHack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.hud.Hud;
import io.ace.nordclient.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ace________/Ace_#1233
 */

public class GoonSquad extends Hud {

    public GoonSquad() {
        super("GoonSquad", 288, 1030);
    }

    public static String friends;
    private String str;

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (button == 0) {
            setHeld(this.name);
            if (isMouseOnButton(mouseX, mouseY) && ClickGuiHudHack.canMove) {
                setX(mouseX);
                setY(mouseY);

            }
        }

        if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
            //this.open = !this.open;
            //this.parent.refresh();
        }

    }

    public boolean isMouseOnButton(final int x, final int y) {
        return x >= this.getX() - 20 && x <= this.getX() + 80 && y >= this.getY() - 5 && y <= this.getY() + 20;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.world == null)
            return;
        if (Mouse.isButtonDown(0)) mouseClicked(Mouse.getX(), Mouse.getY(), 0);
        AtomicInteger y = new AtomicInteger(2);
        if (!Core.customFont.getValBoolean()) mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "GoonSquad", (this.getX() / 2) - 5, ((this.getY() / -2) + 540) - 10, 16777215);
        else CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "GoonSquad", (this.getX() / 2) - 5, ((this.getY() / -2) + 540) - 10, 16777215);
        for (Object o : mc.world.getLoadedEntityList()) {
            if (o instanceof EntityPlayer) {
                if (((EntityPlayer) o).getName() != mc.player.getName()) {
                    if (FriendManager.isFriend(((EntityPlayer) o).getName())) {
                        friends = ((EntityPlayer) o).getGameProfile().getName();
                        str = " " + friends;
                        if (!Core.customFont.getValBoolean()) mc.fontRenderer.drawStringWithShadow(str, (this.getX() / 2) - 5, y.get() + ((this.getY() / -2) + 540), 16755200);
                        else CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(str, (this.getX() / 2) - 5, y.get() + ((this.getY() / -2) + 540), 16755200);
                        y.addAndGet(12);
                    }

                }

            }
        }

    }
}


