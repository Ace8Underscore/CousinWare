package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.NordClient;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.FriendManager;
import io.ace.nordclient.utilz.clientutil.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class GoonSquad extends Hack {
    Setting x;
    Setting y;

    /**
     * @author Ace________/Ace_#1233
     */

    public GoonSquad() {
        super("GoonSquad", Category.RENDER);
        NordClient.INSTANCE.settingsManager.rSetting(x = new Setting("x", this, 959, 0, 1000, false, "GoonSquadX"));
        NordClient.INSTANCE.settingsManager.rSetting(y = new Setting("y", this, 500, 0, 1000, false, "GoonSquadY"));

    }

    public static String friends;
    private String str;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.world == null)
            return;
        AtomicInteger y = new AtomicInteger(2);
        mc.fontRenderer.drawStringWithShadow(ChatFormatting.BOLD + "GoonSquad", x.getValInt(), this.y.getValInt() - 10, 16777215);
        for (Object o : mc.world.getLoadedEntityList()) {
            if (o instanceof EntityPlayer) {
                if (((EntityPlayer) o).getName() != mc.player.getName()) {
                    FriendManager.getFriends()
                            .stream()
                            .forEach(friend -> {
                                if (friend.getName().contains(((EntityPlayer) o).getName())) {
                                    friends = ((EntityPlayer) o).getGameProfile().getName();
                                    str = " " + friends;
                                    mc.fontRenderer.drawStringWithShadow(str, x.getValInt(), y.get() + this.y.getValInt(), 16755200);
                                    y.addAndGet(12);
                                    //}
                                }

                            });
                }

            }
        }

    }
}



