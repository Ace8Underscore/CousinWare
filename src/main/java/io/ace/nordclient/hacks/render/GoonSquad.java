package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class GoonSquad extends Hack {

    /**
     * @author Ace________/Ace_#1233
     */

    public GoonSquad() {
        super("GoonSquad", Category.RENDER);

    }

    public static String friends;
    private String str;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.world == null)
            return;
        AtomicInteger y = new AtomicInteger(2);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "GoonSquad", 100, 100 - 10, 16777215);
        for (Object o : mc.world.getLoadedEntityList()) {
            if (o instanceof EntityPlayer) {
                if (((EntityPlayer) o).getName() != mc.player.getName()) {
                    FriendManager.getFriends()
                            .stream()
                            .forEach(friend -> {
                                if (friend.getName().contains(((EntityPlayer) o).getName())) {
                                    friends = ((EntityPlayer) o).getGameProfile().getName();
                                    str = " " + friends;
                                    mc.fontRenderer.drawStringWithShadow(str, 100, y.get() + 100, 16755200);
                                    y.addAndGet(12);
                                    //}
                                }

                            });
                }

            }
        }

    }
}



